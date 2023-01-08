#include "pch.h"

// header JNI
#include "include/jni_header_fan_control.h"
#include <msclr/marshal_cppstd.h>
#include <iostream>


using namespace System;
using namespace HardwareLib;

/*
 * Array used to retrieve fans, temps and
 * controls infos. This arrays are declared and freed in C++
 */
jobjectArray fan_list_info;
jobjectArray temp_list_info;
jobjectArray control_list_info;

jclass clazz;

/*
 * Array used to retrieve new values of
 * fans, temps and controls.
 * c_value_array is updated in C#.
 * value_array_returned is declared in Kotlin.
 * It is updated using c_value_array
 */
constexpr int array_size = 25;
int c_value_array[array_size];
jintArray value_array_returned;

/*
 * size tab to use SetIntArrayRegion on values.
 * Their initialisation is done after the discovery
 * of the hardware.
*/
int size_fan_value_array;
int size_temp_value_array;
int size_control_value_array;


JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalStart(JNIEnv* env, jobject o, const jintArray value_array)
{
    Api::Start();
    value_array_returned = static_cast<jintArray>(env->NewGlobalRef(value_array));
    const jclass local_clazz = env->FindClass("java/lang/String");
    clazz = static_cast<jclass>(env->NewGlobalRef(local_clazz));
}


JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalStop(JNIEnv* env, jobject o)
{
    Api::Stop();
    env->DeleteGlobalRef(value_array_returned);
    env->DeleteLocalRef(fan_list_info);
    env->DeleteLocalRef(temp_list_info);
    env->DeleteLocalRef(control_list_info);
    env->DeleteGlobalRef(clazz);
}

void fill_java_list_info(JNIEnv* env, array<String^>^ info_list, int* size, jobjectArray* java_list_info)
{
    /*
     * the len is in index [0]
     * we need the len to create the returned array
     */
    String^ csharp_str_len = info_list[0];
    const auto str_len = msclr::interop::marshal_as<std::string>(csharp_str_len);
    const int len = atoi(str_len.c_str());

    /*
     * final len is divide by 3 because we retrieve
     * index in the lib, LibId, LibName
     */
    *size = (len - 1) / 3;

    // len is len - 1 because the index[0] is ignored
    *java_list_info = env->NewObjectArray(len - 1, clazz, nullptr);
    for (int i = 1; i < len; i++)
    {
        String^ csharp_str = info_list[i];
        auto c_str = msclr::interop::marshal_as<std::string>(csharp_str);
        const jstring final_str = env->NewStringUTF(c_str.c_str());
        env->SetObjectArrayElement(*java_list_info, i - 1, final_str);
        // Free the memory allocated for the str object
        env->DeleteLocalRef(final_str);
    }
}


JNIEXPORT jobjectArray JNICALL
Java_external_windows_ExternalWindows_externalGetFansInfo(JNIEnv* env, jobject o)
{
    // this array is manager by the .NET GB
    array<String^>^ fan_list = Api::GetFanList();
    fill_java_list_info(env, fan_list, &size_fan_value_array, &fan_list_info);
    return fan_list_info;
}


JNIEXPORT jobjectArray JNICALL
Java_external_windows_ExternalWindows_externalGetTempsInfo(JNIEnv* env, jobject o)
{
    array<String^>^ temp_list = Api::GetTempList();
    fill_java_list_info(env, temp_list, &size_temp_value_array, &temp_list_info);
    return temp_list_info;
}


JNIEXPORT jobjectArray JNICALL
Java_external_windows_ExternalWindows_externalGetControlsInfo(JNIEnv* env, jobject o)
{
    array<String^>^ control_list = Api::GetControlList();
    fill_java_list_info(env, control_list, &size_control_value_array, &control_list_info);
    return control_list_info;
}


JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalUpdateFanList(JNIEnv* env, jobject o)
{
    Api::UpdateFan(c_value_array);
    env->SetIntArrayRegion(value_array_returned, 0, size_fan_value_array, reinterpret_cast<const jint*>(c_value_array));
}


JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalUpdateTempList(JNIEnv* env, jobject o)
{
    Api::UpdateTemp(c_value_array);
    env->SetIntArrayRegion(value_array_returned, 0, size_temp_value_array,
                           reinterpret_cast<const jint*>(c_value_array));
}

JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalUpdateControlList(JNIEnv* env, jobject o)
{
    Api::UpdateControl(c_value_array);
    env->SetIntArrayRegion(value_array_returned, 0, size_control_value_array,
                           reinterpret_cast<const jint*>(c_value_array));
}

JNIEXPORT void JNICALL
Java_external_windows_ExternalWindows_externalSetControl(JNIEnv* env, jobject o, const jint index,
                                                 const jboolean is_auto, const jint value)
{
    Api::SetControl(index, is_auto, value);
}
