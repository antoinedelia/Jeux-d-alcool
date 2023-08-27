# Jeux d'alcool

https://play.google.com/store/apps/details?id=com.antoinedelia.lebarbu_versionalcool

Boire avec modération

## How to deploy new app

Before anything, make sure to bump the version of the app in the following places:
- "Jeux-d-alcool\app\build.gradle"
  - Bump `versionCode` 
  - Bump `versionName`
- "Jeux-d-alcool\app\build\generated\source\buildConfig\debug\com\antoinedelia\lebarbu_versionalcool\BuildConfig.java"
  - Bump `VERSION_CODE`
  - Bump `VERSION_NAME`

1. Build -> Generate Signed Bundle / APK...
2. Select "Android App Bundle" -> Next
3. Under Key store path, use the `.jks` file
4. Enter the password for the `.jks` file
5. Click on the "browse" icon to select the "Key alias"
6. Enter the key password -> Next
7. Select "release" -> Create
8. This will create a new `.aab` file. This file is required to upload a new version of the app in the Google Play Store.