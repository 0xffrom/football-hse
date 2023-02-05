@file:Suppress("UnstableApiUsage")

package goshka133.football.modules.android

import com.android.build.gradle.LibraryExtension
import goshka133.football.modules.getVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidLibModulePlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project.plugins) {
      apply("com.android.library")
      apply("goshka133.football.base")
    }
    project.extensions.configure(LibraryExtension::class.java) { buildFeatures { compose = true } }
    project.getVersionCatalog().findBundle("compose").ifPresent { bundle ->
      project.dependencies.add("implementation", bundle)
    }
  }
}
