plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.luckperms:api:5.3")
    compileOnly("org.mongodb:mongo-java-driver:2.12.3")
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}
