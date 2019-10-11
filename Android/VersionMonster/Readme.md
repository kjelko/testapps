# Word of the Day


This is a test project intended to exercise the case when a testing app has a fixed
`versionCode` for all releases.  This is common for internal testing as it allows uncoordinated
collaboration (devs don't have to agree on version ordering) as well as making changing versions
consistent and easy.



### How to use:

Please change the Word of the Day in MainActivity.xml, or string resources, and change the 
`versionName` as you'd like, but don't change the `versionCode` so that all versions are the same.

Additionally, please use the checked-in keystore for distributing so that all releases share a signing
key.

`./gradlew clean assembleRelease appDistributionUploadRelease`