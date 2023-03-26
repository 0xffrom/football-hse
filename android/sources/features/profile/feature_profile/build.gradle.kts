plugins { id("goshka133.football.lib") }

android {
  namespace = "goshka133.football.feature_profile"

  testOptions { unitTests.all { it.useJUnitPlatform() } }
}

dependencies {
  implementation(libs.modo)
  implementation(libs.dagger)

  annotationProcessor(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.kotlin.immutable)
  implementation(libs.compose.activity)
  implementation(libs.coil)
  implementation(libs.bundles.elmslie)

  implementation(project(":ui_kit"))

  implementation(project(":core_models"))
  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))

  implementation(project(":domain_profile"))
  implementation(project(":domain_team"))

  testImplementation(libs.bundles.kotest)
}
