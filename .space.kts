job("Build and publish") {
    container(displayName = "Gradle publish", image = "gradle") {
        kotlinScript { api ->
            api.gradle("build")
            api.gradle("getLoginFromEnv", "publish")
        }
    }
}
