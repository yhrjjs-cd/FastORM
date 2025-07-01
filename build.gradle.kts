plugins {
    java
    `maven-publish`
    alias(libs.plugins.spring.boot)
}

group = "com.cdyhrj"
version = "0.0.1"

repositories {
    mavenCentral()
    maven {
        credentials {
            username = "600167914fb2132a19618640"
            password = "i5)-W)rYUXB_"
        }
        setUrl("https://packages.aliyun.com/63199ee5050e9c4a07a98a2f/maven/2276037-release-uzq6cr")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
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

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:unchecked")
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

tasks.bootJar {
    enabled = false
}

tasks {
    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.named<Jar>("jar")) {
                classifier = ""
            }

            artifact(tasks.named<Jar>("sourcesJar")) {}
        }
    }
}