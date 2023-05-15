settingsEvaluated {
    pluginManagement {
        repositories {
            mavenCentral()
            gradlePluginPortal()

            maven {
                name = "GitHub Packages"
                url = uri("https://maven.pkg.github.com/nosadnile/serverhelper")

                credentials {
                    username = System.getProperty("github.user") ?: System.getenv("GITHUB_ACTOR")
                    password = System.getProperty("github.token") ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
