import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    java
    kotlin("jvm") version "1.2.60"
    application
}

group = "maxdistructo.discord.bots"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    compile ("com.mashape.unirest","unirest-java","1.4.9")
    implementation ("com.github.MaxDistructo","mdCore-Discord4J", "2.2.1")
    compile ("com.discord4j","Discord4J","2.10.1")
    compile ("com.fasterxml.jackson.core", "jackson-databind", "2.9.4")
    compile ("com.sedmelluq","lavaplayer", "1.2.45")
    compile ("org.jetbrains.kotlin","kotlin-stdlib-jdk8","1.2.51")
    compile ("org.jetbrains.kotlinx","kotlinx-coroutines-core","0.22.5")
    compile("ch.qos.logback", "logback-classic", "1.2.3")
    testCompile ("org.jetbrains.kotlin", "kotlin-test", "1.2.51")
    compile(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "maxdistructo.discord.bots.droidbot.BaseBot"
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Main-Class"] = "maxdistructo.discord.bots.droidbot.BaseBot"
    }
    from(
            configurations.runtime.map {
                if (it.isDirectory) it else zipTree(it)
            }
    )
    with(tasks["jar"] as CopySpec)
}
tasks {
    "build" {
        dependsOn(fatJar)
    }
}
kotlin {
    experimental.coroutines = Coroutines.ENABLE
}