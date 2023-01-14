plugins { `kotlin-dsl` }

repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}

dependencies {
  implementation(kotlin("gradle-plugin"))
  implementation("com.android.tools.build:gradle:7.3.1")
}

gradlePlugin {
  plugins {
    create("android-base") {
      id = "goshka133.football.base"
      displayName = "Android Base Module Plugin"
      implementationClass = "goshka133.football.modules.android.AndroidBaseModulePlugin"
    }
    create("android-app") {
      id = "goshka133.football.app"
      displayName = "Android App Module Plugin"
      implementationClass = "goshka133.football.modules.android.AndroidAppModulePlugin"
    }
    create("android-lib") {
      id = "goshka133.football.lib"
      displayName = "Android Lib Module Plugin"
      implementationClass = "goshka133.football.modules.android.AndroidLibModulePlugin"
    }
    create("pure-kotlin") {
      id = "goshka133.football.kotlin"
      displayName = "Pure Kotlin Module Plugin"
      implementationClass = "goshka133.football.modules.kotlin.PureKotlinModulePlugin"
    }
  }
}
