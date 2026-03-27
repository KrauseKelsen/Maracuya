plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.mylibrary"
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

    // --- CONFIGURACIÓN DEL COMPILADOR COMPOSE ---
    composeOptions {
        //Si usas Kotlin 2.0.21, necesitas un Compose Compiler 1.7.x,
        //porque a partir de Compose 1.7 el soporte oficial para Kotlin 2.0.x es estable.
        kotlinCompilerExtensionVersion = "1.7.3"

    }

    kotlinOptions {
        jvmTarget = "19"
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
    implementation(libs.androidx.runtime)

    // --- 🔹 Solo en debug (opcional) ---
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- 🔹 Tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}