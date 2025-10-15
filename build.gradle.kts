import Com_hayden_docker_gradle.DockerContext;
import java.nio.file.Paths

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

val registryBase = project.property("registryBase") ?: "localhost:5001"

logger.log(LogLevel.INFO, "Found registry base: $registryBase")

wrapDocker {
    ctx = arrayOf(
        DockerContext(
            "${registryBase}/postgres-pgvector",
            "${project.projectDir}/src/main/docker/postgres",
            "pgVectorPostgres"
        ),
        DockerContext(
            "${registryBase}/postgres-pgvector-15",
            "${project.projectDir}/src/main/docker/postgres-15",
            "pgVectorPostgres15"
        ),
        DockerContext(
            "${registryBase}/jdk",
            "${project.projectDir}/src/main/docker/jdk",
            "jdk"
        ),
        DockerContext(
            "${registryBase}/jdk-codegen",
            "${project.projectDir}/src/main/docker/jdk-codegen",
            "jdkCodegen"
        ),
        DockerContext(
            "${registryBase}/node",
            "${project.projectDir}/src/main/docker/node",
            "node"
        ),
        DockerContext(
            "${registryBase}/python",
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
        dependsOn("bootJar", "startRegistry", "pgVectorPostgres15DockerImage", "pgVectorPostgresDockerImage",
                           "pythonDockerImage", "jdkDockerImage", "jdkCodegenDockerImage", "nodeDockerImage",
                           "pushImages")
        doLast {
            delete(fileTree(Paths.get(projectDir.path, "src/main/docker")) {
                include("**/*.jar")
            })
        }
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

