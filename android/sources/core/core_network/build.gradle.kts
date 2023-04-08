plugins { id("goshka133.football.kotlin") }

dependencies {
  implementation(libs.dagger)
  kapt(libs.dagger.compiler)

  implementation(libs.coroutines)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit)
  implementation(libs.retrofit.serialization)
  implementation(libs.kotlin.serializer)

  implementation(project(":domain_auth"))
}
