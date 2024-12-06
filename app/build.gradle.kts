plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.expensemanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.expensemanagement"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // thư viện retrofit
    // Thêm thư viện jbcrypt
    implementation("org.mindrot:jbcrypt:0.4")  // Đây là cách thêm bcrypt vào dependencies
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


}

