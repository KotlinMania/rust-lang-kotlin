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

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_pal_sgx_abi_mem_SysPalSgxAbiMemJniBridge_stdSysPalSgxAbiMemGetENCLAVESIZE(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static uint64_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "ENCLAVE_SIZE");
        if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "_ENCLAVE_SIZE");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "ENCLAVE_SIZE");
            if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "_ENCLAVE_SIZE");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint64_t*)dlsym(RTLD_DEFAULT, "ENCLAVE_SIZE");
    }
#endif
    uint64_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : 0;
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_pal_sgx_abi_mem_SysPalSgxAbiMemJniBridge_stdSysPalSgxAbiMemGetHEAPBASE(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static uint64_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "HEAP_BASE");
        if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "_HEAP_BASE");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "HEAP_BASE");
            if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "_HEAP_BASE");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint64_t*)dlsym(RTLD_DEFAULT, "HEAP_BASE");
    }
#endif
    uint64_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : 0;
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_pal_sgx_abi_mem_SysPalSgxAbiMemJniBridge_stdSysPalSgxAbiMemGetHEAPSIZE(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    static uint64_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "HEAP_SIZE");
        if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "_HEAP_SIZE");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "HEAP_SIZE");
            if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "_HEAP_SIZE");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint64_t*)dlsym(RTLD_DEFAULT, "HEAP_SIZE");
    }
#endif
    uint64_t km_res = (km_sym_ptr != NULL) ? *km_sym_ptr : 0;
    return (jlong)km_res;
}

