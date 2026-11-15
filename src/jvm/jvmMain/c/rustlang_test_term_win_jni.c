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

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_test_term_win_TestTermWinJniBridge_rustlangTestTermWinSetConsoleTextAttribute(JNIEnv* km_env, jclass km_clazz, jlong handle, jshort attr) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, uint16_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "SetConsoleTextAttribute");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_SetConsoleTextAttribute");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "SetConsoleTextAttribute");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_SetConsoleTextAttribute");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "SetConsoleTextAttribute");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("/usr/lib/libkernel32.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "SetConsoleTextAttribute");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("libkernel32.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "SetConsoleTextAttribute");
        }
#endif
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)handle, (uint16_t)attr);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_test_term_win_TestTermWinJniBridge_rustlangTestTermWinGetStdHandle(JNIEnv* km_env, jclass km_clazz, jint which) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "GetStdHandle");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_GetStdHandle");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "GetStdHandle");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_GetStdHandle");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "GetStdHandle");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("/usr/lib/libkernel32.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetStdHandle");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("libkernel32.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetStdHandle");
        }
#endif
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint32_t)which);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_test_term_win_TestTermWinJniBridge_rustlangTestTermWinGetConsoleScreenBufferInfo(JNIEnv* km_env, jclass km_clazz, jlong handle, jlong info) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "GetConsoleScreenBufferInfo");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_GetConsoleScreenBufferInfo");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "GetConsoleScreenBufferInfo");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_GetConsoleScreenBufferInfo");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "GetConsoleScreenBufferInfo");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("/usr/lib/libkernel32.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetConsoleScreenBufferInfo");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("libkernel32.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetConsoleScreenBufferInfo");
        }
#endif
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)handle, (void*)(uintptr_t)info);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

