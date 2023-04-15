plugins { `kotlin-dsl` }

repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}

dependencies {
  implementation(kotlin("gradle-plugin"))
  implementation("com.android.tools.build:gradle:7.4.2")
}

gradlePlugin {
  plugins {
    create("android-base") {
      id = "andryuh.football.base"
      displayName = "Android Base Module Plugin"
      implementationClass = "andryuh.football.modules.android.AndroidBaseModulePlugin"
    }
    create("android-app") {
      id = "andryuh.football.app"
      displayName = "Android App Module Plugin"
      implementationClass = "andryuh.football.modules.android.AndroidAppModulePlugin"
    }
    create("android-lib") {
      id = "andryuh.football.lib"
      displayName = "Android Lib Module Plugin"
      implementationClass = "andryuh.football.modules.android.AndroidLibModulePlugin"
    }
    create("pure-kotlin") {
      id = "andryuh.football.kotlin"
      displayName = "Pure Kotlin Module Plugin"
      implementationClass = "andryuh.football.modules.kotlin.PureKotlinModulePlugin"
    }
  }
}
