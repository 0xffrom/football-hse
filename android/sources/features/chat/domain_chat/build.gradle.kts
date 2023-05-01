plugins { id("andryuh.football.kotlin") }

dependencies {
    implementation(libs.coroutines)
    implementation(libs.dagger)
    implementation(libs.datastore.core)
    implementation(libs.retrofit)
    implementation(libs.kotlin.date)
    implementationAar(libs.timber)

    api(libs.kotlin.serializer)

    implementationAar(libs.modo)

    implementation(project(":core_auth"))
    implementation(project(":core_kotlin"))
    implementation(project(":core_network"))
    implementation(project(":core_models"))

    implementation(project(":domain_profile"))

    val androidJar: Any by rootProject.extra
    compileOnly(androidJar)
}
