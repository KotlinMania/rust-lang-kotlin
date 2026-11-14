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

static Napi::Value stdSysAllocVexosGetHEAPSTART_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    static uint8_t* km_sym_ptr = nullptr;
#ifdef _WIN32
    if (km_sym_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "__heap_start")));
        if (km_sym_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "__heap_start")));
        }
    }
#else
    if (km_sym_ptr == nullptr) {
        km_sym_ptr = reinterpret_cast<uint8_t*>(dlsym(RTLD_DEFAULT, "__heap_start"));
    }
#endif
    uint8_t km_res = (km_sym_ptr != nullptr) ? *km_sym_ptr : 0;
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value stdSysAllocVexosSetHEAPSTART_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint8_t value = static_cast<uint8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    static uint8_t* km_sym_ptr = nullptr;
#ifdef _WIN32
    if (km_sym_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "__heap_start")));
        if (km_sym_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "__heap_start")));
        }
    }
#else
    if (km_sym_ptr == nullptr) {
        km_sym_ptr = reinterpret_cast<uint8_t*>(dlsym(RTLD_DEFAULT, "__heap_start"));
    }
#endif
    if (km_sym_ptr != nullptr) {
        *km_sym_ptr = value;
    }
    return km_env.Undefined();
}

static Napi::Value stdSysAllocVexosGetHEAPEND_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    static uint8_t* km_sym_ptr = nullptr;
#ifdef _WIN32
    if (km_sym_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "__heap_end")));
        if (km_sym_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "__heap_end")));
        }
    }
#else
    if (km_sym_ptr == nullptr) {
        km_sym_ptr = reinterpret_cast<uint8_t*>(dlsym(RTLD_DEFAULT, "__heap_end"));
    }
#endif
    uint8_t km_res = (km_sym_ptr != nullptr) ? *km_sym_ptr : 0;
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value stdSysAllocVexosSetHEAPEND_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint8_t value = static_cast<uint8_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    static uint8_t* km_sym_ptr = nullptr;
#ifdef _WIN32
    if (km_sym_ptr == nullptr) {
        HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
        if (km_h_ucrt == nullptr) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
        if (km_h_ucrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_ucrt, "__heap_end")));
        if (km_sym_ptr == nullptr) {
            HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
            if (km_h_msvcrt == nullptr) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
            if (km_h_msvcrt != nullptr) km_sym_ptr = reinterpret_cast<uint8_t*>(reinterpret_cast<void*>(GetProcAddress(km_h_msvcrt, "__heap_end")));
        }
    }
#else
    if (km_sym_ptr == nullptr) {
        km_sym_ptr = reinterpret_cast<uint8_t*>(dlsym(RTLD_DEFAULT, "__heap_end"));
    }
#endif
    if (km_sym_ptr != nullptr) {
        *km_sym_ptr = value;
    }
    return km_env.Undefined();
}

static void register_sys_alloc_vexos_napi(Napi::Env env, Napi::Object exports) {
    exports.Set("stdSysAllocVexosGetHEAPSTART", Napi::Function::New(env, stdSysAllocVexosGetHEAPSTART_napi));
    exports.Set("stdSysAllocVexosSetHEAPSTART", Napi::Function::New(env, stdSysAllocVexosSetHEAPSTART_napi));
    exports.Set("stdSysAllocVexosGetHEAPEND", Napi::Function::New(env, stdSysAllocVexosGetHEAPEND_napi));
    exports.Set("stdSysAllocVexosSetHEAPEND", Napi::Function::New(env, stdSysAllocVexosSetHEAPEND_napi));
}

static KmNapiRegistrarNode registrar_node = { &register_sys_alloc_vexos_napi, nullptr };
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
