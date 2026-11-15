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

static Napi::Value rustlangCorearchLoongarch64CrcWBW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crc.w.b.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crc.w.b.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crc.w.b.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crc.w.b.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crc.w.b.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrcWHW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crc.w.h.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crc.w.h.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crc.w.h.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crc.w.h.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crc.w.h.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrcWWW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crc.w.w.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crc.w.w.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crc.w.w.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crc.w.w.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crc.w.w.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrcWDW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crc.w.d.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crc.w.d.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crc.w.d.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crc.w.d.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crc.w.d.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrccWBW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crcc.w.b.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crcc.w.b.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crcc.w.b.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crcc.w.b.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crcc.w.b.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrccWHW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crcc.w.h.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crcc.w.h.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crcc.w.h.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crcc.w.h.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crcc.w.h.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrccWWW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crcc.w.w.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crcc.w.w.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crcc.w.w.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crcc.w.w.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crcc.w.w.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64CrccWDW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int32_t (*km_fn_t)(int64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.crcc.w.d.w")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.crcc.w.d.w")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.crcc.w.d.w")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.crcc.w.d.w")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.crcc.w.d.w"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangCorearchLoongarch64Cacop_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int64_t b = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    int64_t c = km_info.Length() > 2 ? km_napi_arg_i64(km_info[2]) : 0;
    typedef void (*km_fn_t)(int64_t, int64_t, int64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.cacop.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.cacop.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.cacop.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.cacop.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.cacop.d"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(a, b, c);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchLoongarch64Csrrd_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    typedef int64_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.csrrd.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.csrrd.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.csrrd.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.csrrd.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.csrrd.d"));
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(km_res)));
}

static Napi::Value rustlangCorearchLoongarch64Csrwr_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef int64_t (*km_fn_t)(int64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.csrwr.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.csrwr.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.csrwr.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.csrwr.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.csrwr.d"));
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(km_res)));
}

static Napi::Value rustlangCorearchLoongarch64Csrxchg_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int64_t b = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    int32_t c = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    typedef int64_t (*km_fn_t)(int64_t, int64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.csrxchg.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.csrxchg.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.csrxchg.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.csrxchg.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.csrxchg.d"));
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b, c);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(km_res)));
}

static Napi::Value rustlangCorearchLoongarch64IocsrrdD_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t a = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    typedef int64_t (*km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.iocsrrd.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.iocsrrd.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.iocsrrd.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.iocsrrd.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.iocsrrd.d"));
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(km_res)));
}

static Napi::Value rustlangCorearchLoongarch64IocsrwrD_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int32_t b = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef void (*km_fn_t)(int64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.iocsrwr.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.iocsrwr.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.iocsrwr.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.iocsrwr.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.iocsrwr.d"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchLoongarch64Asrtle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int64_t b = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    typedef void (*km_fn_t)(int64_t, int64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.asrtle.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.asrtle.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.asrtle.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.asrtle.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.asrtle.d"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchLoongarch64Asrtgt_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int64_t b = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    typedef void (*km_fn_t)(int64_t, int64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.asrtgt.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.asrtgt.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.asrtgt.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.asrtgt.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.asrtgt.d"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangCorearchLoongarch64Lddir_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int64_t b = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    typedef int64_t (*km_fn_t)(int64_t, int64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.lddir.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.lddir.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.lddir.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.lddir.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.lddir.d"));
    }
#endif
    int64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(km_res)));
}

static Napi::Value rustlangCorearchLoongarch64Ldpte_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int64_t a = km_info.Length() > 0 ? km_napi_arg_i64(km_info[0]) : 0;
    int64_t b = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    typedef void (*km_fn_t)(int64_t, int64_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "llvm.loongarch.ldpte.d")));
        if (km_fn_ptr == nullptr && km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_llvm.loongarch.ldpte.d")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "llvm.loongarch.ldpte.d")));
            if (km_fn_ptr == nullptr && km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_llvm.loongarch.ldpte.d")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "llvm.loongarch.ldpte.d"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(a, b);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static void register_corearch_loongarch64_napi(Napi::Env env, Napi::Object exports) {
    exports.Set("rustlangCorearchLoongarch64CrcWBW", Napi::Function::New(env, rustlangCorearchLoongarch64CrcWBW_napi));
    exports.Set("rustlangCorearchLoongarch64CrcWHW", Napi::Function::New(env, rustlangCorearchLoongarch64CrcWHW_napi));
    exports.Set("rustlangCorearchLoongarch64CrcWWW", Napi::Function::New(env, rustlangCorearchLoongarch64CrcWWW_napi));
    exports.Set("rustlangCorearchLoongarch64CrcWDW", Napi::Function::New(env, rustlangCorearchLoongarch64CrcWDW_napi));
    exports.Set("rustlangCorearchLoongarch64CrccWBW", Napi::Function::New(env, rustlangCorearchLoongarch64CrccWBW_napi));
    exports.Set("rustlangCorearchLoongarch64CrccWHW", Napi::Function::New(env, rustlangCorearchLoongarch64CrccWHW_napi));
    exports.Set("rustlangCorearchLoongarch64CrccWWW", Napi::Function::New(env, rustlangCorearchLoongarch64CrccWWW_napi));
    exports.Set("rustlangCorearchLoongarch64CrccWDW", Napi::Function::New(env, rustlangCorearchLoongarch64CrccWDW_napi));
    exports.Set("rustlangCorearchLoongarch64Cacop", Napi::Function::New(env, rustlangCorearchLoongarch64Cacop_napi));
    exports.Set("rustlangCorearchLoongarch64Csrrd", Napi::Function::New(env, rustlangCorearchLoongarch64Csrrd_napi));
    exports.Set("rustlangCorearchLoongarch64Csrwr", Napi::Function::New(env, rustlangCorearchLoongarch64Csrwr_napi));
    exports.Set("rustlangCorearchLoongarch64Csrxchg", Napi::Function::New(env, rustlangCorearchLoongarch64Csrxchg_napi));
    exports.Set("rustlangCorearchLoongarch64IocsrrdD", Napi::Function::New(env, rustlangCorearchLoongarch64IocsrrdD_napi));
    exports.Set("rustlangCorearchLoongarch64IocsrwrD", Napi::Function::New(env, rustlangCorearchLoongarch64IocsrwrD_napi));
    exports.Set("rustlangCorearchLoongarch64Asrtle", Napi::Function::New(env, rustlangCorearchLoongarch64Asrtle_napi));
    exports.Set("rustlangCorearchLoongarch64Asrtgt", Napi::Function::New(env, rustlangCorearchLoongarch64Asrtgt_napi));
    exports.Set("rustlangCorearchLoongarch64Lddir", Napi::Function::New(env, rustlangCorearchLoongarch64Lddir_napi));
    exports.Set("rustlangCorearchLoongarch64Ldpte", Napi::Function::New(env, rustlangCorearchLoongarch64Ldpte_napi));
}

static KmNapiRegistrarNode registrar_node = { &register_corearch_loongarch64_napi, nullptr };
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
