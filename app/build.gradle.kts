plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.pantrypro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pantrypro"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // add image from online source
    implementation ("com.squareup.picasso:picasso:2.8")

    // HSQLDB
    implementation(group = "org.hsqldb", name = "hsqldb", version = "2.4.1")

    // online database
    implementation("com.google.firebase:firebase-database:20.3.0")
//    implementation("com.github.bumptech.glide:glide:4.14.2")
//    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
//    implementation("com.github.clans:fab:1.6.4")

    //for System testing
    implementation("androidx.test.espresso:espresso-intents:3.6.0-alpha03")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("androidx.test.espresso:espresso-contrib:3.6.0-alpha03")
    // junit testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha03")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha03")
    testImplementation("org.mockito:mockito-core:3.10.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.0-alpha03")

}