plugins { id("andryuh.football.lib") }

android {
  namespace = "andryuh.football.feature_profile"

  testOptions { unitTests.all { it.useJUnitPlatform() } }
}

dependencies {
  implementation(libs.modo)
  implementation(libs.dagger)
  kapt(libs.dagger.compiler)
  implementation(libs.retrofit)

  kapt(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.timber)
  implementation(libs.kotlin.immutable)
  implementation(libs.compose.activity)
  implementation(libs.coil)
  implementation(libs.bundles.elmslie)

  implementation(project(":ui_kit"))

  implementation(project(":core_di"))
  implementation(project(":core_kotlin"))
  implementation(project(":core_models"))
  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))

  implementation(project(":domain_auth"))
  implementation(project(":domain_search"))
  implementation(project(":domain_profile"))
  implementation(project(":domain_team"))
  implementation(project(":domain_chat"))
  implementation(project(":domain_main"))

  testImplementation(libs.bundles.kotest)
}
