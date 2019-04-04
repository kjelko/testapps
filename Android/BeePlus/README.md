# Bee Plus
Bee Plus is a low-quality quiz app that shows a picture and allows you to guess what it is.

## Updating the App
1. Bump the build version (`versionCode`) in the `app/build.gradle` file.
2. (OPTIONAL) Update the picture called `something` at [app/main/res/drawable-xxhdpi/](http://go/gh/crashlytics/app-distro-apps/tree/master/Android/BeePlus/app/src/main/res/drawable-xxhdpi).
3. (OPTIONAL) Update the four answer options at [app/src/main/res/layout/activity_main.xml](https://github.com/crashlytics/app-distro-apps/blob/174ab77e9bb1bf9ee0eff60ff887b15bb9dae2fa/Android/BeePlus/app/src/main/res/layout/activity_main.xml#L34-L72).
4. Create a PR and submit the above changes.

## Building a new APK
1. After updating the version, click on Build > Build Bundle(s) / APK(s) > Build APK(s).
2. Once the build is complete, a pop-up will appear in the bottom-right corner. Click "Locate" to see the `app-debug.apk` file that was generated. You'll upload this file in the next section.

## Distributing via Firebase Console
1. Make sure you have access to the FadApps project in Firebase. This will give you access to the [App Distribution dashboard (Bee Plus Android)](http://firebase/u/0/project/fadapps-2a288/appdistribution/app/android:dev.firebase.beeplus/releases).
2. Upload the APK that you generated in the previous section through the Firebase Console.

## Distributing via Gradle
TBD
