import net.nosadnile.gradle.serverhelper.dsl.ServerType

plugins {
    id("java")
    id("eclipse")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.nosadnile.gradle.serverhelper") version "1.5.0"
}

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

dependencies {
    annotationProcessor("dev.jorel:commandapi-annotations:9.0.0")

    compileOnly(dependencyNotation = "org.purpurmc.purpur:purpur-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly(dependencyNotation = "org.spigotmc:spigot:1.19.4-R0.1-SNAPSHOT")

    compileOnly("net.luckperms:api:5.3")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")

    implementation("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT")
    implementation("dev.jorel:commandapi-bukkit-shade:9.0.0")
    implementation("dev.jorel:commandapi-annotations:9.0.0")
    implementation("org.mongodb:mongo-java-driver:2.12.3")
    implementation("com.google.guava:guava:31.0.1-jre")

    implementation(project(":flow-common"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}

serverHelper {
    getEula().set(true)
    getServerType().set(ServerType.PURPUR)
    getServerDirectory().set(project.rootDir.resolve("run"))
    getMinecraftVersion().set("1.19.4")
}
