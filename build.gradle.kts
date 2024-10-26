import Com_hayden_docker_gradle.DockerContext;

plugins {
    id("com.hayden.docker")
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
        )
    )
}

if (project.property("enable-docker")?.toString()?.toBoolean() == true) {
    afterEvaluate {
        tasks.getByPath("pgVectorPostgresDockerImage").dependsOn("startRegistry")
        tasks.getByPath("pushImages").dependsOn("startRegistry")

        tasks.register("startRegistry") {
            println("Starting Registry...")
            exec {
                workingDir("src/main/docker")
                commandLine("/usr/local/bin/docker-compose", "up", "-d")
            }
        }
    }
}

