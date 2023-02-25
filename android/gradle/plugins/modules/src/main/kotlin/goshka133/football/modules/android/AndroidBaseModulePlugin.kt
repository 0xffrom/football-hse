@file:Suppress("UnstableApiUsage")

package goshka133.football.modules.android

import com.android.build.gradle.BaseExtension
import goshka133.football.modules.ProjectDefaults
import goshka133.football.modules.getVersionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class AndroidBaseModulePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.plugins) {
      apply("kotlin-android")
      apply("kotlin-parcelize")
      apply("com.stepango.aar2jar")
      apply("org.jetbrains.kotlin.kapt")
    }
    project.extensions.configure(BaseExtension::class.java) {
      configure(
        minSdk = 26,
        targetSdk = 33,
        buildToolsVersion = "33.0.0",
        targetJvm = ProjectDefaults.JavaVersion,
      )

      val composeCompilerVersion =
        project.getVersionCatalog().findVersion("compose.compiler").get().requiredVersion

      composeOptions { kotlinCompilerExtensionVersion = composeCompilerVersion }
    }
    project.tasks.withType(KotlinCompile::class.java) {
      kotlinOptions.jvmTarget = ProjectDefaults.JavaVersion.toString()
      kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    project.tasks.withType(JavaCompile::class.java).configureEach {
      sourceCompatibility = ProjectDefaults.JavaVersion.toString()
      targetCompatibility = ProjectDefaults.JavaVersion.toString()
    }

    project.applyComposeMetricCompilerFlags()
  }

  private fun BaseExtension.configure(
    minSdk: Int,
    targetSdk: Int,
    buildToolsVersion: String,
    targetJvm: JavaVersion,
  ) {
    compileSdkVersion(targetSdk)
    buildToolsVersion(buildToolsVersion)

    defaultConfig {
      this.minSdk = minSdk
      this.targetSdk = targetSdk
    }

    packagingOptions { resources.excludes += setOf("/META-INF/{AL2.0,LGPL2.1}") }

    compileOptions {
      targetCompatibility = targetJvm
      sourceCompatibility = targetJvm
    }
  }

  private fun Project.applyComposeMetricCompilerFlags() {

    tasks.withType(KotlinCompile::class.java) {
      kotlinOptions.freeCompilerArgs +=
        listOf(
          "-P",
          "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
            project.buildDir.absolutePath +
            "/compose_metrics",
        )

      kotlinOptions.freeCompilerArgs +=
        listOf(
          "-P",
          "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
            project.buildDir.absolutePath +
            "/compose_metrics",
        )
    }
  }
}
