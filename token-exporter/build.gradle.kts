plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

// Compila las fuentes puras del DS: no analiza texto Kotlin ni duplica valores.
kotlin.sourceSets.main {
    kotlin.srcDir("../maracuya/src/main/java")
    kotlin.include("cruxui/android/lib/atoms/**")
    kotlin.include("cruxui/android/lib/tokens/**")
    kotlin.include("cruxui/android/lib/semantics/**")
    listOf("LibraryThemes", "LibraryThemeSnapshot", "ThemeStyle", "FontResources", "FontAssetCatalog").forEach {
        kotlin.include("cruxui/android/lib/theme/$it.kt")
    }
    kotlin.include("cruxui/android/lib/theme/catalog/**")
    kotlin.include("cruxui/android/lib/theme/collections/**")
    kotlin.include("cruxui/android/lib/wrappers/core/tokens/*Role.kt")
    kotlin.include("exporter/**")
}

// Fase independiente: catálogos Library y tokens puros; no depende de temas ni de APIs generadas.
val catalogSchema by sourceSets.creating
kotlin.sourceSets.named("catalogSchema") {
    kotlin.srcDir("../maracuya/src/main/java")
    kotlin.include("schema/**")
    kotlin.include("cruxui/android/lib/atoms/**")
    kotlin.include("cruxui/android/lib/tokens/base/**")
    kotlin.include("cruxui/android/lib/tokens/colors/**")
    kotlin.include("cruxui/android/lib/tokens/fonts/**")
}
configurations[catalogSchema.implementationConfigurationName].extendsFrom(configurations.implementation.get())

val catalogSources = layout.buildDirectory.dir("generated/catalogApi/kotlin")
val catalogResources = layout.buildDirectory.dir("generated/catalogApi/resources")
val generateCatalogApi = tasks.register<JavaExec>("generateCatalogApi") {
    description = "Genera colecciones, consultas y roles de colores, tipografías e iconos desde Library."
    classpath = catalogSchema.runtimeClasspath
    mainClass.set("schema.GenerateCatalogApiKt")
    outputs.dirs(catalogSources, catalogResources)
    args(catalogSources.get().asFile.absolutePath, catalogResources.get().asFile.absolutePath)
}
kotlin.sourceSets.main { kotlin.srcDir(catalogSources) }
sourceSets.main { resources.srcDir(catalogResources) }
tasks.named("compileKotlin") { dependsOn(generateCatalogApi) }
tasks.named("processResources") { dependsOn(generateCatalogApi) }

application { mainClass.set("exporter.ExportLibraryTokensKt") }
kotlin.compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

// Las pruebas de exportación no requieren Android ni un dispositivo.
dependencies { testImplementation(libs.junit) }

// Prueba los escritores de la fase inicial sin incluirlos en el artefacto de ejecución.
kotlin.sourceSets.test { kotlin.srcDir("src/catalogSchema/kotlin") }
