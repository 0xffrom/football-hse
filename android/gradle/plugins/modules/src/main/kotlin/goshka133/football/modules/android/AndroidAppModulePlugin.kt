@file:Suppress("UnstableApiUsage")

package goshka133.football.modules.android

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidAppModulePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.plugins) {
      apply("com.android.application")
      apply("goshka133.football.base")
    }
    project.extensions.configure(ApplicationExtension::class.java) {
      buildFeatures { compose = true }
    }
  }
}
