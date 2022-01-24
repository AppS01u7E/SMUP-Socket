import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.9"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.32"
    kotlin("plugin.spring") version "1.5.32"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.61"
}

group = "com.appsolute"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor") //configuration
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")//jpa
    developmentOnly("org.springframework.boot:spring-boot-devtools") //devtool
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //jackson for kotilin
    implementation("org.springframework.boot:spring-boot-starter-security") //security
    implementation("org.springframework.boot:spring-boot-starter-validation") //validation
    //socket
    implementation("com.corundumstudio.socketio:netty-socketio:1.7.7")
    //objectMapper
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    //logging
    implementation("org.apache.logging.log4j:log4j-api:2.17.0")
    //jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    //mysql
    runtimeOnly("mysql:mysql-connector-java")
    //redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
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
