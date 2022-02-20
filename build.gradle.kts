import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    jacoco
}

group = "com.hevlar"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.3")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:2.6.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.hamcrest:hamcrest-all:1.3")
    runtimeOnly("com.h2database:h2:2.1.210")
    implementation("ch.qos.logback:logback-core:1.2.10")
    implementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("com.ninja-squad:springmockk:3.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.3") {
        exclude(module = "mockito-core")
    }
    testImplementation("io.cucumber:cucumber-java8:7.2.3")
    testImplementation("io.cucumber:cucumber-junit:7.2.3")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")
    testImplementation(group= "io.cucumber", name="cucumber-spring", version="6.10.4")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

noArg {
    annotation("com.hevlar.eule.model")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Cucumber Settings

val cucumberRuntime: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

task("cucumber") {
    group = "verification"
    dependsOn("assemble", "testClasses")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            // Change glue for your project package where the step definitions are.
            // And where the feature files are.
            args = listOf("--plugin", "pretty", "--glue", "com.hevlar.eule.cucumber", "src/test/resources")
            // Configure jacoco agent for the test coverage.
            val jacocoAgent = zipTree(configurations.jacocoAgent.get().singleFile)
                .filter { it.name == "jacocoagent.jar" }
                .singleFile
            jvmArgs = listOf("-javaagent:$jacocoAgent=destfile=$buildDir/results/jacoco/cucumber.exec,append=false")
        }
    }
}

// Cucumber Settings

tasks.jacocoTestReport {
    // Give jacoco the file generated with the cucumber tests for the coverage.
    executionData(files("$buildDir/jacoco/test.exec", "$buildDir/results/jacoco/cucumber.exec"))
    reports {
        html.required.set(true)
    }
}