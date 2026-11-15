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

static Napi::Value rustlangLibunwindUnwindResume_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* exception = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_Resume")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_Resume")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_Resume"));
#ifdef __APPLE__
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_Resume"));
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_Resume"));
        }
#endif
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(exception);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangLibunwindUnwindDeleteException_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* exception = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_DeleteException")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_DeleteException")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_DeleteException"));
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(exception);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangLibunwindUnwindGetLanguageSpecificData_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_GetLanguageSpecificData")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_GetLanguageSpecificData")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_GetLanguageSpecificData"));
    }
#endif
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ctx);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangLibunwindUnwindGetRegionStart_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_GetRegionStart")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_GetRegionStart")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_GetRegionStart"));
    }
#endif
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ctx);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangLibunwindUnwindGetTextRelBase_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_GetTextRelBase")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_GetTextRelBase")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_GetTextRelBase"));
    }
#endif
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ctx);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangLibunwindUnwindGetDataRelBase_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    typedef void* (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_GetDataRelBase")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_GetDataRelBase")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_GetDataRelBase"));
    }
#endif
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ctx);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangLibunwindUnwindGetGr_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t regIndex = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    typedef void* (*km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_GetGR")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_GetGR")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_GetGR"));
#ifdef __APPLE__
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_GetGR"));
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_GetGR"));
        }
#endif
    }
#endif
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ctx, regIndex);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangLibunwindUnwindSetGr_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t regIndex = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* value = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    typedef void (*km_fn_t)(void*, int32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_SetGR")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_SetGR")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_SetGR"));
#ifdef __APPLE__
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_SetGR"));
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_SetGR"));
        }
#endif
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(ctx, regIndex, value);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangLibunwindUnwindSetIp_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* value = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    typedef void (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_SetIP")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_SetIP")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_SetIP"));
#ifdef __APPLE__
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_SetIP"));
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_SetIP"));
        }
#endif
    }
#endif
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(ctx, value);
    } else {
        errno = ENOSYS;
    }
    return km_env.Undefined();
}

static Napi::Value rustlangLibunwindUnwindGetIpinfo_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* ctx = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* ipBeforeInsn = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    typedef void* (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_Unwind_GetIPInfo")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_Unwind_GetIPInfo")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_Unwind_GetIPInfo"));
#ifdef __APPLE__
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("/usr/lib/libunwind.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_GetIPInfo"));
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == nullptr) {
            static void* km_h_unwind = nullptr;
            static bool km_h_unwind_init = false;
            if (!km_h_unwind_init) {
                km_h_unwind_init = true;
                km_h_unwind = dlopen("libunwind.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_unwind != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(km_h_unwind, "_Unwind_GetIPInfo"));
        }
#endif
    }
#endif
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ctx, ipBeforeInsn);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangLibunwindGccSpecificHandler_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* exceptionRecord = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* establisherFrame = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* contextRecord = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* dispatcherContext = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* personality = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    typedef int32_t (*km_fn_t)(void*, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
#ifdef _WIN32
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "_GCC_specific_handler")));
        if (km_fn_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "_GCC_specific_handler")));
        }
    }
#else
    if (km_fn_ptr == nullptr) {
        km_fn_ptr = reinterpret_cast<km_fn_t>(dlsym(RTLD_DEFAULT, "_GCC_specific_handler"));
    }
#endif
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(exceptionRecord, establisherFrame, contextRecord, dispatcherContext, personality);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static void register_libunwind_napi(Napi::Env env, Napi::Object exports) {
    exports.Set("rustlangLibunwindUnwindResume", Napi::Function::New(env, rustlangLibunwindUnwindResume_napi));
    exports.Set("rustlangLibunwindUnwindDeleteException", Napi::Function::New(env, rustlangLibunwindUnwindDeleteException_napi));
    exports.Set("rustlangLibunwindUnwindGetLanguageSpecificData", Napi::Function::New(env, rustlangLibunwindUnwindGetLanguageSpecificData_napi));
    exports.Set("rustlangLibunwindUnwindGetRegionStart", Napi::Function::New(env, rustlangLibunwindUnwindGetRegionStart_napi));
    exports.Set("rustlangLibunwindUnwindGetTextRelBase", Napi::Function::New(env, rustlangLibunwindUnwindGetTextRelBase_napi));
    exports.Set("rustlangLibunwindUnwindGetDataRelBase", Napi::Function::New(env, rustlangLibunwindUnwindGetDataRelBase_napi));
    exports.Set("rustlangLibunwindUnwindGetGr", Napi::Function::New(env, rustlangLibunwindUnwindGetGr_napi));
    exports.Set("rustlangLibunwindUnwindSetGr", Napi::Function::New(env, rustlangLibunwindUnwindSetGr_napi));
    exports.Set("rustlangLibunwindUnwindSetIp", Napi::Function::New(env, rustlangLibunwindUnwindSetIp_napi));
    exports.Set("rustlangLibunwindUnwindGetIpinfo", Napi::Function::New(env, rustlangLibunwindUnwindGetIpinfo_napi));
    exports.Set("rustlangLibunwindGccSpecificHandler", Napi::Function::New(env, rustlangLibunwindGccSpecificHandler_napi));
}

static KmNapiRegistrarNode registrar_node = { &register_libunwind_napi, nullptr };
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
