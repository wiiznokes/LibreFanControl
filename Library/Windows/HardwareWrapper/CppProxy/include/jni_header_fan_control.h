#ifndef JNI_HEADER_FILE_H
#define JNI_HEADER_FILE_H

#include "jni/jni.h"
#include "jni/jni_md.h"

extern "C"
JNIEXPORT void JNICALL
Java_hardware_external_ExternalWindows_externalStart(JNIEnv*, jobject, jintArray);

extern "C"
JNIEXPORT void JNICALL
Java_hardware_external_ExternalWindows_externalStop(JNIEnv*, jobject);

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_hardware_external_ExternalWindows_externalGetFan(JNIEnv*, jobject);

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_hardware_external_ExternalWindows_externalGetTemp(JNIEnv*, jobject);

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_hardware_external_ExternalWindows_externalGetControl(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_hardware_external_ExternalWindows_externalUpdateFan(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_hardware_external_ExternalWindows_externalUpdateTemp(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_hardware_external_ExternalWindows_externalUpdateControl(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_hardware_external_ExternalWindows_externalSetControl(JNIEnv*, jobject, jint, jboolean, jint);
#endif // JNI_HEADER_FILE_H
