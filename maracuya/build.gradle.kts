import javax.inject.Inject
import org.gradle.process.ExecOperations

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "cruxui.android.lib"
    compileSdk = 36


    //Esto garantiza que todos los archivos dentro de src/main/java/... sean visibles.
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
        }
    }

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // --- 🔹 HABILITAMOS COMPOSE ---
    buildFeatures{
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    // El plugin Kotlin Compose configura el compilador con la versión del catálogo.
    kotlinOptions {
        jvmTarget = "19"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // --- 🔹 Jetpack Compose (usando catálogo y BOM) ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)

    // --- 🔹 Solo en debug (opcional) ---
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- 🔹 Tests ---
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}

// Proyección de las fuentes del tema durante el build, independiente de la compilación Android.
val tokenExporter by configurations.creating
dependencies { tokenExporter(project(":token-exporter")) }

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * GenerateLibraryTokens ejecuta el exportador JVM y publica recursos y referencias Kotlin del DS.
 *
 * Justificación:
 * Conecta las salidas del catálogo con cada variante Android sin duplicar valores en archivos manuales.
 * Gradle comprueba el classpath y los assets de entrada para regenerar solo cuando corresponde.
 *
 * Uso:
 * Tarea interna del build. androidComponents registra sus salidas; el host consume los recursos generados.
 *
 * Objetivos:
 * - Mantener Library como fuente única para Compose y XML.
 * - Eliminar salidas obsoletas cuando se retiran tokens.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param exec Servicio Gradle para ejecutar el exportador en una JVM independiente.
 */
abstract class GenerateLibraryTokens @Inject constructor(private val exec: ExecOperations) : DefaultTask() {
    @get:Classpath abstract val exporterClasspath: ConfigurableFileCollection
    @get:InputDirectory @get:PathSensitive(PathSensitivity.RELATIVE) abstract val assets: DirectoryProperty
    @get:OutputDirectory abstract val resources: DirectoryProperty
    @get:OutputDirectory abstract val sources: DirectoryProperty

    /** Regenera únicamente las salidas propias para no conservar recursos de tokens eliminados. */
    @TaskAction fun generate() {
        resources.get().asFile.deleteRecursively()
        sources.get().asFile.deleteRecursively()
        exec.javaexec {
            classpath(exporterClasspath)
            mainClass.set("exporter.ExportLibraryTokensKt")
            args(resources.get().asFile.absolutePath, sources.get().asFile.absolutePath, assets.get().asFile.absolutePath)
        }.assertNormalExitValue()
    }
}

val generateLibraryTokens = tasks.register<GenerateLibraryTokens>("generateLibraryTokens") {
    exporterClasspath.from(tokenExporter)
    assets.set(layout.projectDirectory.dir("src/main/res"))
    resources.set(layout.buildDirectory.dir("generated/libraryTokens/res"))
    sources.set(layout.buildDirectory.dir("generated/libraryTokens/kotlin"))
}

androidComponents.onVariants { variant ->
    variant.sources.res?.addGeneratedSourceDirectory(generateLibraryTokens, GenerateLibraryTokens::resources)
    variant.sources.java?.addGeneratedSourceDirectory(generateLibraryTokens, GenerateLibraryTokens::sources)
}
