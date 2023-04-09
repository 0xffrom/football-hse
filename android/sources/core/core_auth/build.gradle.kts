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
  implementation(libs.datastore.core)

  implementationAar(libs.modo)
  implementationAar(libs.datastore.preferences)

  implementation(project(":core_navigation"))
}
