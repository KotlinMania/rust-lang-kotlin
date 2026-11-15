#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include <napi.h>
#include <cstdint>
#include <cstddef>
#include <cstdlib>
#include <cstring>
#include <cerrno>
#include <string>
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

struct KmNapiRegistrarNode {
    void (*register_fn)(Napi::Env, Napi::Object);
    KmNapiRegistrarNode* next;
};
#if defined(__GNUC__) || defined(__clang__)
__attribute__((visibility("hidden")))
#endif
extern KmNapiRegistrarNode* km_napi_registrar_head;

namespace {

static int64_t km_napi_arg_i64(const Napi::Value& v) {
    if (v.IsNull() || v.IsUndefined()) return 0;
    if (v.IsString()) {
        std::string s = v.As<Napi::String>().Utf8Value();
        return static_cast<int64_t>(std::strtoll(s.c_str(), nullptr, 10));
    }
    if (v.IsNumber()) return v.As<Napi::Number>().Int64Value();
    return 0;
}

static uint64_t km_napi_arg_u64(const Napi::Value& v) {
    if (v.IsNull() || v.IsUndefined()) return 0;
    if (v.IsString()) {
        std::string s = v.As<Napi::String>().Utf8Value();
        return static_cast<uint64_t>(std::strtoull(s.c_str(), nullptr, 10));
    }
    if (v.IsNumber()) return static_cast<uint64_t>(v.As<Napi::Number>().Int64Value());
    return 0;
}

static Napi::Value rustlangCorearchX8664AmxLdtilecfg_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* memAddr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.ldtilecfg")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.ldtilecfg")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.ldtilecfg")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.ldtilecfg")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.ldtilecfg"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(memAddr);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxSttilecfg_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* memAddr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.sttilecfg")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.sttilecfg")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.sttilecfg")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.sttilecfg")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.sttilecfg"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(memAddr);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTileloadd64_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* base = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint64_t stride = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    typedef void (*km_fn_t)(int8_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tileloadd64")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tileloadd64")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tileloadd64")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tileloadd64")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tileloadd64"));
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == nullptr) {
            km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tileloadd"));
        }
#endif
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, base, stride);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTileloaddt164_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* base = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint64_t stride = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    typedef void (*km_fn_t)(int8_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tileloaddt164")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tileloaddt164")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tileloaddt164")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tileloaddt164")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tileloaddt164"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, base, stride);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTilerelease_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    typedef void (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tilerelease")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tilerelease")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tilerelease")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tilerelease")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tilerelease"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr();
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTilestored64_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* base = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint64_t stride = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    typedef void (*km_fn_t)(int8_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tilestored64")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tilestored64")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tilestored64")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tilestored64")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tilestored64"));
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == nullptr) {
            km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tilestored"));
        }
#endif
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, base, stride);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTilezero_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tilezero")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tilezero")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tilezero")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tilezero")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tilezero"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbf16ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbf16ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbf16ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbf16ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbf16ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbf16ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbuud_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbuud")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbuud")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbuud")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbuud")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbuud"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbusd_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbusd")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbusd")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbusd")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbusd")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbusd"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbsud_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbsud")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbsud")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbsud")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbsud")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbsud"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbssd_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbssd")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbssd")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbssd")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbssd")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbssd"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpfp16ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpfp16ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpfp16ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpfp16ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpfp16ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpfp16ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTcmmimfp16ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tcmmimfp16ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tcmmimfp16ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tcmmimfp16ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tcmmimfp16ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tcmmimfp16ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTcmmrlfp16ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tcmmrlfp16ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tcmmrlfp16ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tcmmrlfp16ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tcmmrlfp16ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tcmmrlfp16ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbf8ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbf8ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbf8ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbf8ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbf8ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbf8ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdpbhf8ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdpbhf8ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdpbhf8ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdpbhf8ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdpbhf8ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdpbhf8ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdphbf8ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdphbf8ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdphbf8ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdphbf8ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdphbf8ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdphbf8ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTdphf8ps_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int8_t a = static_cast<int8_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int8_t b = static_cast<int8_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int8_t, int8_t, int8_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tdphf8ps")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tdphf8ps")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tdphf8ps")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tdphf8ps")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tdphf8ps"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTileloaddrs64_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* base = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint64_t stride = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    typedef void (*km_fn_t)(int8_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tileloaddrs64")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tileloaddrs64")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tileloaddrs64")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tileloaddrs64")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tileloaddrs64"));
#if defined(__ANDROID__) && defined(__LP64__)
        if (km_fn_ptr == nullptr) {
            km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tileloaddrs"));
        }
#endif
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, base, stride);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchX8664AmxTileloaddrst164_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int8_t dst = static_cast<int8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* base = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint64_t stride = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    typedef void (*km_fn_t)(int8_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.x86.tileloaddrst164")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.x86.tileloaddrst164")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.x86.tileloaddrst164")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.x86.tileloaddrst164")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.x86.tileloaddrst164"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dst, base, stride);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static void register_corearch_x8664_amx_napi(Napi::Env env, Napi::Object exports) {
    exports.Set("rustlangCorearchX8664AmxLdtilecfg", Napi::Function::New(env, rustlangCorearchX8664AmxLdtilecfg_napi));
    exports.Set("rustlangCorearchX8664AmxSttilecfg", Napi::Function::New(env, rustlangCorearchX8664AmxSttilecfg_napi));
    exports.Set("rustlangCorearchX8664AmxTileloadd64", Napi::Function::New(env, rustlangCorearchX8664AmxTileloadd64_napi));
    exports.Set("rustlangCorearchX8664AmxTileloaddt164", Napi::Function::New(env, rustlangCorearchX8664AmxTileloaddt164_napi));
    exports.Set("rustlangCorearchX8664AmxTilerelease", Napi::Function::New(env, rustlangCorearchX8664AmxTilerelease_napi));
    exports.Set("rustlangCorearchX8664AmxTilestored64", Napi::Function::New(env, rustlangCorearchX8664AmxTilestored64_napi));
    exports.Set("rustlangCorearchX8664AmxTilezero", Napi::Function::New(env, rustlangCorearchX8664AmxTilezero_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbf16ps", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbf16ps_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbuud", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbuud_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbusd", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbusd_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbsud", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbsud_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbssd", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbssd_napi));
    exports.Set("rustlangCorearchX8664AmxTdpfp16ps", Napi::Function::New(env, rustlangCorearchX8664AmxTdpfp16ps_napi));
    exports.Set("rustlangCorearchX8664AmxTcmmimfp16ps", Napi::Function::New(env, rustlangCorearchX8664AmxTcmmimfp16ps_napi));
    exports.Set("rustlangCorearchX8664AmxTcmmrlfp16ps", Napi::Function::New(env, rustlangCorearchX8664AmxTcmmrlfp16ps_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbf8ps", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbf8ps_napi));
    exports.Set("rustlangCorearchX8664AmxTdpbhf8ps", Napi::Function::New(env, rustlangCorearchX8664AmxTdpbhf8ps_napi));
    exports.Set("rustlangCorearchX8664AmxTdphbf8ps", Napi::Function::New(env, rustlangCorearchX8664AmxTdphbf8ps_napi));
    exports.Set("rustlangCorearchX8664AmxTdphf8ps", Napi::Function::New(env, rustlangCorearchX8664AmxTdphf8ps_napi));
    exports.Set("rustlangCorearchX8664AmxTileloaddrs64", Napi::Function::New(env, rustlangCorearchX8664AmxTileloaddrs64_napi));
    exports.Set("rustlangCorearchX8664AmxTileloaddrst164", Napi::Function::New(env, rustlangCorearchX8664AmxTileloaddrst164_napi));
}

static KmNapiRegistrarNode registrar_node = { &register_corearch_x8664_amx_napi, nullptr };
struct KmNapiAutoRegister {
    KmNapiAutoRegister() {
        registrar_node.next = km_napi_registrar_head;
        km_napi_registrar_head = &registrar_node;
    }
};
static KmNapiAutoRegister auto_register_instance;

} // namespace

#ifdef KM_NAPI_STANDALONE_MODULE
#if defined(__GNUC__) || defined(__clang__)
__attribute__((visibility("hidden")))
#endif
KmNapiRegistrarNode* km_napi_registrar_head = nullptr;
static Napi::Object KmNapiStandaloneInit(Napi::Env env, Napi::Object exports) {
    for (KmNapiRegistrarNode* cur = km_napi_registrar_head; cur != nullptr; cur = cur->next) {
        cur->register_fn(env, exports);
    }
    return exports;
}
NODE_API_MODULE(NODE_GYP_MODULE_NAME, KmNapiStandaloneInit)
#endif
