plugins { id("andryuh.football.kotlin") }

dependencies {
  implementation(libs.coroutines)
  implementation(libs.dagger)
  implementation(libs.datastore.core)
  implementationAar(libs.datastore.preferences)
  implementation(libs.retrofit)

  implementation(libs.kotlin.serializer)

  implementationAar(libs.modo)

  implementation(project(":core_auth"))
  implementation(project(":core_network"))
  implementation(project(":core_models"))
  implementation(project(":core_kotlin"))

  val androidJar: Any by rootProject.extra
  compileOnly(androidJar)
}
