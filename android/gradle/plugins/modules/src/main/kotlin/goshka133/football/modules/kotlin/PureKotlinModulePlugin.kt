package goshka133.football.modules.kotlin

import goshka133.football.modules.ProjectDefaults
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class PureKotlinModulePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.plugins) {
      apply("kotlin")
      apply("kotlin-parcelize")
    }
    project.tasks.withType(JavaCompile::class.java) {
      sourceCompatibility = ProjectDefaults.JavaVersion.toString()
      targetCompatibility = ProjectDefaults.JavaVersion.toString()
    }
    project.tasks.withType(KotlinCompile::class.java) {
      kotlinOptions.jvmTarget = ProjectDefaults.JavaVersion.toString()
      kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
  }
}
