plugins { id("andryuh.football.kotlin") }

dependencies {
  implementation(libs.coroutines)
  implementation(libs.dagger)
  implementation(libs.datastore.core)
  implementation(libs.retrofit)

  api(libs.kotlin.serializer)

  implementationAar(libs.datastore.preferences)
  implementationAar(libs.modo)

  implementation(project(":core_auth"))
  implementation(project(":core_kotlin"))
  implementation(project(":core_network"))

  val androidJar: Any by rootProject.extra
  compileOnly(androidJar)
}
