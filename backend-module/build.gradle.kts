plugins {
    application
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("ru.backend.ApplicationKt")
}

repositories {
   mavenCentral()
}

dependencies {
    implementation(project(":shared-module"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.20-RC")
    implementation ("io.ktor:ktor-server-core:1.6.0")
    implementation ("io.ktor:ktor-server-netty:1.6.0")
    implementation ("ch.qos.logback:logback-classic:1.2.3")
//    implementation("io.ktor:ktor-server-netty:$ktor_version")
//    implementation("ch.qos.logback:logback-classic:$logback_version")
//    implementation("io.ktor:ktor-server-core:$ktor_version")
//    implementation("io.ktor:ktor-freemarker:$ktor_version")
//    implementation("io.ktor:ktor-velocity:$ktor_version")
//    implementation("io.ktor:ktor-server-host-common:$ktor_version")
//    implementation("io.ktor:ktor-server-sessions:$ktor_version")
//    implementation("io.ktor:ktor-webjars:$ktor_version")
//    implementation("org.webjars:jquery:3.2.1")
//    implementation("io.ktor:ktor-websockets:$ktor_version")
//    implementation("io.ktor:ktor-auth:$ktor_version")
//    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    
//    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
//    testImplementation("org.assertj:assertj-core:3.12.2")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks {
    compileKotlin {
        java {
            sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
            targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
        }
    }
    compileTestKotlin {
        java {
            sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
            targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
        }
    }
}

sourceSets {
    get("main").java.srcDirs("src/main/kotlin")
}
//
//kotlin.sourceSets["ru.backend.main"].kotlin.srcDirs("src/main")
//kotlin.sourceSets["test"].kotlin.srcDirs("test")
//
//sourceSets["ru.backend.main"].resources.srcDirs("resources")
//sourceSets["test"].resources.srcDirs("testresources")
