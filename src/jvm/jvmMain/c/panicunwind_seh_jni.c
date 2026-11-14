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

JNIEXPORT jbyte JNICALL Java_io_github_kotlinmania_rustlang_panicunwind_seh_SehJniBridge_panicunwindSehGetTYPEINFOVTABLE(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "??_7type_info@@6B@");
        if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "_??_7type_info@@6B@");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "??_7type_info@@6B@");
            if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "_??_7type_info@@6B@");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "??_7type_info@@6B@");
    }
#endif
    uint8_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : 0;
    return (jbyte)km_res;
}

