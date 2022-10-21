plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "play.pennythdog.gradleplugintestproject"
    compileSdk = 32

    defaultConfig {
        applicationId = "play.pennythdog.gradleplugintestproject"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }

    flavorDimensions += "dim"
    productFlavors {
        create("flavor1") {
            dimension = "dim"
            firebaseAppDistribution {
                releaseNotes = "App flavor1 release"
                testers = "mallardcrash+flavor1@gmail.com"
            }
        }
        create("flavor2") {
            dimension = "dim"
            firebaseAppDistribution {
                releaseNotes = "App flavor2 release"
                testers = "mallardcrash+flavor2@gmail.com"
            }

        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}