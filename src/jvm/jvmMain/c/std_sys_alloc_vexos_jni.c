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

JNIEXPORT jbyte JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_vexos_SysAllocVexosJniBridge_stdSysAllocVexosGetHEAPSTART(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_start");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_start");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_start");
    }
#endif
    uint8_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : 0;
    return (jbyte)km_res;
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_vexos_SysAllocVexosJniBridge_stdSysAllocVexosSetHEAPSTART(JNIEnv* km_env, jclass km_clazz, jbyte value) {
    (void)km_env;
    (void)km_clazz;
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_start");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_start");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_start");
    }
#endif
    if (km_sym_ptr != NULL) {
        *km_sym_ptr = (uint8_t)value;
    }
}

JNIEXPORT jbyte JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_vexos_SysAllocVexosJniBridge_stdSysAllocVexosGetHEAPEND(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_end");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_end");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_end");
    }
#endif
    uint8_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : 0;
    return (jbyte)km_res;
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_vexos_SysAllocVexosJniBridge_stdSysAllocVexosSetHEAPEND(JNIEnv* km_env, jclass km_clazz, jbyte value) {
    (void)km_env;
    (void)km_clazz;
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_end");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_end");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_end");
    }
#endif
    if (km_sym_ptr != NULL) {
        *km_sym_ptr = (uint8_t)value;
    }
}

