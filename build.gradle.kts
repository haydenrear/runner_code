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
    tasks.getByPath("jar").finalizedBy("buildDocker")

    tasks.getByPath("jar").doLast {
        tasks.getByPath("pgVectorPostgresDockerImage").dependsOn("startRegistry")
        tasks.getByPath("pushImages").dependsOn("startRegistry")
    }

    tasks.register("buildDocker") {
        dependsOn("bootJar", "startRegistry", "pgVectorPostgresDockerImage", "pushImages")
    }
    afterEvaluate {

        tasks.register("startRegistry") {
            println("Starting Registry...")

            exec {
                workingDir("runner_code/src/main/docker")
                commandLine("/usr/local/bin/docker-compose", "up", "-d")
            }
        }
    }
}

