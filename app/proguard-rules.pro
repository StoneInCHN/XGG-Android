# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}



# ------------------------ app 开始 ---------------------------------------


# ------------------------ 门里 开始 ---------------------------------------
-keepattributes EnclosingMethod
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify                  # 混淆时是否做预校验
# -ignorewarnings                 # 忽略警告
-verbose                         # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending..ILiclicensingensingService    # 保持哪些类不被混淆

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

# 自定义数据结构
-keep public class com.thinkgem.jeesite.modules.** {public private protected *;}

# Orm数据结构
-keep public class com.hentica.app.util.region.Region {public private protected *;}

# -libraryjars ../libs/andBase-release.aar
# -libraryjars ../libs/androidQuery-release.aar
# -libraryjars ../libs/largeImageViewLib-release.aar
# -libraryjars ../libs/pullToRefreshLibrary-release.aar
# -libraryjars ../libs/rangeSeekBarLib-release.aar
# -libraryjars ../libs/viewPagerIndicator-release.aar
# -libraryjars ../libs/wheel-release.aar

#-libraryjars ../libs/geTuiPushSDK-release.aar
#-libraryjars ../libs/rongIMKit-release.aar
-libraryjars ../libs/umengSocialSdkLibrary-release.aar
-libraryjars ../libs/weixinlib-release.aar

#-libraryjars libs/BaiduLBS_Android.jar
#-libraryjars libs/bugly_crash_release__2.1.5.jar
#-libraryjars libs/com.umeng.fb.5.4.0.jar



# ------------------------ 常用库 开始 ---------------------------------------

# ------------------------ LiteOrm 开始 ---------------------------------------
-dontwarn com.litesuits.orm.*
-keep enum com.litesuits.orm.db.annotation.** { *; }
-keep interface com.litesuits.orm.db.annotation.** { *; }
-keepattributes *Annotation*
# ------------------------ LiteOrm 开始 ---------------------------------------

# ------------------------ legarcy网络库 开始 ---------------------------------------
-dontwarn org.apache.**
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-keep class org.apache.**{ *; }
# ------------------------ legarcy网络库 结束 ---------------------------------------

# ------------------------ EventBus 开始 ---------------------------------------
-keepclassmembers,includedescriptorclasses class ** { public void onEvent*(**); }
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# ------------------------ EventBus 结束 ---------------------------------------

# ------------------------ Gilde 开始 ---------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule
# ------------------------ Gilde 结束 ---------------------------------------

# ------------------------ jiecaovideoplayer 开始 ---------------------------------------
-keep class edt.danmaku.ijk.** { *; }
-dontwarn edt.danmaku.ijk.**
# ------------------------ jiecaovideoplayer 结束 ---------------------------------------

# ------------------------ Gson 开始 ---------------------------------------
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keepattributes Signature
-keepattributes *Annotation*
# ------------------------ Gson 结束 ---------------------------------------

# ------------------------ Andbase 开始 ---------------------------------------
-keep public class com.ab.** {public private protected *;}
# ------------------------ Andbase 结束 ---------------------------------------

# ------------------------ AndroidQuery 开始 ---------------------------------------
-keep public class com.androidquery.** {public private protected *;}
# ------------------------ AndroidQuery 结束 ---------------------------------------


# ------------------------ 常用库 结束 ----------------------------------------



# ------------------------ 个推 开始 ---------------------------------------

-dontwarn com.igexin.**
-keep class com.igexin.**{*;}

# ------------------------ 个推 开始 ---------------------------------------

# ------------------------ 有盟分享 开始 ---------------------------------------
 -dontshrink
 -dontoptimize
 -dontwarn com.google.android.maps.**
 -dontwarn android.webkit.WebView
 -dontwarn com.umeng.**
 -dontwarn com.tencent.weibo.sdk.**
 -dontwarn com.facebook.**
 -keep public class javax.**
 -keep public class android.webkit.**
 -dontwarn android.support.v4.**
 -keep enum com.facebook.**
 -keepattributes Exceptions,InnerClasses,Signature
 -keepattributes *Annotation*
 -keepattributes SourceFile,LineNumberTable

 -keep public interface com.facebook.**
 -keep public interface com.tencent.**
 -keep public interface com.umeng.socialize.**
 -keep public interface com.umeng.socialize.sensor.**
 -keep public interface com.umeng.scrshot.**

 -keep public class com.umeng.socialize.* {*;}


 -keep class com.facebook.**
 -keep class com.facebook.** { *; }
 -keep class com.umeng.scrshot.**
 -keep public class com.tencent.** {*;}
 -keep class com.umeng.socialize.sensor.**
 -keep class com.umeng.socialize.handler.**
 -keep class com.umeng.socialize.handler.*
 -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
 -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

 -keep class im.yixin.sdk.api.YXMessage {*;}
 -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

 -dontwarn twitter4j.**
 -keep class twitter4j.** { *; }

 -keep class com.tencent.** {*;}
 -dontwarn com.tencent.**
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep class com.tencent.open.TDialog$*
 -keep class com.tencent.open.TDialog$* {*;}
 -keep class com.tencent.open.PKDialog
 -keep class com.tencent.open.PKDialog {*;}
 -keep class com.tencent.open.PKDialog$*
 -keep class com.tencent.open.PKDialog$* {*;}

 -keep class com.sina.** {*;}
 -dontwarn com.sina.**
 -keep class  com.alipay.share.sdk.** {
    *;
 }
 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }

 -keep class com.linkedin.** { *; }
 -keepattributes Signature
# ------------------------ 有盟分享 结束 ---------------------------------------

# ------------------------ 有盟反馈 开始 ---------------------------------------
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
# ------------------------ 有盟反馈 结束 ---------------------------------------

# ------------------------ bugly 开始 ---------------------------------------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# ------------------------ bugly 结束 ---------------------------------------

# ------------------------ 百度 开始 ---------------------------------------
-keep class com.baidu.**{ *; }
-keep class vi.com.gdi.bgl.android.java.**{ *; }
# ------------------------ 百度地图 结束 ---------------------------------------

# ------------------------ 银联支付 开始 ---------------------------------------
-dontwarn com.unionpay.mobile.android.**
# ------------------------ 银联支付 结束 ---------------------------------------

# ------------------------ 支付宝 开始 ---------------------------------------
-dontwarn com.alipay.android.**
# ------------------------ 支付宝 结束 ---------------------------------------

# ------------------------ butterknife 开始 ---------------------------------------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
# ------------------------ 支付宝 结束 ---------------------------------------

# ------------------------ 自定义 开始 ---------------------------------------
# ------------------------ 自定义 结束 ---------------------------------------