plugins { id("andryuh.football.kotlin") }

dependencies {
  implementation(libs.coroutines)
  implementation(libs.dagger)
  implementation(libs.datastore.core)
  implementation(libs.kotlin.serializer)
  implementation(libs.retrofit)

  implementationAar(libs.datastore.preferences)
  implementationAar(libs.modo)
  kapt(libs.dagger.compiler)

  implementation(project(":core_auth"))
  implementation(project(":core_navigation"))
  implementation(project(":core_network"))

  implementation(project(":domain_profile"))

  val androidJar: Any by rootProject.extra
  compileOnly(androidJar)
}
