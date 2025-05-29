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
    implementation(libs.commons.lang3)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.bundles.fastjson2)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    annotationProcessor(libs.lombok)
}

tasks.test {
    useJUnitPlatform()
}