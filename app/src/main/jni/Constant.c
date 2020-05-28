#include <jni.h>

// Staging
JNIEXPORT jstring JNICALL
Java_com_kreditplus_eform_helper_JniUtil_getApiUrlStaging(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "http://eform-api.stagingapps.net/");
}

// Staging
JNIEXPORT jstring JNICALL
Java_com_kreditplus_eform_helper_JniUtil_getApiUrlUAT(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "http://182.23.26.234:83/");
}

// Production
JNIEXPORT jstring JNICALL
Java_com_kreditplus_eform_helper_JniUtil_getApiUrlProduction(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "http://36.37.104.180:83/");//http://36.37.104.180:83/

    // Pra Production
    // http://36.37.104.180:9000/
}
