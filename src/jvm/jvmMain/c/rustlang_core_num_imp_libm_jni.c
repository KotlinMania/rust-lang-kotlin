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

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmPowidf2(JNIEnv* km_env, jclass km_clazz, jdouble a, jint b) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "__powidf2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "__powidf2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "__powidf2");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)a, (int32_t)b);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmPowisf2(JNIEnv* km_env, jclass km_clazz, jfloat a, jint b) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "__powisf2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "__powisf2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "__powisf2");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)a, (int32_t)b);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCbrt(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "cbrt");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_cbrt");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "cbrt");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_cbrt");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "cbrt");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCbrtf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "cbrtf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_cbrtf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "cbrtf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_cbrtf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "cbrtf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCeil(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "ceil");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_ceil");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "ceil");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_ceil");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "ceil");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCeilf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "ceilf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_ceilf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "ceilf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_ceilf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "ceilf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCopysign(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "copysign");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_copysign");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "copysign");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_copysign");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "copysign");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCopysignf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "copysignf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_copysignf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "copysignf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_copysignf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "copysignf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFabs(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fabs");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fabs");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fabs");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fabs");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fabs");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFabsf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fabsf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fabsf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fabsf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fabsf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fabsf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFdim(JNIEnv* km_env, jclass km_clazz, jdouble a, jdouble b) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fdim");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fdim");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fdim");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fdim");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fdim");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)a, (double)b);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFdimf(JNIEnv* km_env, jclass km_clazz, jfloat a, jfloat b) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fdimf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fdimf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fdimf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fdimf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fdimf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)a, (float)b);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFloor(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "floor");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_floor");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "floor");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_floor");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "floor");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFloorf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "floorf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_floorf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "floorf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_floorf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "floorf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFma(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y, jdouble z) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fma");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fma");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fma");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fma");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fma");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y, (double)z);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmaf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y, jfloat z) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmaf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmaf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmaf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmaf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmaf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y, (float)z);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmax(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmax");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmax");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmax");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmax");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmax");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmaxf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmaxf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmaxf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmaxf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmaxf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmaxf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmaximum(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmaximum");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmaximum");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmaximum");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmaximum");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmaximum");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmaximumf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmaximumf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmaximumf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmaximumf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmaximumf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmaximumf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmin(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmin");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmin");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmin");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmin");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmin");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFminf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fminf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fminf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fminf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fminf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fminf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFminimum(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fminimum");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fminimum");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fminimum");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fminimum");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fminimum");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFminimumf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fminimumf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fminimumf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fminimumf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fminimumf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fminimumf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmod(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmod");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmod");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmod");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmod");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmod");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmFmodf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fmodf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fmodf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fmodf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fmodf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fmodf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmRint(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "rint");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_rint");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "rint");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_rint");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "rint");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmRintf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "rintf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_rintf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "rintf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_rintf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "rintf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmRound(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "round");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_round");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "round");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_round");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "round");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmRoundeven(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "roundeven");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_roundeven");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "roundeven");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_roundeven");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "roundeven");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmRoundevenf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "roundevenf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_roundevenf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "roundevenf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_roundevenf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "roundevenf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmRoundf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "roundf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_roundf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "roundf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_roundf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "roundf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmSqrt(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sqrt");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sqrt");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sqrt");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sqrt");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sqrt");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmSqrtf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sqrtf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sqrtf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sqrtf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sqrtf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sqrtf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTrunc(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "trunc");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_trunc");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "trunc");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_trunc");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "trunc");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTruncf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "truncf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_truncf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "truncf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_truncf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "truncf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAcosf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "acosf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_acosf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "acosf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_acosf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "acosf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAsinf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "asinf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_asinf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "asinf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_asinf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "asinf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAtan2f(JNIEnv* km_env, jclass km_clazz, jfloat a, jfloat b) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "atan2f");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_atan2f");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "atan2f");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_atan2f");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "atan2f");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)a, (float)b);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAtanf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "atanf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_atanf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "atanf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_atanf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "atanf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCosf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "cosf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_cosf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "cosf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_cosf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "cosf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCoshf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "coshf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_coshf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "coshf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_coshf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "coshf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmErfcf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "erfcf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_erfcf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "erfcf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_erfcf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "erfcf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmErff(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "erff");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_erff");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "erff");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_erff");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "erff");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmExp2f(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "exp2f");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_exp2f");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "exp2f");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_exp2f");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "exp2f");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmExpf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "expf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_expf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "expf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_expf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "expf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmExpm1f(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "expm1f");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_expm1f");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "expm1f");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_expm1f");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "expm1f");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmHypotf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "hypotf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_hypotf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "hypotf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_hypotf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "hypotf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLdexpf(JNIEnv* km_env, jclass km_clazz, jfloat f, jint n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "ldexpf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_ldexpf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "ldexpf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_ldexpf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "ldexpf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)f, (int32_t)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog10f(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log10f");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log10f");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log10f");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log10f");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log10f");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog1pf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log1pf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log1pf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log1pf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log1pf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log1pf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog2f(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log2f");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log2f");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log2f");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log2f");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log2f");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLogf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "logf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_logf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "logf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_logf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "logf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmPowf(JNIEnv* km_env, jclass km_clazz, jfloat x, jfloat y) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float, float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "powf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_powf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "powf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_powf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "powf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x, (float)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmSinf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sinf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sinf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sinf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sinf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sinf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmSinhf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sinhf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sinhf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sinhf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sinhf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sinhf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTanf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tanf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tanf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tanf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tanf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tanf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTanhf(JNIEnv* km_env, jclass km_clazz, jfloat n) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tanhf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tanhf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tanhf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tanhf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tanhf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jfloat JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTgammaf(JNIEnv* km_env, jclass km_clazz, jfloat x) {
    (void)km_env;
    (void)km_clazz;
    typedef float (*km_fn_t)(float);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tgammaf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tgammaf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tgammaf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tgammaf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tgammaf");
    }
#endif
    float km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((float)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0f;
    }
    return (jfloat)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAcos(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "acos");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_acos");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "acos");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_acos");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "acos");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAsin(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "asin");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_asin");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "asin");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_asin");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "asin");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAtan(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "atan");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_atan");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "atan");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_atan");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "atan");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmAtan2(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "atan2");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_atan2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "atan2");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_atan2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "atan2");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCos(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "cos");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_cos");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "cos");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_cos");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "cos");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmCosh(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "cosh");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_cosh");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "cosh");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_cosh");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "cosh");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmErf(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "erf");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_erf");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "erf");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_erf");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "erf");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmErfc(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "erfc");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_erfc");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "erfc");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_erfc");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "erfc");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmExp(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "exp");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_exp");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "exp");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_exp");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "exp");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmExp2(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "exp2");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_exp2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "exp2");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_exp2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "exp2");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmExpm1(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "expm1");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_expm1");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "expm1");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_expm1");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "expm1");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmHypot(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "hypot");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_hypot");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "hypot");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_hypot");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "hypot");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLdexp(JNIEnv* km_env, jclass km_clazz, jdouble f, jint n) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "ldexp");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_ldexp");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "ldexp");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_ldexp");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "ldexp");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)f, (int32_t)n);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog10(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log10");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log10");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log10");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log10");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log10");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog1p(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log1p");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log1p");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log1p");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log1p");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log1p");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmLog2(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "log2");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_log2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "log2");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_log2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "log2");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmPow(JNIEnv* km_env, jclass km_clazz, jdouble x, jdouble y) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double, double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "pow");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_pow");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "pow");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_pow");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "pow");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x, (double)y);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmSin(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sin");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sin");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sin");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sin");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sin");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmSinh(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sinh");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sinh");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sinh");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sinh");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sinh");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTan(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tan");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tan");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tan");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tan");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tan");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTanh(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tanh");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tanh");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tanh");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tanh");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tanh");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

JNIEXPORT jdouble JNICALL Java_io_github_kotlinmania_rustlang_core_num_imp_libm_CoreNumImpLibmJniBridge_rustlangCoreNumImpLibmTgamma(JNIEnv* km_env, jclass km_clazz, jdouble x) {
    (void)km_env;
    (void)km_clazz;
    typedef double (*km_fn_t)(double);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tgamma");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tgamma");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tgamma");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tgamma");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tgamma");
    }
#endif
    double km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((double)x);
    } else {
        errno = ENOSYS;
        km_res = 0.0;
    }
    return (jdouble)km_res;
}

