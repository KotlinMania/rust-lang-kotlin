#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include <stdbool.h>
#include <stdint.h>
#if defined(__has_include)
#  if __has_include(<jni.h>)
#    include <jni.h>
#    define KM_HAS_JNI_H 1
#  endif
#endif
#ifndef KM_HAS_JNI_H
typedef uint8_t jboolean;
typedef int8_t jbyte;
typedef uint16_t jchar;
typedef int16_t jshort;
typedef int32_t jint;
typedef int64_t jlong;
typedef float jfloat;
typedef double jdouble;
typedef jint jsize;
typedef void* jobject;
typedef jobject jclass;
typedef jobject jstring;
typedef jobject jthrowable;
typedef jobject jarray;
typedef jarray jbyteArray;
struct JNINativeInterface_;
typedef const struct JNINativeInterface_* JNIEnv;
struct JNINativeInterface_ {
    const char* (*GetStringUTFChars)(JNIEnv*, jstring, jboolean*);
    void (*ReleaseStringUTFChars)(JNIEnv*, jstring, const char*);
};
#ifdef _WIN32
#  define JNIEXPORT __declspec(dllexport)
#  define JNICALL __stdcall
#else
#  define JNIEXPORT __attribute__((visibility("default")))
#  define JNICALL
#endif
#endif
#include <stddef.h>
#include <stdint.h>
#include <stdlib.h>
#include <errno.h>
#ifdef _WIN32
#ifndef WIN32_LEAN_AND_MEAN
#define WIN32_LEAN_AND_MEAN
#endif
#include <windows.h>
#else
#include <dlfcn.h>
#ifndef RTLD_DEFAULT
#define RTLD_DEFAULT ((void*)0)
#endif
#endif

JNIEXPORT jboolean JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_android_b64_LibcUnixLinuxlikeAndroidB64JniBridge_rustlangLibcUnixLinuxlikeAndroidB64SystemPropertyWait(JNIEnv* km_env, jclass km_clazz, jlong pi, jint oldSerial, jlong newSerialPtr, jlong relativeTimeout) {
    (void)km_env;
    (void)km_clazz;
    typedef bool (*km_fn_t)(void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "__system_property_wait");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "__system_property_wait");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "__system_property_wait");
    }
#endif
    bool km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)pi, (uint32_t)oldSerial, (void*)(uintptr_t)newSerialPtr, (void*)(uintptr_t)relativeTimeout);
    } else {
        errno = ENOSYS;
        km_res = false;
    }
    return (jboolean)km_res;
}

