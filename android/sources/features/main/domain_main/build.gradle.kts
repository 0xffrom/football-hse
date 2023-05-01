plugins { id("andryuh.football.kotlin") }

dependencies {
    implementationAar(libs.modo)

    implementation(libs.coroutines)
    implementation(libs.dagger)
}
