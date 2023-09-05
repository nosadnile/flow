import net.nosadnile.gradle.serverhelper.dsl.ServerType

plugins {
    id("java")
    id("eclipse")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.nosadnile.gradle.serverhelper") version "1.6.0"
}

repositories {
    mavenCentral()
    mavenLocal()

    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")

    compileOnly("net.luckperms:api:5.4")

    implementation("org.mongodb:mongo-java-driver:2.12.3")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("org.javatuples:javatuples:1.2")

    implementation(project(":flow-common"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}

serverHelper {
    eula.set(true)
    serverType.set(ServerType.VELOCITY)
    serverDirectory.set(project.rootDir.resolve("run-proxy"))
    minecraftVersion.set("3.1.1")

    proxy {
        eula.set(true)
        serverType.set(ServerType.PAPER)
        minecraftVersion.set("1.20.1")
        nogui.set(true)

        server("lobby", 25566)
        server("smp", 25567)
        server("creative", 25568)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.nosadnile.flow"
            artifactId = "velocity"
            version = version

            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://repo.nosadnile.net/releases")

            credentials {
                username = System.getenv("MAVEN_REPO_USER")
                password = System.getenv("MAVEN_REPO_TOKEN")
            }
        }
    }
}
