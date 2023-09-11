import net.nosadnile.gradle.serverhelper.dsl.ServerType

plugins {
    id("net.nosadnile.gradle.serverhelper")
}

dependencies {
    compileOnly(dependencyNotation = "org.purpurmc.purpur:purpur-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly(dependencyNotation = "org.spigotmc:spigot:1.20-R0.1-SNAPSHOT")

    implementation("com.google.guava:guava:31.0.1-jre")

    implementation(project(":flow-common"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    expand("version" to project.version)
}

serverHelper {
    eula.set(true)
    jvmArgs.add("-Dfile.encoding=UTF-8")
    serverArgs.add("-o true")
    serverType.set(ServerType.PURPUR)
    serverDirectory.set(project.rootDir.resolve("run/preload"))
    minecraftVersion.set("1.20.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.nosadnile.flow"
            artifactId = "preload"
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
