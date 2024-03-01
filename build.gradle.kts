// Top-level build file where you can add configuration options common to all sub-projects/modules. dependencies {
//        classpath("io.realm:realm-gradle-plugin:10.9.0")
//    }
buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("io.realm:realm-gradle-plugin:10.9.0")
        classpath("com.google.gms:google-services:4.4.1")

    }
}


plugins {

    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id ("io.realm.kotlin") version "1.11.0" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false

}
