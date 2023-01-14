plugins { id("goshka133.football.app") }

android { namespace = "goshka133.football.app" }

dependencies {
  implementation(libs.compose.activity)
  implementation(libs.bundles.compose)
  implementation(libs.coroutines)
}
