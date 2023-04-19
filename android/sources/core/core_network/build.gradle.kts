plugins { id("andryuh.football.kotlin") }

dependencies {
  implementation(libs.dagger)
  kapt(libs.dagger.compiler)

  implementation(libs.coroutines)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit)
  implementation(libs.retrofit.serialization)
  implementation(libs.kotlin.serializer)

  implementation(project(":core_auth"))
  implementation(project(":core_models"))

  val androidJar: Any by rootProject.extra
  compileOnly(androidJar)
}
