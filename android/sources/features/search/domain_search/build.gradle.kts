plugins { id("goshka133.football.kotlin") }

dependencies {
    implementationAar(libs.modo)

    implementation(project(":core_models"))
}
