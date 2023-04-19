plugins { `kotlin-dsl` }

repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
  maven("https://jitpack.io")
}

dependencies {
  implementation(kotlin("gradle-plugin"))
  implementation("com.android.tools.build:gradle:8.0.0")
  implementation("com.github.andryuh:aar2jar:0.7.3")
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
    create("aar2jar") {
      id = "com.andryuh.aar2jar"
      displayName = "Aar2Jar"
      description = "Transforms Aar's to Jar's"
      implementationClass = "com.andryuh.aar2jar.Aar2Jar"
    }
  }
}
