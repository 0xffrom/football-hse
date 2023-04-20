plugins { id("andryuh.football.lib") }

android {
  namespace = "andryuh.football.feature_search"

  testOptions { unitTests.all { it.useJUnitPlatform() } }
}

dependencies {
  implementation(libs.modo)
  implementation(libs.dagger)
  kapt(libs.dagger.compiler)
  implementation(libs.coil)
  implementation(libs.retrofit)

  annotationProcessor(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.kotlin.immutable)
  implementation(libs.compose.activity)
  implementation(libs.bundles.elmslie)

  implementation(project(":ui_kit"))

  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))
  implementation(project(":core_di"))
  implementation(project(":core_kotlin"))
  implementation(project(":core_models"))

  implementation(project(":domain_search"))
  implementation(project(":domain_team"))
  implementation(project(":domain_profile"))

  testImplementation(libs.bundles.kotest)
}
