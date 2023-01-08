#ifndef JNI_HEADER_FILE_H
#define JNI_HEADER_FILE_H

#include "jni/jni.h"
#include "jni/jni_md.h"

extern "C"
JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalStart(JNIEnv*, jobject, jintArray);

extern "C"
JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalStop(JNIEnv*, jobject);

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_external_windows_ExternalWindows_externalGetFansInfo(JNIEnv*, jobject);

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_external_windows_ExternalWindows_externalGetTempsInfo(JNIEnv*, jobject);

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_external_windows_ExternalWindows_externalGetControlsInfo(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalUpdateFanList(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalUpdateTempList(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalUpdateControlList(JNIEnv*, jobject);

extern "C"
JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalSetControl(JNIEnv*, jobject, jint, jboolean, jint);
#endif // JNI_HEADER_FILE_H
