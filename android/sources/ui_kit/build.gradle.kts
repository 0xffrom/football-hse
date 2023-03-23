plugins { id("goshka133.football.lib") }

android { namespace = "goshka133.football.ui_kit" }

dependencies {
  annotationProcessor(libs.lifecycle.compiler)

  implementation(libs.accompanist.systemuicontroller)
  api(libs.lifecycle.viewmodel)
  api(libs.lifecycle.viewmodel.compose)
  api(libs.modo)
}
