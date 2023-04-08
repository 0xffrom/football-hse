plugins { id("goshka133.football.kotlin") }

dependencies {

    implementationAar(libs.modo)
    implementation(libs.dagger)

    val androidJar: Any by rootProject.extra
    compileOnly(androidJar)
}