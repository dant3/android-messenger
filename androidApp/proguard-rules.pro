# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable,InnerClasses

# kotlin-reflect ---
-keep class kotlin.reflect.** { *; }
-keep class kotlin.Metadata { *; }
-keepattributes *Annotation*

# Kotlin+jdk ---
# Kotlin library can change runtime behaviour based on whether Class.forName("java.lang.ClassValue") returns a class or not.
# https://developer.android.com/about/versions/14/behavior-changes-14#core-libraries
-keep class java.lang.ClassValue { *; }
# ---

# WebRTC ---
-keep class org.webrtc.**
-keepclassmembers class org.webrtc.** { *; }
# ---

# kotlinx.coroutines ---
# https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/kotlinx-coroutines-android/README.md#optimization
-assumenosideeffects class kotlinx.coroutines.DebugKt {
    boolean getDEBUG() return true;
    boolean getRECOVER_STACK_TRACES() return true;
}
# ---

# kotlinx-serialization-json ---
# https://github.com/Kotlin/kotlinx.serialization#gradle-with-plugins-block
# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
   static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
   static **$* *;
}
-keepclassmembers class <2>$<3> {
   kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
   public static ** INSTANCE;
}
-keepclassmembers class <1> {
   public static <1> INSTANCE;
   kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
# ---

# https://issuetracker.google.com/issues/144631039
#-shrinkunusedprotofields
-keepclassmembers class androidx.datastore.preferences.** { <fields>; }
-keepclassmembers public class * extends com.google.protobuf.MessageLite { *; }
-keepclassmembers public class * extends com.google.protobuf.MessageLiteOrBuilder { *; }
-keepclassmembers public class * extends com.google.protobuf.ProtocolMessageEnum { *; }
-keep class com.google.rpc.** { *; }
-keep class com.google.api.** { *; }
-keep class com.google.protobuf.** { *; }
# ---


# --- logback ---
-keep class ch.qos.logback.** { *; }
-dontwarn javax.mail.**
