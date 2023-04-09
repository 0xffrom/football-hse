plugins { id("goshka133.football.lib") }

android {
  namespace = "goshka133.football.feature_auth"

  testOptions { unitTests.all { it.useJUnitPlatform() } }
}

dependencies {
  implementation(libs.modo)
  implementation(libs.dagger)

  annotationProcessor(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.compose.activity)
  implementation(libs.bundles.elmslie)

  implementation(project(":ui_kit"))

  implementation(project(":core_di"))
  implementation(project(":core_auth"))

  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))

  implementation(project(":domain_auth"))
  implementation(project(":domain_main"))
  implementation(project(":domain_profile"))

  testImplementation(libs.bundles.kotest)
}
