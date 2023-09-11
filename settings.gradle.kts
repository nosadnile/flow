pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven {
            name = "nosadnile-maven"
            url = uri("https://repo.nosadnile.net/releases")
        }
    }
}

rootProject.name = "flow"

include("flow-purpur", "flow-preload", "flow-velocity", "flow-common")
