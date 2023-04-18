plugins { id("andryuh.football.kotlin") }

dependencies {
    implementation(libs.coroutines)
    implementation(libs.dagger)
    implementation(libs.retrofit)
    implementationAar(libs.modo)

    api(libs.kotlin.serializer)

    implementation(project(":core_auth"))
    implementation(project(":core_kotlin"))
    implementation(project(":core_network"))
    implementation(project(":core_models"))

    implementation(project(":domain_team"))
}
