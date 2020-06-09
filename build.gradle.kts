plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("se.patrikerdes.use-latest-versions") version "0.2.14"
    id("com.github.ben-manes.versions") version "0.28.0"
    id("net.rdrei.android.buildtimetracker") version "0.11.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(":game-engine"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/robertfmurdock/zegreatrob") }
    }
}