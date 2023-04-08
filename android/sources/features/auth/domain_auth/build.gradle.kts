plugins { id("goshka133.football.kotlin") }

dependencies {
  implementation(libs.coroutines)
  implementation(libs.dagger)
  implementation(libs.datastore.core)
  implementation(libs.kotlin.serializer)
  implementation(libs.retrofit)

  implementationAar(libs.datastore.preferences)
  implementationAar(libs.modo)
  kapt(libs.dagger.compiler)

  implementation(project(":core_navigation"))

  val androidJar: Any by rootProject.extra
  compileOnly(androidJar)
}
