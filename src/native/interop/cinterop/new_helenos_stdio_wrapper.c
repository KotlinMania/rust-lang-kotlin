#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "new_helenos_stdio_wrapper.h"
#include <errno.h>
#include <stdio.h>
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

void* libcNewHelenosStdioGetSTDIN(void) {
    static void** km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_msvcrt, "stdin");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_msvcrt, "_stdin");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_ucrt, "stdin");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_ucrt, "_stdin");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (void**)dlsym(RTLD_DEFAULT, "stdin");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return NULL;
}

void* libcNewHelenosStdioGetSTDOUT(void) {
    static void** km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_msvcrt, "stdout");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_msvcrt, "_stdout");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_ucrt, "stdout");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_ucrt, "_stdout");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (void**)dlsym(RTLD_DEFAULT, "stdout");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return NULL;
}

void* libcNewHelenosStdioGetSTDERR(void) {
    static void** km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_msvcrt, "stderr");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_msvcrt, "_stderr");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_ucrt, "stderr");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (void**)(void*)GetProcAddress(km_h_ucrt, "_stderr");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (void**)dlsym(RTLD_DEFAULT, "stderr");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return NULL;
}

int32_t libcNewHelenosStdioFputc(int32_t c, void* stream) {
    return (int32_t)fputc(c, (FILE*)stream);
}

int32_t libcNewHelenosStdioFputs(const char* s, void* stream) {
    return (int32_t)fputs(s, (FILE*)stream);
}

void* libcNewHelenosStdioFopen(const char* filename, const char* mode) {
    return (void*)fopen(filename, mode);
}

int32_t libcNewHelenosStdioFclose(void* stream) {
    return (int32_t)fclose((FILE*)stream);
}

int32_t libcNewHelenosStdioFflush(void* stream) {
    return (int32_t)fflush((FILE*)stream);
}

uint64_t libcNewHelenosStdioFread(void* buf, uint64_t size, uint64_t nmemb, void* stream) {
    return (uint64_t)fread(buf, size, nmemb, (FILE*)stream);
}

uint64_t libcNewHelenosStdioFwrite(void* buf, uint64_t size, uint64_t nmemb, void* stream) {
    return (uint64_t)fwrite(buf, size, nmemb, (FILE*)stream);
}

int32_t libcNewHelenosStdioFseek(void* stream, int64_t offset, int32_t whence) {
    return (int32_t)fseek((FILE*)stream, offset, whence);
}

int64_t libcNewHelenosStdioFtell(void* stream) {
    return (int64_t)ftell((FILE*)stream);
}

int32_t libcNewHelenosStdioFileno(void* stream) {
    typedef int32_t (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fileno");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fileno");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fileno");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fileno");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fileno");
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(stream);
    }
    errno = ENOSYS;
    return -1;
}

