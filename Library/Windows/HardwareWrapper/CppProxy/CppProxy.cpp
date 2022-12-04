#include "pch.h"

// header JNI
#include "include/jni_header_fan_control.h"
#include <msclr/marshal_cppstd.h>
#include <iostream>


using namespace System;
using namespace HardwareLib;

// max values
constexpr int array_size = 25;

// array updated in C# with new values
int c_array[array_size];

// array declared in Kotlin (c_array values are set in this array)
jintArray values;

/*
    size tab to use SetIntArrayRegion on values
    initialisation after discovery of hardware
*/

int size_fan_array;
int size_temp_array;
int size_control_array;

JNIEXPORT void JNICALL
Java_external_ExternalWindows_externalStart(JNIEnv* env, jobject o, const jintArray _values)
{
    Api::Start();

    // int array declared in Kotlin witch serve for transferring updated values
    values = static_cast<jintArray>(env->NewGlobalRef(_values));
}


JNIEXPORT void JNICALL
Java_external_ExternalWindows_externalStop(JNIEnv* env, jobject o)
{
    Api::Stop();

    env->DeleteGlobalRef(values);
}


// there is likely some memory leaks in this 3 next functions :(

JNIEXPORT jobjectArray JNICALL
Java_external_ExternalWindows_externalGetFan(JNIEnv* env, jobject o)
{
    array<String^>^ fan_list = Api::GetFanList();

    // the len of "fanList" is in index [0]
    // we need the len to create the returned array

    String^ str_len_managed = fan_list[0];
    const auto str_len = msclr::interop::marshal_as<std::string>(str_len_managed);
    const int len = atoi(str_len.c_str());
    size_fan_array = (len - 1) / 3;
    // len is len - 1 because the index[0] is ignored
    const jobjectArray fan_list_java = env->NewObjectArray(len - 1, env->FindClass("java/lang/String"), nullptr);
    for (int i = 1; i < len; i++)
    {
        String^ str_managed = fan_list[i];
        auto str_c = msclr::interop::marshal_as<std::string>(str_managed);
        const jstring str = env->NewStringUTF(str_c.c_str());
        env->SetObjectArrayElement(fan_list_java, i - 1, str);
    }
    return fan_list_java;
}

JNIEXPORT jobjectArray JNICALL
Java_external_ExternalWindows_externalGetTemp(JNIEnv* env, jobject o)
{
    array<String^>^ temp_list = Api::GetTempList();

    String^ str_len_managed = temp_list[0];
    const auto str_len = msclr::interop::marshal_as<std::string>(str_len_managed);

    const int len = atoi(str_len.c_str());
    size_temp_array = (len - 1) / 3;
    const jobjectArray temp_list_java = env->NewObjectArray(len - 1, env->FindClass("java/lang/String"), nullptr);

    for (int i = 1; i < len; i++)
    {
        String^ str_managed = temp_list[i];
        auto str_c = msclr::interop::marshal_as<std::string>(str_managed);

        const jstring str = env->NewStringUTF(str_c.c_str());
        env->SetObjectArrayElement(temp_list_java, i - 1, str);
    }
    return temp_list_java;
}


JNIEXPORT jobjectArray JNICALL
Java_external_ExternalWindows_externalGetControl(JNIEnv* env, jobject o)
{
    array<String^>^ control_list = Api::GetControlList();
    String^ str_len_managed = control_list[0];
    const auto str_len = msclr::interop::marshal_as<std::string>(str_len_managed);

    const int len = atoi(str_len.c_str());
    size_control_array = (len - 1) / 3;
    const jobjectArray control_list_java = env->NewObjectArray(len - 1, env->FindClass("java/lang/String"), nullptr);

    for (int i = 1; i < len; i++)
    {
        String^ str_managed = control_list[i];
        auto str_c = msclr::interop::marshal_as<std::string>(str_managed);

        const jstring str = env->NewStringUTF(str_c.c_str());
        env->SetObjectArrayElement(control_list_java, i - 1, str);
    }
    return control_list_java;
}


JNIEXPORT void JNICALL
Java_external_ExternalWindows_externalUpdateFan(JNIEnv* env, jobject o)
{
    Api::UpdateFan(c_array);
    env->SetIntArrayRegion(values, 0, size_fan_array, reinterpret_cast<const jint*>(c_array));
}


JNIEXPORT void JNICALL
Java_external_ExternalWindows_externalUpdateTemp(JNIEnv* env, jobject o)
{
    Api::UpdateTemp(c_array);
    env->SetIntArrayRegion(values, 0, size_temp_array, reinterpret_cast<const jint*>(c_array));
}

JNIEXPORT void JNICALL
Java_external_ExternalWindows_externalUpdateControl(JNIEnv* env, jobject o)
{
    Api::UpdateControl(c_array);
    env->SetIntArrayRegion(values, 0, size_control_array, reinterpret_cast<const jint*>(c_array));
}

JNIEXPORT void JNICALL
Java_external_ExternalWindows_externalSetControl(JNIEnv* env, jobject o, const jint index,
                                                          const jboolean is_auto, const jint value)
{
    Api::SetControl(index, is_auto, value);
}
