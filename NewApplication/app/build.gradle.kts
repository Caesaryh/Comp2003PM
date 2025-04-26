import org.jetbrains.kotlin.cfg.pseudocode.and

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
}


android {
    namespace = "com.example.pmanager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pmanager"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }


}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test3)
    testImplementation(libs.app.turbine)
    androidTestImplementation(libs.app.turbine)
}


// room
dependencies {

    implementation(libs.androidx.room.runtime)

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)

    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    annotationProcessor(libs.androidx.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // optional - RxJava2 support for Room
    implementation(libs.androidx.room.rxjava2)

    // optional - RxJava3 support for Room
    implementation(libs.androidx.room.rxjava3)

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(libs.androidx.room.guava)

    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)
}

//Material 3
dependencies {
    androidTestImplementation(libs.androidx.navigation.navigation.testing)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.ui.test)
    androidTestImplementation(libs.androidx.core.testing)
    implementation(libs.runner)
    implementation(libs.test.runner)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.core)
    implementation(libs.room.testing)
    implementation(libs.androidx.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.androidx.navigation.common)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.core.android)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size.class1)
    implementation(libs.androidx.material3.adaptive.navigation.suite)

}
