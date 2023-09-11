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
            url = uri("https://repo.nosadnile.net/releases")

            credentials {
                username = System.getenv("MAVEN_REPO_USER")
                password = System.getenv("MAVEN_REPO_TOKEN")
            }
        }
    }
}
