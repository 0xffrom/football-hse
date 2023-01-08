@file:Suppress("UnstableApiUsage")

package goshka133.football.modules.android

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibModulePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.plugins) {
      apply("com.android.library")
      apply("android-base")
    }
    project.extensions.configure(LibraryExtension::class.java) { buildFeatures { compose = true } }
  }
}
