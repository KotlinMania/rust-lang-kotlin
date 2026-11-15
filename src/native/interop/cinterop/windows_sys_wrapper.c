#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "windows_sys_wrapper.h"
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

int32_t rustlangWindowsSysEnumerateLoadedModulesW64(void* hprocess, void* enumloadedmodulescallback, void* usercontext) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "EnumerateLoadedModulesW64");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, enumloadedmodulescallback, usercontext);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)enumloadedmodulescallback;
    (void)usercontext;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysStackWalk64(uint32_t machinetype, void* hprocess, void* hthread, void* stackframe, void* contextrecord, void* readmemoryroutine, void* functiontableaccessroutine, void* getmodulebaseroutine, void* translateaddress) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*, void*, void*, void*, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "StackWalk64");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(machinetype, hprocess, hthread, stackframe, contextrecord, readmemoryroutine, functiontableaccessroutine, getmodulebaseroutine, translateaddress);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)machinetype;
    (void)hprocess;
    (void)hthread;
    (void)stackframe;
    (void)contextrecord;
    (void)readmemoryroutine;
    (void)functiontableaccessroutine;
    (void)getmodulebaseroutine;
    (void)translateaddress;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysStackWalkEx(uint32_t machinetype, void* hprocess, void* hthread, void* stackframe, void* contextrecord, void* readmemoryroutine, void* functiontableaccessroutine, void* getmodulebaseroutine, void* translateaddress, uint32_t flags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*, void*, void*, void*, void*, void*, void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "StackWalkEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(machinetype, hprocess, hthread, stackframe, contextrecord, readmemoryroutine, functiontableaccessroutine, getmodulebaseroutine, translateaddress, flags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)machinetype;
    (void)hprocess;
    (void)hthread;
    (void)stackframe;
    (void)contextrecord;
    (void)readmemoryroutine;
    (void)functiontableaccessroutine;
    (void)getmodulebaseroutine;
    (void)translateaddress;
    (void)flags;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t rustlangWindowsSysSymAddrIncludeInlineTrace(void* hprocess, uint64_t address) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymAddrIncludeInlineTrace");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, address);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hprocess;
    (void)address;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t rustlangWindowsSysSymFromAddrW(void* hprocess, uint64_t address, void* displacement, void* symbol) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymFromAddrW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, address, displacement, symbol);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)address;
    (void)displacement;
    (void)symbol;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysSymFromInlineContextW(void* hprocess, uint64_t address, uint32_t inlinecontext, void* displacement, void* symbol) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint64_t, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymFromInlineContextW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, address, inlinecontext, displacement, symbol);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)address;
    (void)inlinecontext;
    (void)displacement;
    (void)symbol;
    errno = ENOSYS;
    return -1;
#endif
}

void* rustlangWindowsSysSymFunctionTableAccess64(void* hprocess, uint64_t addrbase) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymFunctionTableAccess64");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, addrbase);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)hprocess;
    (void)addrbase;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t rustlangWindowsSysSymGetLineFromAddrW64(void* hprocess, uint64_t dwaddr, void* pdwdisplacement, void* line) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymGetLineFromAddrW64");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, dwaddr, pdwdisplacement, line);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)dwaddr;
    (void)pdwdisplacement;
    (void)line;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysSymGetLineFromInlineContextW(void* hprocess, uint64_t dwaddr, uint32_t inlinecontext, uint64_t qwmodulebaseaddress, void* pdwdisplacement, void* line) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint64_t, uint32_t, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymGetLineFromInlineContextW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, dwaddr, inlinecontext, qwmodulebaseaddress, pdwdisplacement, line);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)dwaddr;
    (void)inlinecontext;
    (void)qwmodulebaseaddress;
    (void)pdwdisplacement;
    (void)line;
    errno = ENOSYS;
    return -1;
#endif
}

uint64_t rustlangWindowsSysSymGetModuleBase64(void* hprocess, uint64_t qwaddr) {
#ifdef _WIN32
    typedef uint64_t (__stdcall *km_fn_t)(void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymGetModuleBase64");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, qwaddr);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hprocess;
    (void)qwaddr;
    errno = ENOSYS;
    return 0;
#endif
}

uint32_t rustlangWindowsSysSymGetOptions(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymGetOptions");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return 0;
#else
    errno = ENOSYS;
    return 0;
#endif
}

int32_t rustlangWindowsSysSymGetSearchPathW(void* hprocess, void* searchpatha, uint32_t searchpathlength) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymGetSearchPathW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, searchpatha, searchpathlength);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)searchpatha;
    (void)searchpathlength;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysSymInitializeW(void* hprocess, void* usersearchpath, int32_t finvadeprocess) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymInitializeW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, usersearchpath, finvadeprocess);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)usersearchpath;
    (void)finvadeprocess;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysSymQueryInlineTrace(void* hprocess, uint64_t startaddress, uint32_t startcontext, uint64_t startretaddress, uint64_t curaddress, void* curcontext, void* curframeindex) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint64_t, uint32_t, uint64_t, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymQueryInlineTrace");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, startaddress, startcontext, startretaddress, curaddress, curcontext, curframeindex);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)startaddress;
    (void)startcontext;
    (void)startretaddress;
    (void)curaddress;
    (void)curcontext;
    (void)curframeindex;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t rustlangWindowsSysSymSetOptions(uint32_t symoptions) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymSetOptions");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(symoptions);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)symoptions;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t rustlangWindowsSysSymSetSearchPathW(void* hprocess, void* searchpatha) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("dbghelp.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SymSetSearchPathW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, searchpatha);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)searchpatha;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysCloseHandle(void* hobject) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CloseHandle");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hobject);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hobject;
    errno = ENOSYS;
    return -1;
#endif
}

void* rustlangWindowsSysCreateFileMappingA(void* hfile, void* lpfilemappingattributes, uint32_t flprotect, uint32_t dwmaximumsizehigh, uint32_t dwmaximumsizelow, void* lpname) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateFileMappingA");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpfilemappingattributes, flprotect, dwmaximumsizehigh, dwmaximumsizelow, lpname);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)hfile;
    (void)lpfilemappingattributes;
    (void)flprotect;
    (void)dwmaximumsizehigh;
    (void)dwmaximumsizelow;
    (void)lpname;
    errno = ENOSYS;
    return NULL;
#endif
}

void* rustlangWindowsSysCreateMutexA(void* lpmutexattributes, int32_t binitialowner, void* lpname) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateMutexA");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpmutexattributes, binitialowner, lpname);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpmutexattributes;
    (void)binitialowner;
    (void)lpname;
    errno = ENOSYS;
    return NULL;
#endif
}

void* rustlangWindowsSysCreateToolhelp32Snapshot(uint32_t dwflags, uint32_t th32processid) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateToolhelp32Snapshot");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwflags, th32processid);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)dwflags;
    (void)th32processid;
    errno = ENOSYS;
    return NULL;
#endif
}

void* rustlangWindowsSysGetCurrentProcess(void) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetCurrentProcess");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return NULL;
#else
    errno = ENOSYS;
    return NULL;
#endif
}

uint32_t rustlangWindowsSysGetCurrentProcessId(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetCurrentProcessId");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return 0;
#else
    errno = ENOSYS;
    return 0;
#endif
}

void* rustlangWindowsSysGetCurrentThread(void) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetCurrentThread");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return NULL;
#else
    errno = ENOSYS;
    return NULL;
#endif
}

void* rustlangWindowsSysGetProcAddress(void* hmodule, void* lpprocname) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetProcAddress");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hmodule, lpprocname);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)hmodule;
    (void)lpprocname;
    errno = ENOSYS;
    return NULL;
#endif
}

void* rustlangWindowsSysLoadLibraryA(void* lplibfilename) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "LoadLibraryA");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lplibfilename);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lplibfilename;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t rustlangWindowsSysModule32FirstW(void* hsnapshot, void* lpme) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "Module32FirstW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hsnapshot, lpme);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hsnapshot;
    (void)lpme;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysModule32NextW(void* hsnapshot, void* lpme) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "Module32NextW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hsnapshot, lpme);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hsnapshot;
    (void)lpme;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t rustlangWindowsSysReleaseMutex(void* hmutex) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ReleaseMutex");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hmutex);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hmutex;
    errno = ENOSYS;
    return -1;
#endif
}

void rustlangWindowsSysRtlCaptureContext(void* contextrecord) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "RtlCaptureContext");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(contextrecord);
        return;
    }
    errno = ENOSYS;
#else
    (void)contextrecord;
    errno = ENOSYS;
#endif
}

void* rustlangWindowsSysRtlLookupFunctionEntry(uint64_t controlpc, void* imagebase, void* historytable) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "RtlLookupFunctionEntry");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(controlpc, imagebase, historytable);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)controlpc;
    (void)imagebase;
    (void)historytable;
    errno = ENOSYS;
    return NULL;
#endif
}

void* rustlangWindowsSysRtlVirtualUnwind(uint32_t handlertype, uint64_t imagebase, uint64_t controlpc, void* functionentry, void* contextrecord, void* handlerdata, void* establisherframe, void* contextpointers) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t, uint64_t, uint64_t, void*, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "RtlVirtualUnwind");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(handlertype, imagebase, controlpc, functionentry, contextrecord, handlerdata, establisherframe, contextpointers);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)handlertype;
    (void)imagebase;
    (void)controlpc;
    (void)functionentry;
    (void)contextrecord;
    (void)handlerdata;
    (void)establisherframe;
    (void)contextpointers;
    errno = ENOSYS;
    return NULL;
#endif
}

uint32_t rustlangWindowsSysWaitForSingleObjectEx(void* hhandle, uint32_t dwmilliseconds, int32_t balertable) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WaitForSingleObjectEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hhandle, dwmilliseconds, balertable);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hhandle;
    (void)dwmilliseconds;
    (void)balertable;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t rustlangWindowsSysWideCharToMultiByte(uint32_t codepage, uint32_t dwflags, void* lpwidecharstr, int32_t cchwidechar, void* lpmultibytestr, int32_t cbmultibyte, void* lpdefaultchar, void* lpuseddefaultchar) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, uint32_t, void*, int32_t, void*, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WideCharToMultiByte");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(codepage, dwflags, lpwidecharstr, cchwidechar, lpmultibytestr, cbmultibyte, lpdefaultchar, lpuseddefaultchar);
    }
    errno = ENOSYS;
    return -1;
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
    return -1;
#endif
}

int32_t rustlangWindowsSysLstrlenW(void* lpstring) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "lstrlenW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpstring);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpstring;
    errno = ENOSYS;
    return -1;
#endif
}

