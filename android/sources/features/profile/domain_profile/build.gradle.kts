plugins { id("goshka133.football.kotlin") }

dependencies {
  implementationAar(libs.modo)

  val androidJar: Any by rootProject.extra
  compileOnly(androidJar)
}
