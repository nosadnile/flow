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
}

dependencies {
    compileOnly("net.luckperms:api:5.4")

    implementation("org.mongodb:mongo-java-driver:2.12.3")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.14.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.14.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.nosadnile.flow"
            artifactId = "api"
            version = version

            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://maven.pkg.jetbrains.space/nosadnile/p/main/maven")

            credentials {
                username = System.getProperty("space.username")
                password = System.getProperty("space.password")
            }
        }
    }
}
