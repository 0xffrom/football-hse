plugins { id("goshka133.football.lib") }

android {
  namespace = "goshka133.football.feature_search"

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
  implementation(libs.bundles.elmslie)

  implementation(project(":ui_kit"))

  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))

  implementation(project(":domain_search"))

  testImplementation(libs.bundles.kotest)
}
