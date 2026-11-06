pluginManagement {
    repositories {

        val jfrogUser: String? = providers.gradleProperty("JFROG_USER")
            .orElse(providers.environmentVariable("JFROG_USER"))
            .orNull
        val jfrogPassword: String? = providers.gradleProperty("JFROG_PASSWORD")
            .orElse(providers.environmentVariable("JFROG_PASSWORD"))
            .orNull

        val jfrogDebug = (
                providers.gradleProperty("JFROG_DEBUG_AUTH")
                    .orElse(providers.environmentVariable("JFROG_DEBUG_AUTH"))
                    .orNull
                )?.equals("true", ignoreCase = true) == true

        fun RepositoryHandler.jfrogRepo(repo : String){
            maven {
                url = uri("https://af.cds.bns/artifactory/$repo")
                credentials{
                    username = jfrogUser
                    password = jfrogPassword
                }
                if (jfrogDebug) println("[pluginManagement] Repositorio corporativo habilitado: $repo")
            }
        }

        if(!jfrogUser.isNullOrBlank() && !jfrogPassword.isNullOrBlank()){
            jfrogRepo("ext-google-maven")
            jfrogRepo("ext-google-maven-cache")
            jfrogRepo("jcenter")
            jfrogRepo("jcenter-cache")
            jfrogRepo("ext-release-maven-cache")

        }else if (jfrogDebug){
            println("[pluginManagement] Repositorios corporativos omitidos: faltan credenciales")
        }


        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(
            url = uri("https://maven.google.com/")
        )
        maven(
            url = uri("https://plugins.gradle.org/m2/")
        )
    }
}


dependencyResolutionManagement {
    repositories {
        val jfrogUser: String? = providers.gradleProperty("JFROG_USER")
            .orElse(providers.environmentVariable("JFROG_USER"))
            .orNull
        val jfrogPassword: String? = providers.gradleProperty("JFROG_PASSWORD")
            .orElse(providers.environmentVariable("JFROG_PASSWORD"))
            .orNull
        val jfrogDebug = (
                providers.gradleProperty("JFROG_DEBUG_AUTH")
                    .orElse(providers.environmentVariable("JFROG_DEBUG_AUTH"))
                    .orNull
                )?.equals("true", ignoreCase = true) == true

        fun RepositoryHandler.jfrogRepo(repo : String){
            maven {
                url = uri("https://af.cds.bns/artifactory/$repo")
                credentials{
                    username = jfrogUser
                    password = jfrogPassword
                }
                if (jfrogDebug) println("[dependencyResolutionManagement] Repositorio corporativo habilitado: $repo")
            }
        }


        if(!jfrogUser.isNullOrBlank() && !jfrogPassword.isNullOrBlank()){
            jfrogRepo("ext-google-maven")
            jfrogRepo("ext-google-maven-cache")
            jfrogRepo("jcenter")
            jfrogRepo("jcenter-cache")
            jfrogRepo("ext-release-maven-cache")

        }else if (jfrogDebug){
            println("[dependencyResolutionManagement] Repositorios corporativos omitidos: faltan credenciales")
        }

        google()
        mavenCentral()
        maven(
            url = uri("https://maven.google.com/")
        )
        maven(
            url = uri("https://plugins.gradle.org/m2/")
        )

    }
}

rootProject.name = "MyFirstApplication"
include(":app")
include(":maracuya")
include(":token-exporter")
