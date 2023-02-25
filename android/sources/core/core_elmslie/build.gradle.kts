plugins { id("goshka133.football.lib") }

android { namespace = "goshka133.football.core_elmslie" }

dependencies {
  implementation(libs.dagger)

  annotationProcessor(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.bundles.elmslie)

  implementation(project(":core_di"))
}
