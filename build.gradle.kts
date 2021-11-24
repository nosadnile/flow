plugins {
    java
    application
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.0" apply false
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT")
}

application {
    mainClass.set("net.nosadnile.App")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "net.nosadnile.App"
    }
}