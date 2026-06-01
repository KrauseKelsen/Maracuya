/// codigo para que muestre prints para ver si se pueden o si se consumen los paquetes desde aqui

// hay una alta probabilidad de que se consumen, dadas pruebas anteriores

// una vez se compruebe reportar: problema de version con paquete de versiones lint, imposibilidad de consumir paquetes de
// jfrog aun cuando se logra pin 200 y se conocen credenciales

// hay repos que no se pueden acceder como dl.google

//hacer consultas a dl.google desde compu personal

// despues de reportar empezar a montar los componentes de login y una demo para el lunes

fun Settings.readSecret(name: String): String? =
    providers.gradleProperty(name)
        .orElse(providers.environmentVariable(name))
        .orNull

fun maskSecret(value: String?): String {
    if (value.isNullOrBlank()) return "<empty>"
    if(value.length <= 2) return "*".repeat(value.length)
    return "${value.take(2)}***(${value.length})"
}

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

        if(jfrogDebug){
            println("[jfrogDebug true] [pluginManagement] user=${
                run {
                    when{
                        (jfrogUser.isNullOrBlank()) -> "<empty>"
                        (jfrogUser.length <= 2) -> "*".repeat(jfrogUser.length)
                        else -> "${jfrogUser.take(2)}***(${jfrogUser.length})"
                    }
                }
            } token=${run {
                when{
                    (jfrogPassword.isNullOrBlank()) -> "<empty>"
                    (jfrogPassword.length <= 2) -> "*".repeat(jfrogPassword.length)
                    else -> "${jfrogPassword.take(2)}***(${jfrogPassword.length})"
                }
            }}")
        }

        fun RepositoryHandler.jfrogRepo(repo : String){
            maven {
                url = uri("https://af.cds.bns/artifactory/$repo")
                credentials{
                    username = jfrogUser
                    password = jfrogPassword
                }
                println("[pluginManagement] [jfrog inside https://af.cds.bns/artifactory/$repo] user= $jfrogUser password= $jfrogPassword ")
            }
        }

        if(!jfrogUser.isNullOrBlank() && !jfrogPassword.isNullOrBlank()){
            println("[jfrog is calling repo] [pluginManagement]......")
            jfrogRepo("ext-google-maven")
            jfrogRepo("ext-google-maven-cache")
            jfrogRepo("jcenter")
            jfrogRepo("jcenter-cache")
            jfrogRepo("ext-release-maven-cache")

        }else if (jfrogDebug){
            println("[jfrogDebug with jfrogUser=$jfrogUser jfrogPassword=$jfrogPassword] [pluginManagement] skipped Jfrog repos: missing credentials")
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

        if(jfrogDebug){
            println("[jfrogDebug true] [dependencyResolution] user=${
                run {
                    when{
                        (jfrogUser.isNullOrBlank()) -> "<empty>"
                        (jfrogUser.length <= 2) -> "*".repeat(jfrogUser.length)
                        else -> "${jfrogUser.take(2)}***(${jfrogUser.length})"
                    }
                }
            } token=${run {
                when{
                    (jfrogPassword.isNullOrBlank()) -> "<empty>"
                    (jfrogPassword.length <= 2) -> "*".repeat(jfrogPassword.length)
                    else -> "${jfrogPassword.take(2)}***(${jfrogPassword.length})"
                }
            }}")
        }

        fun RepositoryHandler.jfrogRepo(repo : String){
            maven {
                url = uri("https://af.cds.bns/artifactory/$repo")
                credentials{
                    username = jfrogUser
                    password = jfrogPassword
                    println("[dependencyResolutionManagement] [jfrog inside https://af.cds.bns/artifactory/$repo] user= $jfrogUser password= $jfrogPassword ")
                }
            }
        }


        if(!jfrogUser.isNullOrBlank() && !jfrogPassword.isNullOrBlank()){
            println("[jfrog is calling repo] [dependencyResolutionManagement]......")
            jfrogRepo("ext-google-maven")
            jfrogRepo("ext-google-maven-cache")
            jfrogRepo("jcenter")
            jfrogRepo("jcenter-cache")
            jfrogRepo("ext-release-maven-cache")

        }else if (jfrogDebug){
            println("[jfrogDebug with jfrogUser=$jfrogUser jfrogPassword=$jfrogPassword] [dependencyResolutionManagement] skipped Jfrog repos: missing credentials")
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