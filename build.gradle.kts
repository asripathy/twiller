plugins {
    java
    kotlin("jvm") version "1.4.32"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.twilio.sdk:twilio:8.13.0")
    implementation("com.sparkjava:spark-core:2.7.1")
    implementation("org.slf4j:slf4j-simple:1.7.21")
    implementation("com.github.redouane59.twitter:twittered:1.24")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}