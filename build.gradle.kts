import Com_hayden_docker_gradle.DockerContext;

plugins {
    id("com.hayden.docker")
    id("com.hayden.jpa-persistence")
    id("com.hayden.spring-app")
    id("com.hayden.graphql-data-service")
    id("com.hayden.discovery-app")
    id("com.hayden.messaging")
    id("com.hayden.docker-compose")
}

group = "com.hayden"
version = "0.0.1-SNAPSHOT"

tasks.register("prepareKotlinBuildScriptModel") {}

wrapDocker {
    ctx = arrayOf(
        DockerContext(
            "localhost:5001/postgres-pgvector",
            "${project.projectDir}/src/main/docker/postgres",
            "pgVectorPostgres"
        ),
        DockerContext(
            "localhost:5001/jdk",
            "${project.projectDir}/src/main/docker/jdk",
            "jdk"
        ),
        DockerContext(
            "localhost:5001/python",
            "${project.projectDir}/src/main/docker/python",
            "python"
        )
    )
}

val dockerEnabled = project.property("enable-docker")?.toString()?.toBoolean()?.or(false) ?: false
val buildRunnerCode = project.property("build-runner-code")?.toString()?.toBoolean()?.or(false) ?: false

println("DockerEnabled: $dockerEnabled")

if (dockerEnabled && buildRunnerCode) {
    tasks.getByPath("jar").finalizedBy("buildDocker")

    tasks.getByPath("jar").doLast {
        tasks.getByPath("pgVectorPostgresDockerImage").dependsOn("startRegistry")
        tasks.getByPath("pushImages").dependsOn("startRegistry")
    }

    tasks.register("buildDocker") {
        dependsOn("bootJar", "startRegistry", "pgVectorPostgresDockerImage", "pythonDockerImage", "jdkDockerImage", "pushImages")
    }

    afterEvaluate {

        tasks.register("startRegistry") {
            println("Starting Registry...")

            exec {
                workingDir(projectDir.resolve("src/main/docker"))
                commandLine("/usr/local/bin/docker-compose", "up", "-d")
            }
        }
    }
}

