plugins { id("andryuh.football.lib") }

android { namespace = "andryuh.football.core_elmslie" }

dependencies {
  implementation(libs.dagger)

  kapt(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.bundles.elmslie)
  implementation(libs.modo)

  implementation(project(":core_di"))
}
