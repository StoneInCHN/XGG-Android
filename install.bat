@echo on
cd app/build/outputs/apk
adb install -r app-debug.apk
pause