val springVersion = "6.2.1"
val flywayVersion = "11.1.0"

plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("kapt") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    war
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.cloud.tools.jib") version "3.3.2"
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
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-web:$springVersion")
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("org.springframework:spring-orm:$springVersion")
    implementation("org.springframework:spring-tx:$springVersion")
    implementation("org.springframework:spring-webflux:$springVersion")
    implementation("io.projectreactor:reactor-core:3.7.1")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:11.1.0")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.hibernate:hibernate-core:6.6.3.Final")
    implementation("ch.qos.logback:logback-classic:1.5.12")
    implementation("org.mapstruct:mapstruct:1.6.3")
    kapt("org.mapstruct:mapstruct-processor:1.6.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.2.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testImplementation("com.h2database:h2:2.3.232")
    testImplementation("org.springframework:spring-test:6.2.1")
    testImplementation("org.mockito:mockito-core:5.15.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.15.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
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

tasks.bootWar {
    enabled = false
}

jib {
    from.image = "tomcat:11"
    to.image = "weather-tracker:latest"
    container.appRoot = "/usr/local/tomcat/webapps/ROOT"
}
