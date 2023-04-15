package com.example.project.template.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            java {
                toolchain.languageVersion.set(JavaLanguageVersion.of(17))
            }

            androidApplication {
                namespace?.let {
                    this.namespace = it
                }
                compileSdk = 33
                defaultConfig {
                    minSdk = 23
                }

                compileOptions {
                    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
                    targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
                    isCoreLibraryDesugaringEnabled = true
                }

                kotlinOptions {
                    // Treat all Kotlin warnings as errors (disabled by default)
                    allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false

                    freeCompilerArgs = freeCompilerArgs + listOf(
//              "-opt-in=kotlin.RequiresOptIn",
                        // Enable experimental coroutines APIs, including Flow
//              "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    )
                }

                dependencies {
                    add("coreLibraryDesugaring", libs.findLibrary("androidDesugarJdkLibs").get())
                }

                defaultConfig.targetSdk = 32
                packagingOptions {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}

