import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val weatherApiKey = localProperties.getProperty("WEATHER_API_KEY", "")
android {
    namespace = "com.example.weathernow"

    compileSdk {
        version = release(37)
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.weathernow"
        minSdk = 37
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "WEATHER_API_KEY",
            "\"${weatherApiKey}\""
        )
    }

    buildTypes {

        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.recyclerview)
    implementation(libs.okhttp)
    testImplementation(libs.junit)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
