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

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetspnamR(JNIEnv* km_env, jclass km_clazz, jlong name, jlong spbuf, jlong buf, jlong buflen, jlong spbufp) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, void*, char*, uint64_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getspnam_r");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getspnam_r");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getspnam_r");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getspnam_r");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getspnam_r");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)name, (void*)(uintptr_t)spbuf, (char*)(uintptr_t)buf, (uint64_t)buflen, (void*)(uintptr_t)spbufp);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqOpen(JNIEnv* km_env, jclass km_clazz, jlong name, jint oflag, jint km_vac, jlong km_va0, jlong km_va1, jlong km_va2, jlong km_va3) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, int32_t, ...);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_open");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_open");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_open");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_open");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_open");
    }
#endif
    int32_t km_res = -1;
    if (km_fn_ptr != NULL) {
        switch (km_vac) {
            case 0: km_res = km_fn_ptr((const char*)(uintptr_t)name, (int32_t)oflag); break;
            case 1: km_res = km_fn_ptr((const char*)(uintptr_t)name, (int32_t)oflag, (intptr_t)km_va0); break;
            case 2: km_res = km_fn_ptr((const char*)(uintptr_t)name, (int32_t)oflag, (intptr_t)km_va0, (intptr_t)km_va1); break;
            case 3: km_res = km_fn_ptr((const char*)(uintptr_t)name, (int32_t)oflag, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2); break;
            default: km_res = km_fn_ptr((const char*)(uintptr_t)name, (int32_t)oflag, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2, (intptr_t)km_va3); break;
        }
    } else {
        errno = ENOSYS;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqClose(JNIEnv* km_env, jclass km_clazz, jint mqd) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_close");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_close");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_close");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_close");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_close");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqUnlink(JNIEnv* km_env, jclass km_clazz, jlong name) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_unlink");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_unlink");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_unlink");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_unlink");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_unlink");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)name);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqReceive(JNIEnv* km_env, jclass km_clazz, jint mqd, jlong msgPtr, jlong msgLen, jlong msgPrio) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, char*, uint64_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_receive");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_receive");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_receive");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_receive");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_receive");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd, (char*)(uintptr_t)msgPtr, (uint64_t)msgLen, (void*)(uintptr_t)msgPrio);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqTimedreceive(JNIEnv* km_env, jclass km_clazz, jint mqd, jlong msgPtr, jlong msgLen, jlong msgPrio, jlong absTimeout) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, char*, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_timedreceive");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_timedreceive");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_timedreceive");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_timedreceive");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_timedreceive");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd, (char*)(uintptr_t)msgPtr, (uint64_t)msgLen, (void*)(uintptr_t)msgPrio, (void*)(uintptr_t)absTimeout);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqSend(JNIEnv* km_env, jclass km_clazz, jint mqd, jlong msgPtr, jlong msgLen, jint msgPrio) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, uint64_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_send");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_send");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_send");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_send");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_send");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd, (const char*)(uintptr_t)msgPtr, (uint64_t)msgLen, (uint32_t)msgPrio);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqTimedsend(JNIEnv* km_env, jclass km_clazz, jint mqd, jlong msgPtr, jlong msgLen, jint msgPrio, jlong absTimeout) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, uint64_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_timedsend");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_timedsend");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_timedsend");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_timedsend");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_timedsend");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd, (const char*)(uintptr_t)msgPtr, (uint64_t)msgLen, (uint32_t)msgPrio, (void*)(uintptr_t)absTimeout);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqGetattr(JNIEnv* km_env, jclass km_clazz, jint mqd, jlong attr) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_getattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_getattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_getattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_getattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_getattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd, (void*)(uintptr_t)attr);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMqSetattr(JNIEnv* km_env, jclass km_clazz, jint mqd, jlong newattr, jlong oldattr) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mq_setattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mq_setattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mq_setattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mq_setattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mq_setattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mqd, (void*)(uintptr_t)newattr, (void*)(uintptr_t)oldattr);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMrand48(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mrand48");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mrand48");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mrand48");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mrand48");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mrand48");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSeed48(JNIEnv* km_env, jclass km_clazz, jlong xseed) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "seed48");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_seed48");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "seed48");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_seed48");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "seed48");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)xseed);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxLcong48(JNIEnv* km_env, jclass km_clazz, jlong p) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "lcong48");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_lcong48");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "lcong48");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_lcong48");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "lcong48");
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr((void*)(uintptr_t)p);
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxLutimes(JNIEnv* km_env, jclass km_clazz, jlong file, jlong times) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "lutimes");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_lutimes");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "lutimes");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_lutimes");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "lutimes");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)file, (void*)(uintptr_t)times);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxShmOpen(JNIEnv* km_env, jclass km_clazz, jlong name, jint oflag, jint mode) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, int32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "shm_open");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_shm_open");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "shm_open");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_shm_open");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "shm_open");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)name, (int32_t)oflag, (uint32_t)mode);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxShmUnlink(JNIEnv* km_env, jclass km_clazz, jlong name) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "shm_unlink");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_shm_unlink");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "shm_unlink");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_shm_unlink");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "shm_unlink");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)name);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFtok(JNIEnv* km_env, jclass km_clazz, jlong pathname, jint projId) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "ftok");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_ftok");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "ftok");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_ftok");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "ftok");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)pathname, (int32_t)projId);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSemget(JNIEnv* km_env, jclass km_clazz, jint key, jint nsems, jint semflag) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "semget");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_semget");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "semget");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_semget");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "semget");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)key, (int32_t)nsems, (int32_t)semflag);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSemop(JNIEnv* km_env, jclass km_clazz, jint semid, jlong sops, jlong nsops) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "semop");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_semop");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "semop");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_semop");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "semop");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)semid, (void*)(uintptr_t)sops, (uint64_t)nsops);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSemctl(JNIEnv* km_env, jclass km_clazz, jint semid, jint semnum, jint cmd, jint km_vac, jlong km_va0, jlong km_va1, jlong km_va2, jlong km_va3) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, int32_t, ...);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "semctl");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_semctl");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "semctl");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_semctl");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "semctl");
    }
#endif
    int32_t km_res = -1;
    if (km_fn_ptr != NULL) {
        switch (km_vac) {
            case 0: km_res = km_fn_ptr((int32_t)semid, (int32_t)semnum, (int32_t)cmd); break;
            case 1: km_res = km_fn_ptr((int32_t)semid, (int32_t)semnum, (int32_t)cmd, (intptr_t)km_va0); break;
            case 2: km_res = km_fn_ptr((int32_t)semid, (int32_t)semnum, (int32_t)cmd, (intptr_t)km_va0, (intptr_t)km_va1); break;
            case 3: km_res = km_fn_ptr((int32_t)semid, (int32_t)semnum, (int32_t)cmd, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2); break;
            default: km_res = km_fn_ptr((int32_t)semid, (int32_t)semnum, (int32_t)cmd, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2, (intptr_t)km_va3); break;
        }
    } else {
        errno = ENOSYS;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMsgctl(JNIEnv* km_env, jclass km_clazz, jint msqid, jint cmd, jlong buf) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "msgctl");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_msgctl");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "msgctl");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_msgctl");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "msgctl");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)msqid, (int32_t)cmd, (void*)(uintptr_t)buf);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMsgget(JNIEnv* km_env, jclass km_clazz, jint key, jint msgflg) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "msgget");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_msgget");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "msgget");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_msgget");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "msgget");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)key, (int32_t)msgflg);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMsgrcv(JNIEnv* km_env, jclass km_clazz, jint msqid, jlong msgp, jlong msgsz, jlong msgtyp, jint msgflg) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, void*, uint64_t, int64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "msgrcv");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_msgrcv");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "msgrcv");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_msgrcv");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "msgrcv");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)msqid, (void*)(uintptr_t)msgp, (uint64_t)msgsz, (int64_t)msgtyp, (int32_t)msgflg);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMsgsnd(JNIEnv* km_env, jclass km_clazz, jint msqid, jlong msgp, jlong msgsz, jint msgflg) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "msgsnd");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_msgsnd");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "msgsnd");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_msgsnd");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "msgsnd");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)msqid, (void*)(uintptr_t)msgp, (uint64_t)msgsz, (int32_t)msgflg);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFallocate(JNIEnv* km_env, jclass km_clazz, jint fd, jint mode, jlong offset, jlong len) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, int64_t, int64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fallocate");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fallocate");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fallocate");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fallocate");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fallocate");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int32_t)mode, (int64_t)offset, (int64_t)len);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixFallocate(JNIEnv* km_env, jclass km_clazz, jint fd, jlong offset, jlong len) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int64_t, int64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_fallocate");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_fallocate");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_fallocate");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_fallocate");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_fallocate");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int64_t)offset, (int64_t)len);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxReadahead(JNIEnv* km_env, jclass km_clazz, jint fd, jlong offset, jlong count) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, int64_t, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "readahead");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_readahead");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "readahead");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_readahead");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "readahead");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int64_t)offset, (uint64_t)count);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetxattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong name, jlong value, jlong size) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(const char*, const char*, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getxattr");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (const char*)(uintptr_t)name, (void*)(uintptr_t)value, (uint64_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxLgetxattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong name, jlong value, jlong size) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(const char*, const char*, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "lgetxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_lgetxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "lgetxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_lgetxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "lgetxattr");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (const char*)(uintptr_t)name, (void*)(uintptr_t)value, (uint64_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFgetxattr(JNIEnv* km_env, jclass km_clazz, jint filedes, jlong name, jlong value, jlong size) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, const char*, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fgetxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fgetxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fgetxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fgetxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fgetxattr");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)filedes, (const char*)(uintptr_t)name, (void*)(uintptr_t)value, (uint64_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSetxattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong name, jlong value, jlong size, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, const char*, void*, uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "setxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_setxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "setxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_setxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "setxattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (const char*)(uintptr_t)name, (void*)(uintptr_t)value, (uint64_t)size, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxLsetxattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong name, jlong value, jlong size, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, const char*, void*, uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "lsetxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_lsetxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "lsetxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_lsetxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "lsetxattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (const char*)(uintptr_t)name, (void*)(uintptr_t)value, (uint64_t)size, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFsetxattr(JNIEnv* km_env, jclass km_clazz, jint filedes, jlong name, jlong value, jlong size, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, void*, uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fsetxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fsetxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fsetxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fsetxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fsetxattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)filedes, (const char*)(uintptr_t)name, (void*)(uintptr_t)value, (uint64_t)size, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxListxattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong list, jlong size) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(const char*, char*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "listxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_listxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "listxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_listxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "listxattr");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (char*)(uintptr_t)list, (uint64_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxLlistxattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong list, jlong size) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(const char*, char*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "llistxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_llistxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "llistxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_llistxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "llistxattr");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (char*)(uintptr_t)list, (uint64_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFlistxattr(JNIEnv* km_env, jclass km_clazz, jint filedes, jlong list, jlong size) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, char*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "flistxattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_flistxattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "flistxattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_flistxattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "flistxattr");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)filedes, (char*)(uintptr_t)list, (uint64_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxRemovexattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong name) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "removexattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_removexattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "removexattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_removexattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "removexattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (const char*)(uintptr_t)name);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxLremovexattr(JNIEnv* km_env, jclass km_clazz, jlong path, jlong name) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "lremovexattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_lremovexattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "lremovexattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_lremovexattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "lremovexattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (const char*)(uintptr_t)name);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFremovexattr(JNIEnv* km_env, jclass km_clazz, jint filedes, jlong name) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fremovexattr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fremovexattr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fremovexattr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fremovexattr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fremovexattr");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)filedes, (const char*)(uintptr_t)name);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSignalfd(JNIEnv* km_env, jclass km_clazz, jint fd, jlong mask, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "signalfd");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_signalfd");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "signalfd");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_signalfd");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "signalfd");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (void*)(uintptr_t)mask, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxTimerfdCreate(JNIEnv* km_env, jclass km_clazz, jint clockid, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "timerfd_create");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_timerfd_create");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "timerfd_create");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_timerfd_create");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "timerfd_create");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint32_t)clockid, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxTimerfdGettime(JNIEnv* km_env, jclass km_clazz, jint fd, jlong currValue) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "timerfd_gettime");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_timerfd_gettime");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "timerfd_gettime");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_timerfd_gettime");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "timerfd_gettime");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (void*)(uintptr_t)currValue);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxTimerfdSettime(JNIEnv* km_env, jclass km_clazz, jint fd, jint flags, jlong newValue, jlong oldValue) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "timerfd_settime");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_timerfd_settime");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "timerfd_settime");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_timerfd_settime");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "timerfd_settime");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int32_t)flags, (void*)(uintptr_t)newValue, (void*)(uintptr_t)oldValue);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxQuotactl(JNIEnv* km_env, jclass km_clazz, jint cmd, jlong special, jint id, jlong data) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, int32_t, char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "quotactl");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_quotactl");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "quotactl");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_quotactl");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "quotactl");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)cmd, (const char*)(uintptr_t)special, (int32_t)id, (char*)(uintptr_t)data);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEpollPwait(JNIEnv* km_env, jclass km_clazz, jint epfd, jlong events, jint maxevents, jint timeout, jlong sigmask) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, int32_t, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "epoll_pwait");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_epoll_pwait");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "epoll_pwait");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_epoll_pwait");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "epoll_pwait");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)epfd, (void*)(uintptr_t)events, (int32_t)maxevents, (int32_t)timeout, (void*)(uintptr_t)sigmask);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxDup3(JNIEnv* km_env, jclass km_clazz, jint oldfd, jint newfd, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "dup3");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_dup3");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "dup3");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_dup3");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "dup3");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)oldfd, (int32_t)newfd, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSigtimedwait(JNIEnv* km_env, jclass km_clazz, jlong set, jlong info, jlong timeout) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sigtimedwait");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sigtimedwait");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sigtimedwait");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sigtimedwait");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sigtimedwait");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)set, (void*)(uintptr_t)info, (void*)(uintptr_t)timeout);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSigwaitinfo(JNIEnv* km_env, jclass km_clazz, jlong set, jlong info) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sigwaitinfo");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sigwaitinfo");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sigwaitinfo");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sigwaitinfo");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sigwaitinfo");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)set, (void*)(uintptr_t)info);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxAccept4(JNIEnv* km_env, jclass km_clazz, jint fd, jlong addr, jlong len, jint flg) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "accept4");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_accept4");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "accept4");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_accept4");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "accept4");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (void*)(uintptr_t)addr, (void*)(uintptr_t)len, (int32_t)flg);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxReboot(JNIEnv* km_env, jclass km_clazz, jint howTo) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "reboot");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_reboot");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "reboot");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_reboot");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "reboot");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)howTo);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSetfsgid(JNIEnv* km_env, jclass km_clazz, jint gid) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "setfsgid");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_setfsgid");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "setfsgid");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_setfsgid");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "setfsgid");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)gid);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSetfsuid(JNIEnv* km_env, jclass km_clazz, jint uid) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "setfsuid");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_setfsuid");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "setfsuid");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_setfsuid");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "setfsuid");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)uid);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMkfifoat(JNIEnv* km_env, jclass km_clazz, jint dirfd, jlong pathname, jint mode) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mkfifoat");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mkfifoat");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mkfifoat");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mkfifoat");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mkfifoat");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)dirfd, (const char*)(uintptr_t)pathname, (uint32_t)mode);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSyncFileRange(JNIEnv* km_env, jclass km_clazz, jint fd, jlong offset, jlong nbytes, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int64_t, int64_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sync_file_range");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sync_file_range");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sync_file_range");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sync_file_range");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sync_file_range");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int64_t)offset, (int64_t)nbytes, (uint32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixMadvise(JNIEnv* km_env, jclass km_clazz, jlong addr, jlong len, jint advice) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_madvise");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_madvise");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_madvise");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_madvise");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_madvise");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)len, (int32_t)advice);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMremap(JNIEnv* km_env, jclass km_clazz, jlong addr, jlong len, jlong newLen, jint flags, jint km_vac, jlong km_va0, jlong km_va1, jlong km_va2, jlong km_va3) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void*, uint64_t, uint64_t, int32_t, ...);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mremap");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mremap");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mremap");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mremap");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mremap");
    }
#endif
    void* km_res = NULL;
    if (km_fn_ptr != NULL) {
        switch (km_vac) {
            case 0: km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)len, (uint64_t)newLen, (int32_t)flags); break;
            case 1: km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)len, (uint64_t)newLen, (int32_t)flags, (intptr_t)km_va0); break;
            case 2: km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)len, (uint64_t)newLen, (int32_t)flags, (intptr_t)km_va0, (intptr_t)km_va1); break;
            case 3: km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)len, (uint64_t)newLen, (int32_t)flags, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2); break;
            default: km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)len, (uint64_t)newLen, (int32_t)flags, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2, (intptr_t)km_va3); break;
        }
    } else {
        errno = ENOSYS;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxRemapFilePages(JNIEnv* km_env, jclass km_clazz, jlong addr, jlong size, jint prot, jlong pgoff, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, uint64_t, int32_t, uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "remap_file_pages");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_remap_file_pages");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "remap_file_pages");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_remap_file_pages");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "remap_file_pages");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)addr, (uint64_t)size, (int32_t)prot, (uint64_t)pgoff, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxMkstemps(JNIEnv* km_env, jclass km_clazz, jlong arg0Template, jint suffixlen) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(char*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "mkstemps");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_mkstemps");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "mkstemps");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_mkstemps");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "mkstemps");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((char*)(uintptr_t)arg0Template, (int32_t)suffixlen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxVhangup(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "vhangup");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_vhangup");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "vhangup");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_vhangup");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "vhangup");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSync(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sync");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sync");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sync");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sync");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sync");
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr();
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSyncfs(JNIEnv* km_env, jclass km_clazz, jint fd) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "syncfs");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_syncfs");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "syncfs");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_syncfs");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "syncfs");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSyscall(JNIEnv* km_env, jclass km_clazz, jlong num, jint km_vac, jlong km_va0, jlong km_va1, jlong km_va2, jlong km_va3) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int64_t, ...);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "syscall");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_syscall");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "syscall");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_syscall");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "syscall");
    }
#endif
    int64_t km_res = -1;
    if (km_fn_ptr != NULL) {
        switch (km_vac) {
            case 0: km_res = km_fn_ptr((int64_t)num); break;
            case 1: km_res = km_fn_ptr((int64_t)num, (intptr_t)km_va0); break;
            case 2: km_res = km_fn_ptr((int64_t)num, (intptr_t)km_va0, (intptr_t)km_va1); break;
            case 3: km_res = km_fn_ptr((int64_t)num, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2); break;
            default: km_res = km_fn_ptr((int64_t)num, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2, (intptr_t)km_va3); break;
        }
    } else {
        errno = ENOSYS;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(JNIEnv* km_env, jclass km_clazz, jint pid, jlong cpusetsize, jlong cpuset) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, uint64_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sched_setaffinity");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sched_setaffinity");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sched_setaffinity");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sched_setaffinity");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sched_setaffinity");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)pid, (uint64_t)cpusetsize, (void*)(uintptr_t)cpuset);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEpollCreate(JNIEnv* km_env, jclass km_clazz, jint size) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "epoll_create");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_epoll_create");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "epoll_create");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_epoll_create");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "epoll_create");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)size);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEpollCreate1(JNIEnv* km_env, jclass km_clazz, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "epoll_create1");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_epoll_create1");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "epoll_create1");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_epoll_create1");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "epoll_create1");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEpollWait(JNIEnv* km_env, jclass km_clazz, jint epfd, jlong events, jint maxevents, jint timeout) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "epoll_wait");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_epoll_wait");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "epoll_wait");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_epoll_wait");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "epoll_wait");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)epfd, (void*)(uintptr_t)events, (int32_t)maxevents, (int32_t)timeout);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEpollCtl(JNIEnv* km_env, jclass km_clazz, jint epfd, jint op, jint fd, jlong event) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "epoll_ctl");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_epoll_ctl");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "epoll_ctl");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_epoll_ctl");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "epoll_ctl");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)epfd, (int32_t)op, (int32_t)fd, (void*)(uintptr_t)event);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxUnshare(JNIEnv* km_env, jclass km_clazz, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "unshare");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_unshare");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "unshare");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_unshare");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "unshare");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxUmount(JNIEnv* km_env, jclass km_clazz, jlong target) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "umount");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_umount");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "umount");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_umount");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "umount");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)target);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxTee(JNIEnv* km_env, jclass km_clazz, jint fdIn, jint fdOut, jlong len, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, int32_t, uint64_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tee");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tee");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tee");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tee");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tee");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fdIn, (int32_t)fdOut, (uint64_t)len, (uint32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSplice(JNIEnv* km_env, jclass km_clazz, jint fdIn, jlong offIn, jint fdOut, jlong offOut, jlong len, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, void*, int32_t, void*, uint64_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "splice");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_splice");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "splice");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_splice");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "splice");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fdIn, (void*)(uintptr_t)offIn, (int32_t)fdOut, (void*)(uintptr_t)offOut, (uint64_t)len, (uint32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEventfd(JNIEnv* km_env, jclass km_clazz, jint initval, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "eventfd");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_eventfd");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "eventfd");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_eventfd");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "eventfd");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint32_t)initval, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEventfdRead(JNIEnv* km_env, jclass km_clazz, jint fd, jlong value) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "eventfd_read");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_eventfd_read");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "eventfd_read");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_eventfd_read");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "eventfd_read");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (void*)(uintptr_t)value);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEventfdWrite(JNIEnv* km_env, jclass km_clazz, jint fd, jlong value) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "eventfd_write");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_eventfd_write");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "eventfd_write");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_eventfd_write");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "eventfd_write");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (uint64_t)value);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(JNIEnv* km_env, jclass km_clazz, jint pid, jlong tp) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sched_rr_get_interval");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sched_rr_get_interval");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sched_rr_get_interval");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sched_rr_get_interval");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sched_rr_get_interval");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)pid, (void*)(uintptr_t)tp);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSchedSetparam(JNIEnv* km_env, jclass km_clazz, jint pid, jlong param) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sched_setparam");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sched_setparam");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sched_setparam");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sched_setparam");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sched_setparam");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)pid, (void*)(uintptr_t)param);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSetns(JNIEnv* km_env, jclass km_clazz, jint fd, jint nstype) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "setns");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_setns");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "setns");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_setns");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "setns");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int32_t)nstype);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSwapoff(JNIEnv* km_env, jclass km_clazz, jlong path) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "swapoff");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_swapoff");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "swapoff");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_swapoff");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "swapoff");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxVmsplice(JNIEnv* km_env, jclass km_clazz, jint fd, jlong iov, jlong nrSegs, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, void*, uint64_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "vmsplice");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_vmsplice");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "vmsplice");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_vmsplice");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "vmsplice");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (void*)(uintptr_t)iov, (uint64_t)nrSegs, (uint32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPersonality(JNIEnv* km_env, jclass km_clazz, jlong persona) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "personality");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_personality");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "personality");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_personality");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "personality");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint64_t)persona);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSchedGetparam(JNIEnv* km_env, jclass km_clazz, jint pid, jlong param) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sched_getparam");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sched_getparam");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sched_getparam");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sched_getparam");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sched_getparam");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)pid, (void*)(uintptr_t)param);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxClone(JNIEnv* km_env, jclass km_clazz, jlong cb, jlong childStack, jint flags, jlong arg, jint km_vac, jlong km_va0, jlong km_va1, jlong km_va2, jlong km_va3) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*, int32_t, void*, ...);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "clone");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_clone");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "clone");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_clone");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "clone");
    }
#endif
    int32_t km_res = -1;
    if (km_fn_ptr != NULL) {
        switch (km_vac) {
            case 0: km_res = km_fn_ptr((void*)(uintptr_t)cb, (void*)(uintptr_t)childStack, (int32_t)flags, (void*)(uintptr_t)arg); break;
            case 1: km_res = km_fn_ptr((void*)(uintptr_t)cb, (void*)(uintptr_t)childStack, (int32_t)flags, (void*)(uintptr_t)arg, (intptr_t)km_va0); break;
            case 2: km_res = km_fn_ptr((void*)(uintptr_t)cb, (void*)(uintptr_t)childStack, (int32_t)flags, (void*)(uintptr_t)arg, (intptr_t)km_va0, (intptr_t)km_va1); break;
            case 3: km_res = km_fn_ptr((void*)(uintptr_t)cb, (void*)(uintptr_t)childStack, (int32_t)flags, (void*)(uintptr_t)arg, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2); break;
            default: km_res = km_fn_ptr((void*)(uintptr_t)cb, (void*)(uintptr_t)childStack, (int32_t)flags, (void*)(uintptr_t)arg, (intptr_t)km_va0, (intptr_t)km_va1, (intptr_t)km_va2, (intptr_t)km_va3); break;
        }
    } else {
        errno = ENOSYS;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(JNIEnv* km_env, jclass km_clazz, jint pid) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sched_getscheduler");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sched_getscheduler");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sched_getscheduler");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sched_getscheduler");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sched_getscheduler");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)pid);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxClockNanosleep(JNIEnv* km_env, jclass km_clazz, jint clkId, jint flags, jlong rqtp, jlong rmtp) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(uint32_t, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "clock_nanosleep");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_clock_nanosleep");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "clock_nanosleep");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_clock_nanosleep");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "clock_nanosleep");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint32_t)clkId, (int32_t)flags, (void*)(uintptr_t)rqtp, (void*)(uintptr_t)rmtp);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxUmount2(JNIEnv* km_env, jclass km_clazz, jlong target, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "umount2");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_umount2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "umount2");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_umount2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "umount2");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)target, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSwapon(JNIEnv* km_env, jclass km_clazz, jlong path, jint swapflags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "swapon");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_swapon");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "swapon");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_swapon");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "swapon");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)path, (int32_t)swapflags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(JNIEnv* km_env, jclass km_clazz, jint pid, jint policy, jlong param) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sched_setscheduler");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sched_setscheduler");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sched_setscheduler");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sched_setscheduler");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sched_setscheduler");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)pid, (int32_t)policy, (void*)(uintptr_t)param);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSendfile(JNIEnv* km_env, jclass km_clazz, jint outFd, jint inFd, jlong offset, jlong count) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, int32_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sendfile");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sendfile");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sendfile");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sendfile");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sendfile");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)outFd, (int32_t)inFd, (void*)(uintptr_t)offset, (uint64_t)count);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSigaltstack(JNIEnv* km_env, jclass km_clazz, jlong ss, jlong oss) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sigaltstack");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sigaltstack");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sigaltstack");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sigaltstack");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sigaltstack");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)ss, (void*)(uintptr_t)oss);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetdtablesize(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getdtablesize");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getdtablesize");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getdtablesize");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getdtablesize");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getdtablesize");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetgrouplist(JNIEnv* km_env, jclass km_clazz, jlong user, jint group, jlong groups, jlong ngroups) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(const char*, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getgrouplist");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getgrouplist");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getgrouplist");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getgrouplist");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getgrouplist");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)user, (int32_t)group, (void*)(uintptr_t)groups, (void*)(uintptr_t)ngroups);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawn(JNIEnv* km_env, jclass km_clazz, jlong pid, jlong path, jlong fileActions, jlong attrp, jlong argv, jlong envp) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, const char*, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawn");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawn");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawn");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawn");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawn");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)pid, (const char*)(uintptr_t)path, (void*)(uintptr_t)fileActions, (void*)(uintptr_t)attrp, (void*)(uintptr_t)argv, (void*)(uintptr_t)envp);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnp(JNIEnv* km_env, jclass km_clazz, jlong pid, jlong file, jlong fileActions, jlong attrp, jlong argv, jlong envp) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, const char*, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnp");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnp");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnp");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnp");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnp");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)pid, (const char*)(uintptr_t)file, (void*)(uintptr_t)fileActions, (void*)(uintptr_t)attrp, (void*)(uintptr_t)argv, (void*)(uintptr_t)envp);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(JNIEnv* km_env, jclass km_clazz, jlong attr) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_init");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_init");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_init");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_init");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_init");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(JNIEnv* km_env, jclass km_clazz, jlong attr) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_destroy");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_destroy");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_destroy");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_destroy");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_destroy");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong arg1Default) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_getsigdefault");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_getsigdefault");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_getsigdefault");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_getsigdefault");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_getsigdefault");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)arg1Default);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong arg1Default) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_setsigdefault");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_setsigdefault");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_setsigdefault");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_setsigdefault");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_setsigdefault");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)arg1Default);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong arg1Default) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_getsigmask");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_getsigmask");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_getsigmask");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_getsigmask");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_getsigmask");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)arg1Default);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong arg1Default) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_setsigmask");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_setsigmask");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_setsigmask");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_setsigmask");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_setsigmask");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)arg1Default);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_getflags");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_getflags");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_getflags");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_getflags");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_getflags");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(JNIEnv* km_env, jclass km_clazz, jlong attr, jshort flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, int16_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_setflags");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_setflags");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_setflags");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_setflags");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_setflags");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (int16_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_getpgroup");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_getpgroup");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_getpgroup");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_getpgroup");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_getpgroup");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(JNIEnv* km_env, jclass km_clazz, jlong attr, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_setpgroup");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_setpgroup");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_setpgroup");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_setpgroup");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_setpgroup");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_getschedpolicy");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_getschedpolicy");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_getschedpolicy");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_getschedpolicy");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_getschedpolicy");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(JNIEnv* km_env, jclass km_clazz, jlong attr, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_setschedpolicy");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_setschedpolicy");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_setschedpolicy");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_setschedpolicy");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_setschedpolicy");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong param) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_getschedparam");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_getschedparam");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_getschedparam");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_getschedparam");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_getschedparam");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)param);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(JNIEnv* km_env, jclass km_clazz, jlong attr, jlong param) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawnattr_setschedparam");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawnattr_setschedparam");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawnattr_setschedparam");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawnattr_setschedparam");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawnattr_setschedparam");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)attr, (void*)(uintptr_t)param);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(JNIEnv* km_env, jclass km_clazz, jlong actions) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawn_file_actions_init");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawn_file_actions_init");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawn_file_actions_init");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawn_file_actions_init");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawn_file_actions_init");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)actions);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(JNIEnv* km_env, jclass km_clazz, jlong actions) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawn_file_actions_destroy");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawn_file_actions_destroy");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawn_file_actions_destroy");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawn_file_actions_destroy");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawn_file_actions_destroy");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)actions);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(JNIEnv* km_env, jclass km_clazz, jlong actions, jint fd, jlong path, jint oflag, jint mode) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, int32_t, const char*, int32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawn_file_actions_addopen");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawn_file_actions_addopen");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawn_file_actions_addopen");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawn_file_actions_addopen");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawn_file_actions_addopen");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)actions, (int32_t)fd, (const char*)(uintptr_t)path, (int32_t)oflag, (uint32_t)mode);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(JNIEnv* km_env, jclass km_clazz, jlong actions, jint fd) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawn_file_actions_addclose");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawn_file_actions_addclose");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawn_file_actions_addclose");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawn_file_actions_addclose");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawn_file_actions_addclose");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)actions, (int32_t)fd);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(JNIEnv* km_env, jclass km_clazz, jlong actions, jint fd, jint newfd) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_spawn_file_actions_adddup2");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_spawn_file_actions_adddup2");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_spawn_file_actions_adddup2");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_spawn_file_actions_adddup2");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_spawn_file_actions_adddup2");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)actions, (int32_t)fd, (int32_t)newfd);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFreadUnlocked(JNIEnv* km_env, jclass km_clazz, jlong buf, jlong size, jlong nobj, jlong stream) {
    (void)km_env;
    (void)km_clazz;
    typedef uint64_t (*km_fn_t)(void*, uint64_t, uint64_t, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fread_unlocked");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fread_unlocked");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fread_unlocked");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fread_unlocked");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fread_unlocked");
    }
#endif
    uint64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)buf, (uint64_t)size, (uint64_t)nobj, (void*)(uintptr_t)stream);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(JNIEnv* km_env, jclass km_clazz, jint fd, jint wd) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "inotify_rm_watch");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_inotify_rm_watch");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "inotify_rm_watch");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_inotify_rm_watch");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "inotify_rm_watch");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int32_t)wd);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxInotifyInit(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "inotify_init");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_inotify_init");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "inotify_init");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_inotify_init");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "inotify_init");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxInotifyInit1(JNIEnv* km_env, jclass km_clazz, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "inotify_init1");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_inotify_init1");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "inotify_init1");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_inotify_init1");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "inotify_init1");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(JNIEnv* km_env, jclass km_clazz, jint fd, jlong path, jint mask) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "inotify_add_watch");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_inotify_add_watch");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "inotify_add_watch");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_inotify_add_watch");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "inotify_add_watch");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (const char*)(uintptr_t)path, (uint32_t)mask);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFanotifyInit(JNIEnv* km_env, jclass km_clazz, jint flags, jint eventFFlags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fanotify_init");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fanotify_init");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fanotify_init");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fanotify_init");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fanotify_init");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint32_t)flags, (uint32_t)eventFFlags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGethostid(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "gethostid");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_gethostid");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "gethostid");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_gethostid");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "gethostid");
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxKlogctl(JNIEnv* km_env, jclass km_clazz, jint syslogType, jlong bufp, jint len) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, char*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "klogctl");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_klogctl");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "klogctl");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_klogctl");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "klogctl");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)syslogType, (char*)(uintptr_t)bufp, (int32_t)len);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxNameToHandleAt(JNIEnv* km_env, jclass km_clazz, jint dirfd, jlong path, jlong handle, jlong mountId, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, const char*, void*, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "name_to_handle_at");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_name_to_handle_at");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "name_to_handle_at");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_name_to_handle_at");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "name_to_handle_at");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)dirfd, (const char*)(uintptr_t)path, (void*)(uintptr_t)handle, (void*)(uintptr_t)mountId, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(JNIEnv* km_env, jclass km_clazz, jint mountFd, jlong handle, jint flags) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "open_by_handle_at");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_open_by_handle_at");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "open_by_handle_at");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_open_by_handle_at");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "open_by_handle_at");
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)mountFd, (void*)(uintptr_t)handle, (int32_t)flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetnetbyname(JNIEnv* km_env, jclass km_clazz, jlong name) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getnetbyname");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getnetbyname");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getnetbyname");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getnetbyname");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getnetbyname");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)name);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(JNIEnv* km_env, jclass km_clazz, jint net, jint type) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getnetbyaddr");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getnetbyaddr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getnetbyaddr");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getnetbyaddr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getnetbyaddr");
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((uint32_t)net, (int32_t)type);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxGetnetent(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "getnetent");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_getnetent");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "getnetent");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_getnetent");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "getnetent");
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

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSetnetent(JNIEnv* km_env, jclass km_clazz, jint stayopen) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "setnetent");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_setnetent");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "setnetent");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_setnetent");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "setnetent");
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr((int32_t)stayopen);
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxEndnetent(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef void (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "endnetent");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_endnetent");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "endnetent");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_endnetent");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "endnetent");
    }
#endif
    if (km_fn_ptr != NULL) {
        km_fn_ptr();
    } else {
        errno = ENOSYS;
    }
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFallocate64(JNIEnv* km_env, jclass km_clazz, jint fd, jint mode, jlong offset, jlong len) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int32_t, int64_t, int64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fallocate64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fallocate64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fallocate64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fallocate64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fallocate64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fallocate");
        }
#endif
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int32_t)mode, (int64_t)offset, (int64_t)len);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFgetpos64(JNIEnv* km_env, jclass km_clazz, jlong stream, jlong ptr) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fgetpos64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fgetpos64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fgetpos64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fgetpos64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fgetpos64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fgetpos");
        }
#endif
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)stream, (void*)(uintptr_t)ptr);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxFopen64(JNIEnv* km_env, jclass km_clazz, jlong filename, jlong mode) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(const char*, const char*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "fopen64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_fopen64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "fopen64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_fopen64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fopen64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "fopen");
        }
#endif
    }
#endif
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((const char*)(uintptr_t)filename, (const char*)(uintptr_t)mode);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxPosixFallocate64(JNIEnv* km_env, jclass km_clazz, jint fd, jlong offset, jlong len) {
    (void)km_env;
    (void)km_clazz;
    typedef int32_t (*km_fn_t)(int32_t, int64_t, int64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "posix_fallocate64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_posix_fallocate64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "posix_fallocate64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_posix_fallocate64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_fallocate64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "posix_fallocate");
        }
#endif
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)fd, (int64_t)offset, (int64_t)len);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jint)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxSendfile64(JNIEnv* km_env, jclass km_clazz, jint outFd, jint inFd, jlong offset, jlong count) {
    (void)km_env;
    (void)km_clazz;
    typedef int64_t (*km_fn_t)(int32_t, int32_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "sendfile64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_sendfile64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "sendfile64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_sendfile64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sendfile64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "sendfile");
        }
#endif
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((int32_t)outFd, (int32_t)inFd, (void*)(uintptr_t)offset, (uint64_t)count);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return (jlong)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_libc_unix_linuxlike_linux_LibcUnixLinuxlikeLinuxJniBridge_rustlangLibcUnixLinuxlikeLinuxTmpfile64(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
    typedef void* (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "tmpfile64");
        if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_tmpfile64");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "tmpfile64");
            if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_tmpfile64");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tmpfile64");
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == NULL) {
            km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "tmpfile");
        }
#endif
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

