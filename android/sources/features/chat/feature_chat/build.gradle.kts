plugins { id("andryuh.football.lib") }

android {
  namespace = "andryuh.football.feature_chat"

  testOptions { unitTests.all { it.useJUnitPlatform() } }
}

dependencies {
  implementation(libs.coil)
  implementation(libs.modo)
  implementation(libs.dagger)
  implementation(libs.kotlin.date)
  implementation(libs.retrofit)

  kapt(libs.lifecycle.compiler)
  kapt(libs.dagger.compiler)

  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.kotlin.immutable)
  implementation(libs.compose.activity)
  implementation(libs.bundles.elmslie)

  implementation(project(":ui_kit"))

  implementation(project(":core_kotlin"))
  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))

  implementation(project(":domain_chat"))

  testImplementation(libs.bundles.kotest)
}
