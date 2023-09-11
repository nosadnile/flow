plugins {
    id("java")
    id("eclipse")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.nosadnile.gradle.serverhelper") version "1.6.1" apply false
}

group = project.property("maven_group")!!
version = project.property("version")!!

allprojects {
    apply(plugin = "java")
    apply(plugin = "eclipse")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        mavenLocal()

        maven {
            name = "spigotmc-repo"
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        }

        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }

        maven {
            name = "purpurmc-repo"
            url = uri("https://repo.purpurmc.org/snapshots")
        }

        maven {
            name = "dmulloy2-repo"
            url = uri("https://repo.dmulloy2.net/repository/public/")
        }

        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }

        maven {
            name = "jitpack"
            url = uri("https://jitpack.io/")
        }

        maven {
            name = "alessiodp"
            url = uri("https://repo.alessiodp.com/releases/")
        }

        maven {
            name = "PlaceholderAPI"
            url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        }

        maven {
            name = "SimonStators-Repo"
            url = uri("https://simonsator.de/repo/")
        }

        maven {
            name = "CodeMC"
            url = uri("https://repo.codemc.org/repository/maven-public/")
        }

        maven {
            name = "Citizens"
            url = uri("https://maven.citizensnpcs.co/repo")
        }
    }
}

dependencies {
    implementation(project(":flow-purpur"))
    implementation(project(":flow-velocity"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}
