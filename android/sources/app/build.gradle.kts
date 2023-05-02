plugins { id("andryuh.football.app") }

android {
  namespace = "andryuh.football.app"

  defaultConfig {
    versionName = "1.0.4"
    versionCode = 8
  }
  buildTypes {
    debug {
      isDebuggable = true
      isMinifyEnabled = false
      isShrinkResources = false
      applicationIdSuffix = ".debug"

      signingConfig = (signingConfigs.getByName("debug"))
    }

    release {
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
      matchingFallbacks.add("debug")
      signingConfig = (signingConfigs.getByName("debug"))
    }
  }
}

dependencies {
  implementation(libs.android.startup)
  implementation(libs.bundles.compose)
  implementation(libs.bundles.elmslie)
  implementation(libs.coil)
  implementation(libs.compose.activity)
  implementation(libs.coroutines)
  implementation(libs.dagger)
  implementation(libs.datastore)
  implementation(libs.datastore.security)
  implementation(libs.datastore.security.preferences)
  implementation(libs.datastore.core)
  implementation(libs.datastore.preferences)
  implementation(libs.kotlin.immutable)
  implementation(libs.kotlin.date)
  implementation(libs.kotlin.serializer)
  implementation(libs.lifecycle.compiler)
  implementation(libs.lifecycle.viewmodel)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.modo)
  implementation(libs.okhttp)
  implementation(libs.retrofit)
  implementation(libs.timber)
  implementation(libs.tink)

  kapt(libs.dagger.compiler)

  implementation(project(":ui_kit"))
  // #region Di Dependencies
  implementation(project(":core_auth"))
  implementation(project(":core_di"))
  implementation(project(":core_elmslie"))
  implementation(project(":core_navigation"))
  implementation(project(":core_network"))

  implementation(project(":domain_auth"))
  implementation(project(":domain_chat"))
  implementation(project(":domain_main"))
  implementation(project(":domain_profile"))
  implementation(project(":domain_search"))
  implementation(project(":domain_team"))
  implementation(project(":feature_auth"))
  implementation(project(":feature_chat"))
  implementation(project(":feature_main"))
  implementation(project(":feature_profile"))
  implementation(project(":feature_search"))
  implementation(project(":feature_team"))
}
