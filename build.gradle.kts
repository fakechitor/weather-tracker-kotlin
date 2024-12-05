plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    war
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.weatherTracker"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.0")
    implementation("org.springframework:spring-core:6.2.0")
    implementation("org.springframework:spring-context:6.2.0")
    implementation("org.springframework:spring-web:6.2.0")
    implementation("org.springframework:spring-webmvc:6.2.0")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")
    providedCompile("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
    testImplementation("junit:junit:3.8.1")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.named<War>("war") {
    archiveFileName.set("ROOT.war")
}
