import net.nosadnile.gradle.serverhelper.dsl.ServerType

plugins {
    id("net.nosadnile.gradle.serverhelper")
}

dependencies {
    annotationProcessor("dev.jorel:commandapi-annotations:9.1.0")

    compileOnly(dependencyNotation = "org.purpurmc.purpur:purpur-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly(dependencyNotation = "org.spigotmc:spigot:1.20-R0.1-SNAPSHOT")

    compileOnly("net.luckperms:api:5.4")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    compileOnly("net.citizensnpcs:citizens-main:2.0.32-SNAPSHOT")

    implementation("dev.jorel:commandapi-bukkit-shade:9.1.0")
    implementation("dev.jorel:commandapi-annotations:9.1.0")
    implementation("org.mongodb:mongo-java-driver:2.12.3")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("me.catcoder:bukkit-sidebar:6.2.3-SNAPSHOT")

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
    jvmArgs.add("-Dfile.encoding=UTF-8")
    serverArgs.add("-o true")
    serverType.set(ServerType.PURPUR)
    serverDirectory.set(project.rootDir.resolve("run/purpur"))
    minecraftVersion.set("1.20.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.nosadnile.flow"
            artifactId = "purpur"
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
