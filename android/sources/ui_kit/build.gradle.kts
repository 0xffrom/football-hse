plugins { id("andryuh.football.lib") }

android { namespace = "andryuh.football.ui_kit" }

dependencies {
  kapt(libs.lifecycle.compiler)

  implementation(libs.accompanist.systemuicontroller)
  api(libs.lifecycle.viewmodel)
  api(libs.lifecycle.viewmodel.compose)
  api(libs.modo)
}
