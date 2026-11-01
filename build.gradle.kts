plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ridecuenca.app"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.ridecuenca.app"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
