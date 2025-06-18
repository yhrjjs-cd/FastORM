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
    implementation(libs.javassist)
    implementation(libs.lombok)
    implementation(libs.commons.lang3)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.bundles.fastjson2)
    implementation(libs.guava)
    implementation(libs.spring.jdbc)
    implementation(libs.alibaba.druid.starter)
    implementation(libs.com.mysql.mysql.connector.j)
    compileOnly(libs.spring.boot.autoconfigure)
//    implementation(libs.easy.query.sql.api.proxy)
//    implementation(libs.easy.query.sql.api4j)
//    implementation(libs.easy.query.sql.mysql)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    annotationProcessor(libs.lombok)
}

tasks.test {
    useJUnitPlatform()
}