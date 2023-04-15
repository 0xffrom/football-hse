package andryuh.football.modules.kotlin

import andryuh.football.modules.getVersionCatalog
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.JetBrainsSubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

internal class KotlinParcelizePlugin : KotlinCompilerPluginSupportPlugin {

  override fun apply(target: Project) {
    val libs = target.getVersionCatalog()

    libs.findLibrary("kotlin.parcelize").ifPresent { dependencyProvider ->
      val dependency = dependencyProvider.get().toString()

      target.dependencies.add("implementation", dependency)
    }
  }

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>,
  ): Provider<List<SubpluginOption>> = kotlinCompilation.target.project.provider { emptyList() }

  override fun getCompilerPluginId() = "org.jetbrains.kotlin.parcelize"

  override fun getPluginArtifact() = JetBrainsSubpluginArtifact("kotlin-parcelize-compiler")

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = true
}
