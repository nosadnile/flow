job("Build and publish") {
    container(displayName = "Gradle publish", image = "amazoncorretto:17-alpine") {
        kotlinScript { api ->
            api.gradlew("build")
            api.gradlew("flow-purpur:getLoginFromEnv")
            api.gradlew("flow-purpur:publish")
        }
    }
}
