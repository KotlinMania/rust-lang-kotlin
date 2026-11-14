#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include <jni.h>
#include <stdbool.h>
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

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_corearch_x86_X86Avx512fJniBridge_corearchX86Avx512fVfmaddssround(JNIEnv* km_env, jclass km_clazz, jfloat a, jfloat b, jfloat c, jint rounding) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float, float, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "llvm.x86.avx512.vfmadd.f32");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_llvm.x86.avx512.vfmadd.f32");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "llvm.x86.avx512.vfmadd.f32");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_llvm.x86.avx512.vfmadd.f32");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "llvm.x86.avx512.vfmadd.f32");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)a, (float)b, (float)c, (int32_t)rounding);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_corearch_x86_X86Avx512fJniBridge_corearchX86Avx512fVfmaddsdround(JNIEnv* km_env, jclass km_clazz, jdouble a, jdouble b, jdouble c, jint rounding) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double, double, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "llvm.x86.avx512.vfmadd.f64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_llvm.x86.avx512.vfmadd.f64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "llvm.x86.avx512.vfmadd.f64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_llvm.x86.avx512.vfmadd.f64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "llvm.x86.avx512.vfmadd.f64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "llvm.x86.avx512.vfmadd.f");
        }
#endif
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)a, (double)b, (double)c, (int32_t)rounding);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

