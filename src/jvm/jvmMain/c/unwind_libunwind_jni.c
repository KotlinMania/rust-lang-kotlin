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

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindResume(JNIEnv* km_env, jclass km_clazz, jlong exception) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_Resume");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_Resume");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_Resume");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_Resume");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_Resume");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr((void*)(uintptr_t)exception);
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindDeleteException(JNIEnv* km_env, jclass km_clazz, jlong exception) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_DeleteException");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_DeleteException");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_DeleteException");
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr((void*)(uintptr_t)exception);
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindGetLanguageSpecificData(JNIEnv* km_env, jclass km_clazz, jlong ctx) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_GetLanguageSpecificData");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_GetLanguageSpecificData");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_GetLanguageSpecificData");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ctx);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindGetRegionStart(JNIEnv* km_env, jclass km_clazz, jlong ctx) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_GetRegionStart");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_GetRegionStart");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_GetRegionStart");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ctx);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindGetTextRelBase(JNIEnv* km_env, jclass km_clazz, jlong ctx) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_GetTextRelBase");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_GetTextRelBase");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_GetTextRelBase");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ctx);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindGetDataRelBase(JNIEnv* km_env, jclass km_clazz, jlong ctx) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_GetDataRelBase");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_GetDataRelBase");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_GetDataRelBase");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ctx);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindGetGr(JNIEnv* km_env, jclass km_clazz, jlong ctx, jint regIndex) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_GetGR");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_GetGR");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_GetGR");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_GetGR");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_GetGR");
        }
#endif
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ctx, (int32_t)regIndex);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindSetGr(JNIEnv* km_env, jclass km_clazz, jlong ctx, jint regIndex, jlong value) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void*, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_SetGR");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_SetGR");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_SetGR");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_SetGR");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_SetGR");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr((void*)(uintptr_t)ctx, (int32_t)regIndex, (void*)(uintptr_t)value);
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindSetIp(JNIEnv* km_env, jclass km_clazz, jlong ctx, jlong value) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_SetIP");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_SetIP");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_SetIP");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_SetIP");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_SetIP");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr((void*)(uintptr_t)ctx, (void*)(uintptr_t)value);
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindUnwindGetIpinfo(JNIEnv* km_env, jclass km_clazz, jlong ctx, jlong ipBeforeInsn) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_Unwind_GetIPInfo");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_Unwind_GetIPInfo");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_Unwind_GetIPInfo");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_GetIPInfo");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_unwind = NULL;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_unwind, "_Unwind_GetIPInfo");
        }
#endif
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ctx, (void*)(uintptr_t)ipBeforeInsn);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_unwind_libunwind_LibunwindJniBridge_unwindLibunwindGccSpecificHandler(JNIEnv* km_env, jclass km_clazz, jlong exceptionRecord, jlong establisherFrame, jlong contextRecord, jlong dispatcherContext, jlong personality) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_GCC_specific_handler");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_GCC_specific_handler");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "_GCC_specific_handler");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)exceptionRecord, (void*)(uintptr_t)establisherFrame, (void*)(uintptr_t)contextRecord, (void*)(uintptr_t)dispatcherContext, (void*)(uintptr_t)personality);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

