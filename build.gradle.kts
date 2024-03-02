plugins {
    val kotlinVersion = "1.9.20"

    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
<<<<<<< Updated upstream
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.22"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.22"
    id("org.jetbrains.kotlin.kapt") version "1.8.22"
=======
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.lombok") version kotlinVersion
    id("io.freefair.lombok") version "8.1.0"
>>>>>>> Stashed changes
}

group = "me.justin"
version = "0.0.4"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    val mockkVersion = "1.13.8"
    val querydslVersion = "5.0.0"

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.projectlombok:lombok:1.18.28")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    implementation("jakarta.mail:jakarta.mail-api:2.1.2")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.modelmapper:modelmapper:2.4.5")
    implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    implementation("com.querydsl:querydsl-core:${querydslVersion}")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.codehaus.janino:janino:3.1.2")
    annotationProcessor("org.projectlombok:lombok")

<<<<<<< Updated upstream
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
=======
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("com.h2database:h2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
>>>>>>> Stashed changes

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("com.tngtech.archunit:archunit-junit5:0.13.1")
    testImplementation("org.testcontainers:junit-jupiter:1.13.0")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("io.mockk:mockk:${mockkVersion}")
}

val generated = "src.main/generated"

tasks.withType<Test> {
    useJUnitPlatform()
}

sourceSets{
    main{
        java{
            srcDir("generated")
        }
    }
}

kapt {
    keepJavacAnnotationProcessors = true
}

tasks{

    test {
        useJUnitPlatform()
    }
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    "clean" {
        doLast{
            file("generated").deleteRecursively()
        }
    }
}

