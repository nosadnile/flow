pluginManagement {
    repositories {
        maven {
            name = "nosadnile-maven"
            url = uri("https://repo.nosadnile.net/releases")
        }
    }
}

rootProject.name = "flow"

include("flow-purpur", "flow-velocity", "flow-common")
