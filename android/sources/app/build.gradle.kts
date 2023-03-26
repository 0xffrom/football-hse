plugins { id("goshka133.football.app") }

android { namespace = "goshka133.football.app" }

dependencies {
  implementation(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)

  implementation(libs.compose.activity)
  implementation(libs.bundles.compose)
  implementation(libs.coroutines)

  implementation(libs.dagger)
  kapt(libs.dagger.compiler)

  implementation(libs.modo)
  implementation(libs.kotlin.immutable)
  implementation(project(":ui_kit"))

  // #region Di Dependencies
  implementation(project(":core_di"))
  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))
  implementation(project(":core_network"))

  implementation(libs.okhttp)
  implementation(libs.retrofit)

  implementation(project(":domain_auth"))
  implementation(project(":feature_auth"))
  implementation(project(":domain_main"))
  implementation(project(":feature_main"))
  implementation(project(":domain_chat"))
  implementation(project(":feature_chat"))
  implementation(project(":domain_search"))
  implementation(project(":feature_search"))
  implementation(project(":domain_profile"))
  implementation(project(":feature_profile"))
}
