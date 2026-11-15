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

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_std_sys_io_error_unix_StdSysIoErrorUnixJniBridge_rustlangStdSysIoErrorUnixGetLIBCERRNO(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static int32_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "errno");
        if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "_errno");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "errno");
            if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "_errno");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (int32_t*)dlsym(RTLD_DEFAULT, "errno");
    }
#endif
    int32_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : -1;
    return (jint)km_res;
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_std_sys_io_error_unix_StdSysIoErrorUnixJniBridge_rustlangStdSysIoErrorUnixSetLIBCERRNO(JNIEnv* km_env, jclass km_clazz, jint value) {
    (void)km_env;
    (void)km_clazz;
    static int32_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "errno");
        if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "_errno");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "errno");
            if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "_errno");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (int32_t*)dlsym(RTLD_DEFAULT, "errno");
    }
#endif
    if (km_sym_ptr != NULL) {
        *km_sym_ptr = (int32_t)value;
    }
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_io_error_unix_StdSysIoErrorUnixJniBridge_rustlangStdSysIoErrorUnixErrnoLocation(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "errno_location");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_errno_location");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "errno_location");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_errno_location");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "errno_location");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

