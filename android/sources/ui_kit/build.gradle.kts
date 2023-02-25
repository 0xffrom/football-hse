plugins { id("goshka133.football.lib") }

android { namespace = "goshka133.footbal.core_di" }

dependencies {
    annotationProcessor(libs.lifecycle.compiler)

    api(libs.lifecycle.viewmodel)
    api(libs.lifecycle.viewmodel.compose)
    api(libs.modo)
}
