plugins {
    id("java")
    id("eclipse")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = project.property("maven_group")!!
version = project.property("version")!!

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
    implementation(project(":flow-purpur"))
    implementation(project(":flow-velocity"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}

tasks.create("getLoginFromEnv") {
    doLast {
        val key = System.getenv("JB_SPACE_CLIENT_ID")
        val secret = System.getenv("JB_SPACE_CLIENT_SECRET")

        if (key == null || secret == null) {
            throw GradleException("spaceUsername and/or spacePassword are not defined environment variables")
        }

        System.setProperty("space.username", key)
        System.setProperty("space.password", secret)
    }
}
