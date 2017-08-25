@echo off
cd app/build/outputs/apk
adb install -r app-debug.apk
adb shell am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n com.fiveixlg.app.customer/com.hentica.app.framework.activity.WelcomeActivity
pause