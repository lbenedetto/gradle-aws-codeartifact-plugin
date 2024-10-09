import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.3.0"
}


repositories {
    mavenCentral()
}


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


val awsSdkVersion = project.extra["aws.sdk.version"] as String


dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    implementation("org.unbroken-dome.aws-codeartifact-maven-proxy:aws-codeartifact-maven-proxy:0.4.0") {
        exclude(group = "org.jetbrains.kotlin")
    }

    testImplementation(kotlin("stdlib-jdk8"))
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}


gradlePlugin {
    val githubUrl = project.extra["github.url"] as String
    website = githubUrl
    vcsUrl = githubUrl
    plugins {
        create("codeArtifact") {
            description = "A Gradle plugin for using AWS CodeArtifact repositories"
            displayName = "AWS CodeArtifact plugin"
            id = "org.unbroken-dome.aws.codeartifact"
            implementationClass = "org.unbrokendome.gradle.plugins.aws.codeartifact.AwsCodeArtifactPlugin"
            tags = listOf("codeartifact")
        }
    }
}
