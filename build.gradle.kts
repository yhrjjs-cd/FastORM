plugins {
    id("java")
    alias(libs.plugins.spring.boot)
}

group = "com.cdyhrj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.lombok)
    implementation(libs.spring.boot.starter.web)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}