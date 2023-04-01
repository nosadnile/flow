plugins {
    id("java")
    id("eclipse")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
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
}

dependencies {
    compileOnly("org.purpurmc.purpur:purpur-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.19.4-R0.1-SNAPSHOT")

    compileOnly("net.luckperms:api:5.3")
    compileOnly("org.bstats:bstats-bukkit:3.0.0")
    compileOnly("de.simonsator:Party-and-Friends-MySQL-Edition-Spigot-API:1.5.3")
    compileOnly("de.simonsator:Spigot-Party-API-For-RedisBungee:1.0.3-SNAPSHOT")
    compileOnly("de.simonsator:DevelopmentPAFSpigot:1.0.67")
    compileOnly("com.alessiodp.parties:parties-api:3.2.4")
    compileOnly("me.clip:placeholderapi:2.11.3")

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
