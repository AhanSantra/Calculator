plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.beryx.jlink") version "2.26.0"
}

group = "io.github.ahansantra"
version = "1.0.0"

application {
    mainClass.set("io.github.ahansantra.calculator.Main")
    mainModule.set("io.github.ahansantra.calculator")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "io.github.ahansantra.calculator.Main"
        )
    }
}

/**
 * jpackage app-image task
 */
tasks.register<Exec>("jpackageApp") {
    group = "distribution"
    description = "Create self-contained app image using jpackage"

    dependsOn(tasks.jar)

    val jarTask = tasks.jar.get()
    val jarFile = jarTask.archiveFile.get().asFile
    val outputDir = layout.buildDirectory.dir("jpackage").get().asFile

    doFirst {
        outputDir.mkdirs()
        println("Using JAR: ${jarFile.name}")
    }

    commandLine(
        "jpackage",
        "--type", "app-image",
        "--dest", outputDir.absolutePath,
        "--name", "calculator",

        // 🔑 where jars are
        "--input", jarFile.parent,

        // 🔑 main jar
        "--main-jar", jarFile.name,

        // 🔑 main class
        "--main-class", "io.github.ahansantra.calculator.Main"
    )
}

tasks.register("windowsExe") {
    dependsOn("shadowJar")

    doLast {
        exec {
            commandLine(
                "scripts/build-exe.sh",
                "Calculator",
                project.version.toString(),
                "build/libs/calculator-${project.version}-all.jar",
                "io.github.ahansantra.calculator.Main",
                "icons/calculator.ico"
            )
        }
    }
}
