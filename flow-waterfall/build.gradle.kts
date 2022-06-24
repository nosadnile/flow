plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        name = "papermc-repo"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    mavenCentral()
}

dependencies {
    api(project(":flow-common"))

    compileOnly("io.github.waterfallmc:waterfall-api:1.18-R0.1-SNAPSHOT")

    compileOnly("net.luckperms:api:5.3")
    implementation("org.mongodb:mongo-java-driver:2.12.3")
    implementation("com.google.guava:guava:31.0.1-jre")
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}
