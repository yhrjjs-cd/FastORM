plugins {
    java
    `maven-publish`
    alias(libs.plugins.spring.boot)
}

group = "com.cdyhrj"
version = "0.0.1"

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
    annotationProcessor(libs.lombok)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testAnnotationProcessor(libs.lombok)
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }

    jvmArgs(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "--add-exports", "java.base/sun.security.ssl=ALL-UNNAMED"
    )
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}