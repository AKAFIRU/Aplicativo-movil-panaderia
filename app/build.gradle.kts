plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.panmovil_panitasproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.panmovil_panitasproject"
        minSdk = 24
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

    packagingOptions {
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")
        // Puedes agregar más exclusiones si es necesario
    }

}

dependencies {

    /*implementation ("androidx.activity:activity:1.3.1")*/

    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.android.volley:volley:1.2.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    // Para enviar correos electrónicos en una aplicación Android
    implementation ("com.sun.mail:android-mail:1.6.7")
    implementation ("com.sun.mail:android-activation:1.6.7")
    implementation ("com.airbnb.android:lottie:3.4.0") /*ANIMACIONES*/
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // La lógica de envío de correo en tu actividad
    implementation ("com.sun.mail:android-mail:1.6.7")
    implementation ("com.sun.mail:android-activation:1.6.7")
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation("androidx.gridlayout:gridlayout:1.0.0") /* FORMATO JSON */
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}