plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    targets {
        jvm()
        js { nodejs() }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("stdlib", "1.3.61"))
                api(kotlin("stdlib-common", "1.3.61"))
                api("com.soywiz.korlibs.klock:klock:1.8.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test", "1.3.61"))
                implementation(kotlin("test-common", "1.3.61"))
                implementation(kotlin("test-annotations-common", "1.3.61"))
                implementation("com.zegreatrob.testmints:standard:+")
                implementation("com.zegreatrob.testmints:minassert:+")
                implementation("com.zegreatrob.testmints:minspy:+")
                implementation("com.github.ajalt:clikt:2.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.3")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("com.zegreatrob.testmints:async:+")
                implementation(kotlin("test", "1.3.61"))
                implementation(kotlin("test-junit5", "1.3.61"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
                implementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
            }
        }

        val jsMain by getting {
            dependencies {
                api(kotlin("stdlib-js", "1.3.61"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js", "1.3.61"))
            }
        }
    }
}

tasks {
    val jvmTest by getting(Test::class) {
        systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")
        useJUnitPlatform()
    }
}
