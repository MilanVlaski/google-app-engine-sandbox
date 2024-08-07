import groovy.xml.dom.DOMCategory.attributes

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.19"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.2"
    id("io.micronaut.aot") version "4.4.2"
}

version = "0.1"
group = "com.akimi"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")

    implementation("com.google.cloud:google-cloud-secretmanager:2.0.2")

    implementation("com.auth0:java-jwt:3.19.1")
    implementation("com.auth0:jwks-rsa:0.22.1")
    implementation("com.google.apis:google-api-services-oauth2:v2-rev20200213-2.0.0")
    implementation("com.google.api-client:google-api-client:2.6.0")
    implementation("com.google.apis:google-api-services-walletobjects:v1-rev20240220-2.0.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.24.0")

    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass = "com.akimi.ApplicationKt"
}

tasks.shadowJar {
    archiveBaseName.set("gae-sbx") // Customize the name if needed
    archiveClassifier.set("") // This ensures no classifier is added
    archiveVersion.set("") // This ensures no version is added
    manifest {
        attributes(
            "Main-Class" to "com.akimi.ApplicationKt" // Specify your main class here
        )
    }
    destinationDirectory.set(file("$rootDir/lib"))
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.akimi.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


