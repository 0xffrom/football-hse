package goshka133.football.modules.kotlin

import goshka133.football.modules.getVersionCatalog
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

internal class KotlinComposePlugin : KotlinCompilerPluginSupportPlugin {

  private var composeCompilerVersion: String? = null

  override fun apply(target: Project) {
    val libs = target.getVersionCatalog()

    composeCompilerVersion = libs.findVersion("compose-compiler").get().requiredVersion

    libs.findLibrary("compose-runtime").ifPresent { dependencyProvider ->
      val dependency = dependencyProvider.get().toString()

      target.dependencies.add("implementationAar", dependency)
    }
  }

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>,
  ): Provider<List<SubpluginOption>> = kotlinCompilation.target.project.provider { emptyList() }

  override fun getCompilerPluginId() = "androidx.compose.compiler"

  override fun getPluginArtifact() =
    SubpluginArtifact(
      groupId = "androidx.compose.compiler",
      artifactId = "compiler",
      version = composeCompilerVersion,
    )

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = true
}
