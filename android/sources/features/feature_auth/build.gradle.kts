plugins { id("goshka133.football.lib") }

android { namespace = "goshka133.football.feature_auth" }

dependencies {
    implementation(libs.modo)
    implementation(libs.dagger)

    implementation(project(":domain_auth"))
}
