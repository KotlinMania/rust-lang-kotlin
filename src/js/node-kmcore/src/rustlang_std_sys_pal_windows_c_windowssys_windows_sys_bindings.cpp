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

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "AcquireSRWLockExclusive")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(srwlock);
    } else {
        errno = ENOSYS;
    }
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "AcquireSRWLockShared")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(srwlock);
    } else {
        errno = ENOSYS;
    }
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t first = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* handler = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "AddVectoredExceptionHandler")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(first, handler);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)first;
    (void)handler;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CancelIo")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hobject = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CloseHandle")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hobject);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hobject;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpstring1 = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t cchcount1 = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpstring2 = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    int32_t cchcount2 = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    int32_t bignorecase = static_cast<int32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CompareStringOrdinal")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpstring1, cchcount1, lpstring2, cchcount2, bignorecase);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpstring1;
    (void)cchcount1;
    (void)lpstring2;
    (void)cchcount2;
    (void)bignorecase;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpexistingfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpnewfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lpprogressroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lpdata = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* pbcancel = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    uint32_t dwcopyflags = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CopyFileExW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpexistingfilename, lpnewfilename, lpprogressroutine, lpdata, pbcancel, dwcopyflags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpexistingfilename;
    (void)lpnewfilename;
    (void)lpprogressroutine;
    (void)lpdata;
    (void)pbcancel;
    (void)dwcopyflags;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lppathname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpsecurityattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateDirectoryW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lppathname, lpsecurityattributes);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lppathname;
    (void)lpsecurityattributes;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpeventattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t bmanualreset = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int32_t binitialstate = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, int32_t, int32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateEventW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpeventattributes, bmanualreset, binitialstate, lpname);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpeventattributes;
    (void)bmanualreset;
    (void)binitialstate;
    (void)lpname;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwdesiredaccess = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint32_t dwsharemode = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpsecurityattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint32_t dwcreationdisposition = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    uint32_t dwflagsandattributes = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    void* htemplatefile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, void*, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateFileW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename, dwdesiredaccess, dwsharemode, lpsecurityattributes, dwcreationdisposition, dwflagsandattributes, htemplatefile);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpfilename;
    (void)dwdesiredaccess;
    (void)dwsharemode;
    (void)lpsecurityattributes;
    (void)dwcreationdisposition;
    (void)dwflagsandattributes;
    (void)htemplatefile;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpexistingfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lpsecurityattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateHardLinkW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename, lpexistingfilename, lpsecurityattributes);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpfilename;
    (void)lpexistingfilename;
    (void)lpsecurityattributes;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwopenmode = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint32_t dwpipemode = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t nmaxinstances = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    uint32_t noutbuffersize = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    uint32_t ninbuffersize = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    uint32_t ndefaulttimeout = static_cast<uint32_t>(km_info.Length() > 6 ? km_info[6].ToNumber().Int32Value() : 0);
    void* lpsecurityattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 7 ? km_napi_arg_u64(km_info[7]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateNamedPipeW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpname, dwopenmode, dwpipemode, nmaxinstances, noutbuffersize, ninbuffersize, ndefaulttimeout, lpsecurityattributes);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpname;
    (void)dwopenmode;
    (void)dwpipemode;
    (void)nmaxinstances;
    (void)noutbuffersize;
    (void)ninbuffersize;
    (void)ndefaulttimeout;
    (void)lpsecurityattributes;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hreadpipe = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* hwritepipe = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lppipeattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t nsize = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreatePipe")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hreadpipe, hwritepipe, lppipeattributes, nsize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hreadpipe;
    (void)hwritepipe;
    (void)lppipeattributes;
    (void)nsize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpapplicationname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpcommandline = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lpprocessattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lpthreadattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    int32_t binherithandles = static_cast<int32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    uint32_t dwcreationflags = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    void* lpenvironment = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
    void* lpcurrentdirectory = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 7 ? km_napi_arg_u64(km_info[7]) : 0));
    void* lpstartupinfo = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 8 ? km_napi_arg_u64(km_info[8]) : 0));
    void* lpprocessinformation = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 9 ? km_napi_arg_u64(km_info[9]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, int32_t, uint32_t, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateProcessW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpapplicationname, lpcommandline, lpprocessattributes, lpthreadattributes, binherithandles, dwcreationflags, lpenvironment, lpcurrentdirectory, lpstartupinfo, lpprocessinformation);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpapplicationname;
    (void)lpcommandline;
    (void)lpprocessattributes;
    (void)lpthreadattributes;
    (void)binherithandles;
    (void)dwcreationflags;
    (void)lpenvironment;
    (void)lpcurrentdirectory;
    (void)lpstartupinfo;
    (void)lpprocessinformation;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpsymlinkfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lptargetfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateSymbolicLinkW")));
    }
    uint8_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpsymlinkfilename, lptargetfilename, dwflags);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpsymlinkfilename;
    (void)lptargetfilename;
    (void)dwflags;
    errno = ENOSYS;
    uint8_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpthreadattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint64_t dwstacksize = km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0;
    void* lpstartaddress = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lpparameter = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint32_t dwcreationflags = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    void* lpthreadid = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint64_t, void*, void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateThread")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpthreadattributes, dwstacksize, lpstartaddress, lpparameter, dwcreationflags, lpthreadid);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpthreadattributes;
    (void)dwstacksize;
    (void)lpstartaddress;
    (void)lpparameter;
    (void)dwcreationflags;
    (void)lpthreadid;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lptimerattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lptimername = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t dwdesiredaccess = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "CreateWaitableTimerExW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lptimerattributes, lptimername, dwflags, dwdesiredaccess);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lptimerattributes;
    (void)lptimername;
    (void)dwflags;
    (void)dwdesiredaccess;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "DeleteFileW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpfilename;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpattributelist = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "DeleteProcThreadAttributeList")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(lpattributelist);
    } else {
        errno = ENOSYS;
    }
#else
    (void)lpattributelist;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hdevice = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwiocontrolcode = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpinbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t ninbuffersize = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* lpoutbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    uint32_t noutbuffersize = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    void* lpbytesreturned = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 7 ? km_napi_arg_u64(km_info[7]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, uint32_t, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "DeviceIoControl")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hdevice, dwiocontrolcode, lpinbuffer, ninbuffersize, lpoutbuffer, noutbuffersize, lpbytesreturned, lpoverlapped);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hdevice;
    (void)dwiocontrolcode;
    (void)lpinbuffer;
    (void)ninbuffersize;
    (void)lpoutbuffer;
    (void)noutbuffersize;
    (void)lpbytesreturned;
    (void)lpoverlapped;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hsourceprocesshandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* hsourcehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* htargetprocesshandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lptargethandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint32_t dwdesiredaccess = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    int32_t binherithandle = static_cast<int32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    uint32_t dwoptions = static_cast<uint32_t>(km_info.Length() > 6 ? km_info[6].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, uint32_t, int32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "DuplicateHandle")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hsourceprocesshandle, hsourcehandle, htargetprocesshandle, lptargethandle, dwdesiredaccess, binherithandle, dwoptions);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hsourceprocesshandle;
    (void)hsourcehandle;
    (void)htargetprocesshandle;
    (void)lptargethandle;
    (void)dwdesiredaccess;
    (void)binherithandle;
    (void)dwoptions;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t uexitcode = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ExitProcess")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(uexitcode);
    } else {
        errno = ENOSYS;
    }
#else
    (void)uexitcode;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfindfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FindClose")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfindfile);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfindfile;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t finfolevelid = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpfindfiledata = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    int32_t fsearchop = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* lpsearchfilter = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    uint32_t dwadditionalflags = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, int32_t, void*, int32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FindFirstFileExW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename, finfolevelid, lpfindfiledata, fsearchop, lpsearchfilter, dwadditionalflags);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpfilename;
    (void)finfolevelid;
    (void)lpfindfiledata;
    (void)fsearchop;
    (void)lpsearchfilter;
    (void)dwadditionalflags;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfindfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpfindfiledata = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FindNextFileW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfindfile, lpfindfiledata);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfindfile;
    (void)lpfindfiledata;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpcallback = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FlsAlloc")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpcallback);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpcallback;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwflsindex = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FlsFree")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwflsindex);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)dwflsindex;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwflsindex = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FlsGetValue")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwflsindex);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)dwflsindex;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwflsindex = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lpflsdata = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FlsSetValue")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwflsindex, lpflsdata);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)dwflsindex;
    (void)lpflsdata;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FlushFileBuffers")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lpsource = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwmessageid = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t dwlanguageid = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    uint32_t nsize = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    void* arguments = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*, uint32_t, uint32_t, void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FormatMessageW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwflags, lpsource, dwmessageid, dwlanguageid, lpbuffer, nsize, arguments);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)dwflags;
    (void)lpsource;
    (void)dwmessageid;
    (void)dwlanguageid;
    (void)lpbuffer;
    (void)nsize;
    (void)arguments;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* penv = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "FreeEnvironmentStringsW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(penv);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)penv;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint16_t groupnumber = static_cast<uint16_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint16_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetActiveProcessorCount")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(groupnumber);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)groupnumber;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetCommandLineW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hconsolehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpmode = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetConsoleMode")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hconsolehandle, lpmode);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hconsolehandle;
    (void)lpmode;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetConsoleOutputCP")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t nbufferlength = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetCurrentDirectoryW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(nbufferlength, lpbuffer);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)nbufferlength;
    (void)lpbuffer;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetCurrentProcess")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetCurrentProcessId")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetCurrentThread")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetCurrentThreadId")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetEnvironmentStringsW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nsize = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetEnvironmentVariableW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpname, lpbuffer, nsize);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpname;
    (void)lpbuffer;
    (void)nsize;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hprocess = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpexitcode = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetExitCodeProcess")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hprocess, lpexitcode);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hprocess;
    (void)lpexitcode;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFileAttributesW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpfilename;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpfileinformation = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFileInformationByHandle")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpfileinformation);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpfileinformation;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t fileinformationclass = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpfileinformation = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t dwbuffersize = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFileInformationByHandleEx")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, fileinformationclass, lpfileinformation, dwbuffersize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)fileinformationclass;
    (void)lpfileinformation;
    (void)dwbuffersize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpfilesize = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFileSizeEx")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpfilesize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpfilesize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFileType")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)hfile;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpszfilepath = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t cchfilepath = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFinalPathNameByHandleW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpszfilepath, cchfilepath, dwflags);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)hfile;
    (void)lpszfilepath;
    (void)cchfilepath;
    (void)dwflags;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t nbufferlength = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lpfilepart = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetFullPathNameW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename, nbufferlength, lpbuffer, lpfilepart);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpfilename;
    (void)nbufferlength;
    (void)lpbuffer;
    (void)lpfilepart;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* name = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t namelen = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetHostNameW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(name, namelen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetLastError")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetLogicalDrives")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hmodule = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nsize = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetModuleFileNameW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hmodule, lpfilename, nsize);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)hmodule;
    (void)lpfilename;
    (void)nsize;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpmodulename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetModuleHandleA")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpmodulename);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpmodulename;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lpmodulename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* phmodule = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetModuleHandleExW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwflags, lpmodulename, phmodule);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)dwflags;
    (void)lpmodulename;
    (void)phmodule;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpmodulename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetModuleHandleW")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpmodulename);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lpmodulename;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lpnumberofbytestransferred = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    int32_t bwait = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetOverlappedResult")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpoverlapped, lpnumberofbytestransferred, bwait);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpoverlapped;
    (void)lpnumberofbytestransferred;
    (void)bwait;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hmodule = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpprocname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetProcAddress")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hmodule, lpprocname);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)hmodule;
    (void)lpprocname;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* process = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetProcessId")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(process);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)process;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t nstdhandle = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetStdHandle")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(nstdhandle);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)nstdhandle;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t usize = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetSystemDirectoryW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpbuffer, usize);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpbuffer;
    (void)usize;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpsysteminfo = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetSystemInfo")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(lpsysteminfo);
    } else {
        errno = ENOSYS;
    }
#else
    (void)lpsysteminfo;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpsystemtimeasfiletime = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetSystemTimeAsFileTime")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(lpsystemtimeasfiletime);
    } else {
        errno = ENOSYS;
    }
#else
    (void)lpsystemtimeasfiletime;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpsystemtimeasfiletime = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetSystemTimePreciseAsFileTime")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(lpsystemtimeasfiletime);
    } else {
        errno = ENOSYS;
    }
#else
    (void)lpsystemtimeasfiletime;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t nbufferlength = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetTempPathW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(nbufferlength, lpbuffer);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)nbufferlength;
    (void)lpbuffer;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* htoken = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpprofiledir = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lpcchsize = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("userenv.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetUserProfileDirectoryW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(htoken, lpprofiledir, lpcchsize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)htoken;
    (void)lpprofiledir;
    (void)lpcchsize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t usize = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "GetWindowsDirectoryW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpbuffer, usize);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpbuffer;
    (void)usize;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpinitonce = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* fpending = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lpcontext = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "InitOnceBeginInitialize")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpinitonce, dwflags, fpending, lpcontext);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpinitonce;
    (void)dwflags;
    (void)fpending;
    (void)lpcontext;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpinitonce = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpcontext = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "InitOnceComplete")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpinitonce, dwflags, lpcontext);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpinitonce;
    (void)dwflags;
    (void)lpcontext;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpattributelist = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwattributecount = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpsize = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "InitializeProcThreadAttributeList")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpattributelist, dwattributecount, dwflags, lpsize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpattributelist;
    (void)dwattributecount;
    (void)dwflags;
    (void)lpsize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "IsThreadAFiber")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lplibfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "LoadLibraryExA")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lplibfilename, hfile, dwflags);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)lplibfilename;
    (void)hfile;
    (void)dwflags;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hmem = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "LocalFree")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hmem);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)hmem;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint32_t dwreserved = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t nnumberofbytestolocklow = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    uint32_t nnumberofbytestolockhigh = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "LockFileEx")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, dwflags, dwreserved, nnumberofbytestolocklow, nnumberofbytestolockhigh, lpoverlapped);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)dwflags;
    (void)dwreserved;
    (void)nnumberofbytestolocklow;
    (void)nnumberofbytestolockhigh;
    (void)lpoverlapped;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpexistingfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpnewfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "MoveFileExW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpexistingfilename, lpnewfilename, dwflags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpexistingfilename;
    (void)lpnewfilename;
    (void)dwflags;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t codepage = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpmultibytestr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    int32_t cbmultibyte = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* lpwidecharstr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    int32_t cchwidechar = static_cast<int32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, uint32_t, void*, int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "MultiByteToWideChar")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(codepage, dwflags, lpmultibytestr, cbmultibyte, lpwidecharstr, cchwidechar);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)codepage;
    (void)dwflags;
    (void)lpmultibytestr;
    (void)cbmultibyte;
    (void)lpwidecharstr;
    (void)cchwidechar;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* filehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t desiredaccess = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* objectattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* iostatusblock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* allocationsize = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    uint32_t fileattributes = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    uint32_t shareaccess = static_cast<uint32_t>(km_info.Length() > 6 ? km_info[6].ToNumber().Int32Value() : 0);
    uint32_t createdisposition = static_cast<uint32_t>(km_info.Length() > 7 ? km_info[7].ToNumber().Int32Value() : 0);
    uint32_t createoptions = static_cast<uint32_t>(km_info.Length() > 8 ? km_info[8].ToNumber().Int32Value() : 0);
    void* eabuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 9 ? km_napi_arg_u64(km_info[9]) : 0));
    uint32_t ealength = static_cast<uint32_t>(km_info.Length() > 10 ? km_info[10].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*, void*, uint32_t, uint32_t, uint32_t, uint32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "NtCreateFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(filehandle, desiredaccess, objectattributes, iostatusblock, allocationsize, fileattributes, shareaccess, createdisposition, createoptions, eabuffer, ealength);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)filehandle;
    (void)desiredaccess;
    (void)objectattributes;
    (void)iostatusblock;
    (void)allocationsize;
    (void)fileattributes;
    (void)shareaccess;
    (void)createdisposition;
    (void)createoptions;
    (void)eabuffer;
    (void)ealength;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* filehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t desiredaccess = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* objectattributes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* iostatusblock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint32_t shareaccess = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    uint32_t openoptions = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "NtOpenFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(filehandle, desiredaccess, objectattributes, iostatusblock, shareaccess, openoptions);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)filehandle;
    (void)desiredaccess;
    (void)objectattributes;
    (void)iostatusblock;
    (void)shareaccess;
    (void)openoptions;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* filehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* iostatusblock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* fsinformation = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t length = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    int32_t fsinformationclass = static_cast<int32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, uint32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "NtQueryVolumeInformationFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(filehandle, iostatusblock, fsinformation, length, fsinformationclass);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)filehandle;
    (void)iostatusblock;
    (void)fsinformation;
    (void)length;
    (void)fsinformationclass;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* filehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* event = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* apcroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* apccontext = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* iostatusblock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    void* buffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
    uint32_t length = static_cast<uint32_t>(km_info.Length() > 6 ? km_info[6].ToNumber().Int32Value() : 0);
    void* byteoffset = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 7 ? km_napi_arg_u64(km_info[7]) : 0));
    void* key = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 8 ? km_napi_arg_u64(km_info[8]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "NtReadFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(filehandle, event, apcroutine, apccontext, iostatusblock, buffer, length, byteoffset, key);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)filehandle;
    (void)event;
    (void)apcroutine;
    (void)apccontext;
    (void)iostatusblock;
    (void)buffer;
    (void)length;
    (void)byteoffset;
    (void)key;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* filehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* iostatusblock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* fileinformation = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t length = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    int32_t fileinformationclass = static_cast<int32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, uint32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "NtSetInformationFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(filehandle, iostatusblock, fileinformation, length, fileinformationclass);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)filehandle;
    (void)iostatusblock;
    (void)fileinformation;
    (void)length;
    (void)fileinformationclass;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* filehandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* event = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* apcroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* apccontext = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* iostatusblock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    void* buffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
    uint32_t length = static_cast<uint32_t>(km_info.Length() > 6 ? km_info[6].ToNumber().Int32Value() : 0);
    void* byteoffset = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 7 ? km_napi_arg_u64(km_info[7]) : 0));
    void* key = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 8 ? km_napi_arg_u64(km_info[8]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "NtWriteFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(filehandle, event, apcroutine, apccontext, iostatusblock, buffer, length, byteoffset, key);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)filehandle;
    (void)event;
    (void)apcroutine;
    (void)apccontext;
    (void)iostatusblock;
    (void)buffer;
    (void)length;
    (void)byteoffset;
    (void)key;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* processhandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t desiredaccess = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* tokenhandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("advapi32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "OpenProcessToken")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(processhandle, desiredaccess, tokenhandle);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)processhandle;
    (void)desiredaccess;
    (void)tokenhandle;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpdevicename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lptargetpath = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t ucchmax = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "QueryDosDeviceW")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpdevicename, lptargetpath, ucchmax);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)lpdevicename;
    (void)lptargetpath;
    (void)ucchmax;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpperformancecount = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "QueryPerformanceCounter")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpperformancecount);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpperformancecount;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfrequency = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "QueryPerformanceFrequency")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfrequency);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpfrequency;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hconsoleinput = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nnumberofcharstoread = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpnumberofcharsread = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* pinputcontrol = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ReadConsoleW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hconsoleinput, lpbuffer, nnumberofcharstoread, lpnumberofcharsread, pinputcontrol);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hconsoleinput;
    (void)lpbuffer;
    (void)nnumberofcharstoread;
    (void)lpnumberofcharsread;
    (void)pinputcontrol;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nnumberofbytestoread = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpnumberofbytesread = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ReadFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpbuffer, nnumberofbytestoread, lpnumberofbytesread, lpoverlapped);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpbuffer;
    (void)nnumberofbytestoread;
    (void)lpnumberofbytesread;
    (void)lpoverlapped;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nnumberofbytestoread = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* lpcompletionroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ReadFileEx")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpbuffer, nnumberofbytestoread, lpoverlapped, lpcompletionroutine);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpbuffer;
    (void)nnumberofbytestoread;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ReleaseSRWLockExclusive")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(srwlock);
    } else {
        errno = ENOSYS;
    }
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ReleaseSRWLockShared")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(srwlock);
    } else {
        errno = ENOSYS;
    }
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lppathname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "RemoveDirectoryW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lppathname);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lppathname;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* randombuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t randombufferlength = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("advapi32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SystemFunction036")));
    }
    uint8_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(randombuffer, randombufferlength);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)randombuffer;
    (void)randombufferlength;
    errno = ENOSYS;
    uint8_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t status = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "RtlNtStatusToDosError")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(status);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)status;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lppathname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetCurrentDirectoryW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lppathname);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lppathname;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpname = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpvalue = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetEnvironmentVariableW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpname, lpvalue);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpname;
    (void)lpvalue;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpfilename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwfileattributes = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetFileAttributesW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpfilename, dwfileattributes);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpfilename;
    (void)dwfileattributes;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int32_t fileinformationclass = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpfileinformation = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t dwbuffersize = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetFileInformationByHandle")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, fileinformationclass, lpfileinformation, dwbuffersize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)fileinformationclass;
    (void)lpfileinformation;
    (void)dwbuffersize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    int64_t lidistancetomove = km_info.Length() > 1 ? km_napi_arg_i64(km_info[1]) : 0;
    void* lpnewfilepointer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    uint32_t dwmovemethod = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int64_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetFilePointerEx")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lidistancetomove, lpnewfilepointer, dwmovemethod);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lidistancetomove;
    (void)lpnewfilepointer;
    (void)dwmovemethod;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpcreationtime = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* lplastaccesstime = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* lplastwritetime = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetFileTime")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpcreationtime, lplastaccesstime, lplastwritetime);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpcreationtime;
    (void)lplastaccesstime;
    (void)lplastwritetime;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hobject = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwmask = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetHandleInformation")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hobject, dwmask, dwflags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hobject;
    (void)dwmask;
    (void)dwflags;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwerrcode = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetLastError")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dwerrcode);
    } else {
        errno = ENOSYS;
    }
#else
    (void)dwerrcode;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t nstdhandle = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* hhandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetStdHandle")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(nstdhandle, hhandle);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)nstdhandle;
    (void)hhandle;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* stacksizeinbytes = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetThreadStackGuarantee")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(stacksizeinbytes);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)stacksizeinbytes;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* htimer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpduetime = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t lperiod = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* pfncompletionroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* lpargtocompletionroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    int32_t fresume = static_cast<int32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, int32_t, void*, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SetWaitableTimer")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(htimer, lpduetime, lperiod, pfncompletionroutine, lpargtocompletionroutine, fresume);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)htimer;
    (void)lpduetime;
    (void)lperiod;
    (void)pfncompletionroutine;
    (void)lpargtocompletionroutine;
    (void)fresume;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSleep_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwmilliseconds = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "Sleep")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(dwmilliseconds);
    } else {
        errno = ENOSYS;
    }
#else
    (void)dwmilliseconds;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* conditionvariable = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwmilliseconds = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t flags = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SleepConditionVariableSRW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(conditionvariable, srwlock, dwmilliseconds, flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)conditionvariable;
    (void)srwlock;
    (void)dwmilliseconds;
    (void)flags;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwmilliseconds = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t balertable = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SleepEx")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwmilliseconds, balertable);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)dwmilliseconds;
    (void)balertable;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "SwitchToThread")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hprocess = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t uexitcode = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TerminateProcess")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hprocess, uexitcode);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hprocess;
    (void)uexitcode;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TlsAlloc")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwtlsindex = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TlsFree")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwtlsindex);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)dwtlsindex;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwtlsindex = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TlsGetValue")));
    }
    void* km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwtlsindex);
    } else {
        errno = ENOSYS;
        km_res = nullptr;
    }
#else
    (void)dwtlsindex;
    errno = ENOSYS;
    void* km_res = nullptr;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<int64_t>(reinterpret_cast<uintptr_t>(km_res))));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t dwtlsindex = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lptlsvalue = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TlsSetValue")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(dwtlsindex, lptlsvalue);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)dwtlsindex;
    (void)lptlsvalue;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TryAcquireSRWLockExclusive")));
    }
    uint8_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(srwlock);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)srwlock;
    errno = ENOSYS;
    uint8_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* srwlock = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "TryAcquireSRWLockShared")));
    }
    uint8_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(srwlock);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)srwlock;
    errno = ENOSYS;
    uint8_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwfileoffsetlow = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint32_t dwfileoffsethigh = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t nnumberofbytestounlocklow = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    uint32_t nnumberofbytestounlockhigh = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "UnlockFile")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, dwfileoffsetlow, dwfileoffsethigh, nnumberofbytestounlocklow, nnumberofbytestounlockhigh);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)dwfileoffsetlow;
    (void)dwfileoffsethigh;
    (void)nnumberofbytestounlocklow;
    (void)nnumberofbytestounlockhigh;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpattributelist = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    uint64_t attribute = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    void* lpvalue = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint64_t cbsize = km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0;
    void* lppreviousvalue = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
    void* lpreturnsize = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint64_t, void*, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "UpdateProcThreadAttribute")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpattributelist, dwflags, attribute, lpvalue, cbsize, lppreviousvalue, lpreturnsize);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpattributelist;
    (void)dwflags;
    (void)attribute;
    (void)lpvalue;
    (void)cbsize;
    (void)lppreviousvalue;
    (void)lpreturnsize;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSACleanup")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    uint32_t dwprocessid = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpprotocolinfo = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSADuplicateSocketW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, dwprocessid, lpprotocolinfo);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)dwprocessid;
    (void)lpprotocolinfo;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSAGetLastError")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* lpbuffers = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwbuffercount = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpnumberofbytesrecvd = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* lpflags = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
    void* lpcompletionroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, uint32_t, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSARecv")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, lpbuffers, dwbuffercount, lpnumberofbytesrecvd, lpflags, lpoverlapped, lpcompletionroutine);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)lpbuffers;
    (void)dwbuffercount;
    (void)lpnumberofbytesrecvd;
    (void)lpflags;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* lpbuffers = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t dwbuffercount = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpnumberofbytessent = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
    void* lpcompletionroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, uint32_t, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSASend")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, lpbuffers, dwbuffercount, lpnumberofbytessent, dwflags, lpoverlapped, lpcompletionroutine);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)lpbuffers;
    (void)dwbuffercount;
    (void)lpnumberofbytessent;
    (void)dwflags;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t af = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    int32_t type = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int32_t protocol = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpprotocolinfo = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    uint32_t g = static_cast<uint32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint64_t (__stdcall *km_fn_t)(int32_t, int32_t, int32_t, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSASocketW")));
    }
    uint64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(af, type, protocol, lpprotocolinfo, g, dwflags);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)af;
    (void)type;
    (void)protocol;
    (void)lpprotocolinfo;
    (void)g;
    (void)dwflags;
    errno = ENOSYS;
    uint64_t km_res = 0;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<uint64_t>(km_res)));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint16_t wversionrequested = static_cast<uint16_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lpwsadata = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint16_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WSAStartup")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(wversionrequested, lpwsadata);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)wversionrequested;
    (void)lpwsadata;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t ncount = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* lphandles = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t bwaitall = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    uint32_t dwmilliseconds = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*, int32_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WaitForMultipleObjects")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(ncount, lphandles, bwaitall, dwmilliseconds);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)ncount;
    (void)lphandles;
    (void)bwaitall;
    (void)dwmilliseconds;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hhandle = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    uint32_t dwmilliseconds = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WaitForSingleObject")));
    }
    uint32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hhandle, dwmilliseconds);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)hhandle;
    (void)dwmilliseconds;
    errno = ENOSYS;
    uint32_t km_res = 0;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* address = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* compareaddress = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint64_t addresssize = km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0;
    uint32_t dwmilliseconds = static_cast<uint32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint64_t, uint32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("api-ms-win-core-synch-l1-2-0.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WaitOnAddress")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(address, compareaddress, addresssize, dwmilliseconds);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)address;
    (void)compareaddress;
    (void)addresssize;
    (void)dwmilliseconds;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* conditionvariable = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WakeAllConditionVariable")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(conditionvariable);
    } else {
        errno = ENOSYS;
    }
#else
    (void)conditionvariable;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* address = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("api-ms-win-core-synch-l1-2-0.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WakeByAddressAll")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(address);
    } else {
        errno = ENOSYS;
    }
#else
    (void)address;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* address = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("api-ms-win-core-synch-l1-2-0.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WakeByAddressSingle")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(address);
    } else {
        errno = ENOSYS;
    }
#else
    (void)address;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* conditionvariable = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WakeConditionVariable")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(conditionvariable);
    } else {
        errno = ENOSYS;
    }
#else
    (void)conditionvariable;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint32_t codepage = static_cast<uint32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    uint32_t dwflags = static_cast<uint32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* lpwidecharstr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    int32_t cchwidechar = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* lpmultibytestr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    int32_t cbmultibyte = static_cast<int32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
    void* lpdefaultchar = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 6 ? km_napi_arg_u64(km_info[6]) : 0));
    void* lpuseddefaultchar = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 7 ? km_napi_arg_u64(km_info[7]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, uint32_t, void*, int32_t, void*, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WideCharToMultiByte")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(codepage, dwflags, lpwidecharstr, cchwidechar, lpmultibytestr, cbmultibyte, lpdefaultchar, lpuseddefaultchar);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)codepage;
    (void)dwflags;
    (void)lpwidecharstr;
    (void)cchwidechar;
    (void)lpmultibytestr;
    (void)cbmultibyte;
    (void)lpdefaultchar;
    (void)lpuseddefaultchar;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hconsoleoutput = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nnumberofcharstowrite = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpnumberofcharswritten = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* lpreserved = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WriteConsoleW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hconsoleoutput, lpbuffer, nnumberofcharstowrite, lpnumberofcharswritten, lpreserved);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hconsoleoutput;
    (void)lpbuffer;
    (void)nnumberofcharstowrite;
    (void)lpnumberofcharswritten;
    (void)lpreserved;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* hfile = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* lpbuffer = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    uint32_t nnumberofbytestowrite = static_cast<uint32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* lpoverlapped = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* lpcompletionroutine = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "WriteFileEx")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(hfile, lpbuffer, nnumberofbytestowrite, lpoverlapped, lpcompletionroutine);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hfile;
    (void)lpbuffer;
    (void)nnumberofbytestowrite;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysAccept_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* addr = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* addrlen = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef uint64_t (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "accept")));
    }
    uint64_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, addr, addrlen);
    } else {
        errno = ENOSYS;
        km_res = 0;
    }
#else
    (void)s;
    (void)addr;
    (void)addrlen;
    errno = ENOSYS;
    uint64_t km_res = 0;
#endif
    return Napi::String::New(km_env, std::to_string(static_cast<uint64_t>(km_res)));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysBind_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* name = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t namelen = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "bind")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, name, namelen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "closesocket")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysConnect_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* name = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t namelen = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "connect")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, name, namelen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* paddrinfo = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "freeaddrinfo")));
    }
    if (km_fn_ptr != nullptr) {
        km_fn_ptr(paddrinfo);
    } else {
        errno = ENOSYS;
    }
#else
    (void)paddrinfo;
    errno = ENOSYS;
#endif
    return km_env.Undefined();
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* pnodename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
    void* pservicename = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* phints = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* ppresult = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "getaddrinfo")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(pnodename, pservicename, phints, ppresult);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)pnodename;
    (void)pservicename;
    (void)phints;
    (void)ppresult;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* name = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* namelen = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "getpeername")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, name, namelen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* name = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* namelen = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "getsockname")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, name, namelen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    int32_t level = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int32_t optname = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* optval = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* optlen = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "getsockopt")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, level, optname, optval, optlen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)level;
    (void)optname;
    (void)optval;
    (void)optlen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    int32_t cmd = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    void* argp = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "ioctlsocket")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, cmd, argp);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)cmd;
    (void)argp;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysListen_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    int32_t backlog = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "listen")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, backlog);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)backlog;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    void* lpstring = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "lstrlenW")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(lpstring);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)lpstring;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysRecv_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* buf = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t len = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    int32_t flags = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "recv")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, buf, len, flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* buf = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t len = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    int32_t flags = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* from = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    void* fromlen = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 5 ? km_napi_arg_u64(km_info[5]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "recvfrom")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, buf, len, flags, from, fromlen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    (void)from;
    (void)fromlen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSelect_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    int32_t nfds = static_cast<int32_t>(km_info.Length() > 0 ? km_info[0].ToNumber().Int32Value() : 0);
    void* readfds = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    void* writefds = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 2 ? km_napi_arg_u64(km_info[2]) : 0));
    void* exceptfds = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    void* timeout = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(int32_t, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "select")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(nfds, readfds, writefds, exceptfds, timeout);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)nfds;
    (void)readfds;
    (void)writefds;
    (void)exceptfds;
    (void)timeout;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSend_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* buf = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t len = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    int32_t flags = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "send")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, buf, len, flags);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSendto_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    void* buf = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 1 ? km_napi_arg_u64(km_info[1]) : 0));
    int32_t len = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    int32_t flags = static_cast<int32_t>(km_info.Length() > 3 ? km_info[3].ToNumber().Int32Value() : 0);
    void* to = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 4 ? km_napi_arg_u64(km_info[4]) : 0));
    int32_t tolen = static_cast<int32_t>(km_info.Length() > 5 ? km_info[5].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "sendto")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, buf, len, flags, to, tolen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    (void)to;
    (void)tolen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    int32_t level = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
    int32_t optname = static_cast<int32_t>(km_info.Length() > 2 ? km_info[2].ToNumber().Int32Value() : 0);
    void* optval = reinterpret_cast<void*>(static_cast<uintptr_t>(km_info.Length() > 3 ? km_napi_arg_u64(km_info[3]) : 0));
    int32_t optlen = static_cast<int32_t>(km_info.Length() > 4 ? km_info[4].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t, int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "setsockopt")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, level, optname, optval, optlen);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)level;
    (void)optname;
    (void)optval;
    (void)optlen;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static Napi::Value rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown_napi(const Napi::CallbackInfo& km_info) {
    Napi::Env km_env = km_info.Env();
    uint64_t s = km_info.Length() > 0 ? km_napi_arg_u64(km_info[0]) : 0;
    int32_t how = static_cast<int32_t>(km_info.Length() > 1 ? km_info[1].ToNumber().Int32Value() : 0);
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t);
    static km_fn_t km_fn_ptr = nullptr;
    if (km_fn_ptr == nullptr) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != nullptr) km_fn_ptr = reinterpret_cast<km_fn_t>(reinterpret_cast<void*>(GetProcAddress(km_h_mod, "shutdown")));
    }
    int32_t km_res;
    if (km_fn_ptr != nullptr) {
        km_res = km_fn_ptr(s, how);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)s;
    (void)how;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return Napi::Number::New(km_env, static_cast<int32_t>(km_res));
}

static void register_std_sys_pal_windows_c_windowssys_windows_sys_napi(Napi::Env env, Napi::Object exports) {
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSleep", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSleep_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysAccept", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysAccept_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysBind", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysBind_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysConnect", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysConnect_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysListen", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysListen_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysRecv", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysRecv_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSelect", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSelect_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSend", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSend_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSendto", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSendto_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt_napi));
    exports.Set("rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown", Napi::Function::New(env, rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown_napi));
}

static KmNapiRegistrarNode registrar_node = { &register_std_sys_pal_windows_c_windowssys_windows_sys_napi, nullptr };
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
