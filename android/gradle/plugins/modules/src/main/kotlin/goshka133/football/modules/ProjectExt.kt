package goshka133.football.modules

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

fun Project.getVersionCatalog(): VersionCatalog =
  extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
