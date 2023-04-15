plugins { id("andryuh.football.kotlin") }

dependencies {

    implementationAar(libs.modo)
    implementation(libs.dagger)

    val androidJar: Any by rootProject.extra
    compileOnly(androidJar)
}