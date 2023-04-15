package andryuh.football.modules.kotlin

import andryuh.football.modules.ProjectDefaults
import andryuh.football.modules.getVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class PureKotlinModulePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.plugins) {
      apply("kotlin")
      apply("com.stepango.aar2jar")
      apply(KotlinParcelizePlugin::class.java)
      apply(KotlinComposePlugin::class.java)
      apply("org.jetbrains.kotlin.kapt")
      apply("org.jetbrains.kotlin.plugin.serialization")
    }
    val libs = project.getVersionCatalog()

    libs.findLibrary("compose-runtime").ifPresent { library ->
      project.dependencies.add("implementationAar", library)
    }
    libs.findLibrary("kotlin-immutable").ifPresent { library ->
      project.dependencies.add("implementation", library)
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
