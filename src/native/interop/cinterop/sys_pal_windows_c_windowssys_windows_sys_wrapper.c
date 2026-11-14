#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "sys_pal_windows_c_windowssys_windows_sys_wrapper.h"
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

void stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(void* srwlock) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "AcquireSRWLockExclusive");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(srwlock);
        return;
    }
    errno = ENOSYS;
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(void* srwlock) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "AcquireSRWLockShared");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(srwlock);
        return;
    }
    errno = ENOSYS;
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(uint32_t first, void* handler) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "AddVectoredExceptionHandler");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(first, handler);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)first;
    (void)handler;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCancelIo(void* hfile) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CancelIo");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCloseHandle(void* hobject) {
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

int32_t stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(void* lpstring1, int32_t cchcount1, void* lpstring2, int32_t cchcount2, int32_t bignorecase) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CompareStringOrdinal");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpstring1, cchcount1, lpstring2, cchcount2, bignorecase);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpstring1;
    (void)cchcount1;
    (void)lpstring2;
    (void)cchcount2;
    (void)bignorecase;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(void* lpexistingfilename, void* lpnewfilename, void* lpprogressroutine, void* lpdata, void* pbcancel, uint32_t dwcopyflags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CopyFileExW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpexistingfilename, lpnewfilename, lpprogressroutine, lpdata, pbcancel, dwcopyflags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpexistingfilename;
    (void)lpnewfilename;
    (void)lpprogressroutine;
    (void)lpdata;
    (void)pbcancel;
    (void)dwcopyflags;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(void* lppathname, void* lpsecurityattributes) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lppathname, lpsecurityattributes);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lppathname;
    (void)lpsecurityattributes;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysCreateEventW(void* lpeventattributes, int32_t bmanualreset, int32_t binitialstate, void* lpname) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, int32_t, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateEventW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpeventattributes, bmanualreset, binitialstate, lpname);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpeventattributes;
    (void)bmanualreset;
    (void)binitialstate;
    (void)lpname;
    errno = ENOSYS;
    return NULL;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysCreateFileW(void* lpfilename, uint32_t dwdesiredaccess, uint32_t dwsharemode, void* lpsecurityattributes, uint32_t dwcreationdisposition, uint32_t dwflagsandattributes, void* htemplatefile) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, void*, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateFileW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename, dwdesiredaccess, dwsharemode, lpsecurityattributes, dwcreationdisposition, dwflagsandattributes, htemplatefile);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpfilename;
    (void)dwdesiredaccess;
    (void)dwsharemode;
    (void)lpsecurityattributes;
    (void)dwcreationdisposition;
    (void)dwflagsandattributes;
    (void)htemplatefile;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(void* lpfilename, void* lpexistingfilename, void* lpsecurityattributes) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateHardLinkW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename, lpexistingfilename, lpsecurityattributes);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpfilename;
    (void)lpexistingfilename;
    (void)lpsecurityattributes;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(void* lpname, uint32_t dwopenmode, uint32_t dwpipemode, uint32_t nmaxinstances, uint32_t noutbuffersize, uint32_t ninbuffersize, uint32_t ndefaulttimeout, void* lpsecurityattributes) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateNamedPipeW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpname, dwopenmode, dwpipemode, nmaxinstances, noutbuffersize, ninbuffersize, ndefaulttimeout, lpsecurityattributes);
    }
    errno = ENOSYS;
    return NULL;
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
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCreatePipe(void* hreadpipe, void* hwritepipe, void* lppipeattributes, uint32_t nsize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreatePipe");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hreadpipe, hwritepipe, lppipeattributes, nsize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hreadpipe;
    (void)hwritepipe;
    (void)lppipeattributes;
    (void)nsize;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(void* lpapplicationname, void* lpcommandline, void* lpprocessattributes, void* lpthreadattributes, int32_t binherithandles, uint32_t dwcreationflags, void* lpenvironment, void* lpcurrentdirectory, void* lpstartupinfo, void* lpprocessinformation) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, int32_t, uint32_t, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateProcessW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpapplicationname, lpcommandline, lpprocessattributes, lpthreadattributes, binherithandles, dwcreationflags, lpenvironment, lpcurrentdirectory, lpstartupinfo, lpprocessinformation);
    }
    errno = ENOSYS;
    return -1;
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
    return -1;
#endif
}

uint8_t stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(void* lpsymlinkfilename, void* lptargetfilename, uint32_t dwflags) {
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateSymbolicLinkW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpsymlinkfilename, lptargetfilename, dwflags);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpsymlinkfilename;
    (void)lptargetfilename;
    (void)dwflags;
    errno = ENOSYS;
    return 0;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysCreateThread(void* lpthreadattributes, uint64_t dwstacksize, void* lpstartaddress, void* lpparameter, uint32_t dwcreationflags, void* lpthreadid) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint64_t, void*, void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateThread");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpthreadattributes, dwstacksize, lpstartaddress, lpparameter, dwcreationflags, lpthreadid);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpthreadattributes;
    (void)dwstacksize;
    (void)lpstartaddress;
    (void)lpparameter;
    (void)dwcreationflags;
    (void)lpthreadid;
    errno = ENOSYS;
    return NULL;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(void* lptimerattributes, void* lptimername, uint32_t dwflags, uint32_t dwdesiredaccess) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "CreateWaitableTimerExW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lptimerattributes, lptimername, dwflags, dwdesiredaccess);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lptimerattributes;
    (void)lptimername;
    (void)dwflags;
    (void)dwdesiredaccess;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(void* lpfilename) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "DeleteFileW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpfilename;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(void* lpattributelist) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "DeleteProcThreadAttributeList");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(lpattributelist);
        return;
    }
    errno = ENOSYS;
#else
    (void)lpattributelist;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(void* hdevice, uint32_t dwiocontrolcode, void* lpinbuffer, uint32_t ninbuffersize, void* lpoutbuffer, uint32_t noutbuffersize, void* lpbytesreturned, void* lpoverlapped) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, uint32_t, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "DeviceIoControl");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hdevice, dwiocontrolcode, lpinbuffer, ninbuffersize, lpoutbuffer, noutbuffersize, lpbytesreturned, lpoverlapped);
    }
    errno = ENOSYS;
    return -1;
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
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(void* hsourceprocesshandle, void* hsourcehandle, void* htargetprocesshandle, void* lptargethandle, uint32_t dwdesiredaccess, int32_t binherithandle, uint32_t dwoptions) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, uint32_t, int32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "DuplicateHandle");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hsourceprocesshandle, hsourcehandle, htargetprocesshandle, lptargethandle, dwdesiredaccess, binherithandle, dwoptions);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hsourceprocesshandle;
    (void)hsourcehandle;
    (void)htargetprocesshandle;
    (void)lptargethandle;
    (void)dwdesiredaccess;
    (void)binherithandle;
    (void)dwoptions;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysExitProcess(uint32_t uexitcode) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ExitProcess");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(uexitcode);
        return;
    }
    errno = ENOSYS;
#else
    (void)uexitcode;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysFindClose(void* hfindfile) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FindClose");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfindfile);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfindfile;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(void* lpfilename, int32_t finfolevelid, void* lpfindfiledata, int32_t fsearchop, void* lpsearchfilter, uint32_t dwadditionalflags) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, int32_t, void*, int32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FindFirstFileExW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename, finfolevelid, lpfindfiledata, fsearchop, lpsearchfilter, dwadditionalflags);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpfilename;
    (void)finfolevelid;
    (void)lpfindfiledata;
    (void)fsearchop;
    (void)lpsearchfilter;
    (void)dwadditionalflags;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(void* hfindfile, void* lpfindfiledata) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FindNextFileW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfindfile, lpfindfiledata);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfindfile;
    (void)lpfindfiledata;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(void* lpcallback) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FlsAlloc");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpcallback);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpcallback;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysFlsFree(uint32_t dwflsindex) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FlsFree");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwflsindex);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)dwflsindex;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(uint32_t dwflsindex) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FlsGetValue");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwflsindex);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)dwflsindex;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(uint32_t dwflsindex, void* lpflsdata) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FlsSetValue");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwflsindex, lpflsdata);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)dwflsindex;
    (void)lpflsdata;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(void* hfile) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FlushFileBuffers");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(uint32_t dwflags, void* lpsource, uint32_t dwmessageid, uint32_t dwlanguageid, void* lpbuffer, uint32_t nsize, void* arguments) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*, uint32_t, uint32_t, void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FormatMessageW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwflags, lpsource, dwmessageid, dwlanguageid, lpbuffer, nsize, arguments);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)dwflags;
    (void)lpsource;
    (void)dwmessageid;
    (void)dwlanguageid;
    (void)lpbuffer;
    (void)nsize;
    (void)arguments;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(void* penv) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "FreeEnvironmentStringsW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(penv);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)penv;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(uint16_t groupnumber) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint16_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetActiveProcessorCount");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(groupnumber);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)groupnumber;
    errno = ENOSYS;
    return 0;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(void) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetCommandLineW");
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

int32_t stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(void* hconsolehandle, void* lpmode) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetConsoleMode");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hconsolehandle, lpmode);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hconsolehandle;
    (void)lpmode;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetConsoleOutputCP");
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(uint32_t nbufferlength, void* lpbuffer) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetCurrentDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(nbufferlength, lpbuffer);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)nbufferlength;
    (void)lpbuffer;
    errno = ENOSYS;
    return 0;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(void) {
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(void) {
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

void* stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(void) {
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetCurrentThreadId");
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

void* stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(void) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetEnvironmentStringsW");
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(void* lpname, void* lpbuffer, uint32_t nsize) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetEnvironmentVariableW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpname, lpbuffer, nsize);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpname;
    (void)lpbuffer;
    (void)nsize;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(void* hprocess, void* lpexitcode) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetExitCodeProcess");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, lpexitcode);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)lpexitcode;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(void* lpfilename) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFileAttributesW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpfilename;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(void* hfile, void* lpfileinformation) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFileInformationByHandle");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpfileinformation);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpfileinformation;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(void* hfile, int32_t fileinformationclass, void* lpfileinformation, uint32_t dwbuffersize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFileInformationByHandleEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, fileinformationclass, lpfileinformation, dwbuffersize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)fileinformationclass;
    (void)lpfileinformation;
    (void)dwbuffersize;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(void* hfile, void* lpfilesize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFileSizeEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpfilesize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpfilesize;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFileType(void* hfile) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFileType");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hfile;
    errno = ENOSYS;
    return 0;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(void* hfile, void* lpszfilepath, uint32_t cchfilepath, uint32_t dwflags) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFinalPathNameByHandleW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpszfilepath, cchfilepath, dwflags);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hfile;
    (void)lpszfilepath;
    (void)cchfilepath;
    (void)dwflags;
    errno = ENOSYS;
    return 0;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(void* lpfilename, uint32_t nbufferlength, void* lpbuffer, void* lpfilepart) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetFullPathNameW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename, nbufferlength, lpbuffer, lpfilepart);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpfilename;
    (void)nbufferlength;
    (void)lpbuffer;
    (void)lpfilepart;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(void* name, int32_t namelen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetHostNameW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(name, namelen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetLastError(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetLastError");
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetLogicalDrives");
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(void* hmodule, void* lpfilename, uint32_t nsize) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetModuleFileNameW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hmodule, lpfilename, nsize);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hmodule;
    (void)lpfilename;
    (void)nsize;
    errno = ENOSYS;
    return 0;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(void* lpmodulename) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetModuleHandleA");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpmodulename);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpmodulename;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(uint32_t dwflags, void* lpmodulename, void* phmodule) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetModuleHandleExW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwflags, lpmodulename, phmodule);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)dwflags;
    (void)lpmodulename;
    (void)phmodule;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(void* lpmodulename) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetModuleHandleW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpmodulename);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lpmodulename;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(void* hfile, void* lpoverlapped, void* lpnumberofbytestransferred, int32_t bwait) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetOverlappedResult");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpoverlapped, lpnumberofbytestransferred, bwait);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpoverlapped;
    (void)lpnumberofbytestransferred;
    (void)bwait;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysGetProcAddress(void* hmodule, void* lpprocname) {
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

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetProcessId(void* process) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetProcessId");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(process);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)process;
    errno = ENOSYS;
    return 0;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(uint32_t nstdhandle) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetStdHandle");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(nstdhandle);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)nstdhandle;
    errno = ENOSYS;
    return NULL;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(void* lpbuffer, uint32_t usize) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetSystemDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpbuffer, usize);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpbuffer;
    (void)usize;
    errno = ENOSYS;
    return 0;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(void* lpsysteminfo) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetSystemInfo");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(lpsysteminfo);
        return;
    }
    errno = ENOSYS;
#else
    (void)lpsysteminfo;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(void* lpsystemtimeasfiletime) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetSystemTimeAsFileTime");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(lpsystemtimeasfiletime);
        return;
    }
    errno = ENOSYS;
#else
    (void)lpsystemtimeasfiletime;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(void* lpsystemtimeasfiletime) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetSystemTimePreciseAsFileTime");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(lpsystemtimeasfiletime);
        return;
    }
    errno = ENOSYS;
#else
    (void)lpsystemtimeasfiletime;
    errno = ENOSYS;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(uint32_t nbufferlength, void* lpbuffer) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetTempPathW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(nbufferlength, lpbuffer);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)nbufferlength;
    (void)lpbuffer;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(void* htoken, void* lpprofiledir, void* lpcchsize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("userenv.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetUserProfileDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(htoken, lpprofiledir, lpcchsize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)htoken;
    (void)lpprofiledir;
    (void)lpcchsize;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(void* lpbuffer, uint32_t usize) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetWindowsDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpbuffer, usize);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpbuffer;
    (void)usize;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(void* lpinitonce, uint32_t dwflags, void* fpending, void* lpcontext) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "InitOnceBeginInitialize");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpinitonce, dwflags, fpending, lpcontext);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpinitonce;
    (void)dwflags;
    (void)fpending;
    (void)lpcontext;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(void* lpinitonce, uint32_t dwflags, void* lpcontext) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "InitOnceComplete");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpinitonce, dwflags, lpcontext);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpinitonce;
    (void)dwflags;
    (void)lpcontext;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(void* lpattributelist, uint32_t dwattributecount, uint32_t dwflags, void* lpsize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "InitializeProcThreadAttributeList");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpattributelist, dwattributecount, dwflags, lpsize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpattributelist;
    (void)dwattributecount;
    (void)dwflags;
    (void)lpsize;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(void) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "IsThreadAFiber");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return -1;
#else
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(void* lplibfilename, void* hfile, uint32_t dwflags) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "LoadLibraryExA");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lplibfilename, hfile, dwflags);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)lplibfilename;
    (void)hfile;
    (void)dwflags;
    errno = ENOSYS;
    return NULL;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysLocalFree(void* hmem) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "LocalFree");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hmem);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)hmem;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysLockFileEx(void* hfile, uint32_t dwflags, uint32_t dwreserved, uint32_t nnumberofbytestolocklow, uint32_t nnumberofbytestolockhigh, void* lpoverlapped) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "LockFileEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, dwflags, dwreserved, nnumberofbytestolocklow, nnumberofbytestolockhigh, lpoverlapped);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)dwflags;
    (void)dwreserved;
    (void)nnumberofbytestolocklow;
    (void)nnumberofbytestolockhigh;
    (void)lpoverlapped;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(void* lpexistingfilename, void* lpnewfilename, uint32_t dwflags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "MoveFileExW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpexistingfilename, lpnewfilename, dwflags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpexistingfilename;
    (void)lpnewfilename;
    (void)dwflags;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(uint32_t codepage, uint32_t dwflags, void* lpmultibytestr, int32_t cbmultibyte, void* lpwidecharstr, int32_t cchwidechar) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, uint32_t, void*, int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "MultiByteToWideChar");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(codepage, dwflags, lpmultibytestr, cbmultibyte, lpwidecharstr, cchwidechar);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)codepage;
    (void)dwflags;
    (void)lpmultibytestr;
    (void)cbmultibyte;
    (void)lpwidecharstr;
    (void)cchwidechar;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, void* allocationsize, uint32_t fileattributes, uint32_t shareaccess, uint32_t createdisposition, uint32_t createoptions, void* eabuffer, uint32_t ealength) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*, void*, uint32_t, uint32_t, uint32_t, uint32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtCreateFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, desiredaccess, objectattributes, iostatusblock, allocationsize, fileattributes, shareaccess, createdisposition, createoptions, eabuffer, ealength);
    }
    errno = ENOSYS;
    return -1;
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
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, uint32_t shareaccess, uint32_t openoptions) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtOpenFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, desiredaccess, objectattributes, iostatusblock, shareaccess, openoptions);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)filehandle;
    (void)desiredaccess;
    (void)objectattributes;
    (void)iostatusblock;
    (void)shareaccess;
    (void)openoptions;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(void* filehandle, void* iostatusblock, void* fsinformation, uint32_t length, int32_t fsinformationclass) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtQueryVolumeInformationFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, iostatusblock, fsinformation, length, fsinformationclass);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)filehandle;
    (void)iostatusblock;
    (void)fsinformation;
    (void)length;
    (void)fsinformationclass;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysNtReadFile(void* filehandle, void* event, void* apcroutine, void* apccontext, void* iostatusblock, void* buffer, uint32_t length, void* byteoffset, void* key) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtReadFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, event, apcroutine, apccontext, iostatusblock, buffer, length, byteoffset, key);
    }
    errno = ENOSYS;
    return -1;
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
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(void* filehandle, void* iostatusblock, void* fileinformation, uint32_t length, int32_t fileinformationclass) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtSetInformationFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, iostatusblock, fileinformation, length, fileinformationclass);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)filehandle;
    (void)iostatusblock;
    (void)fileinformation;
    (void)length;
    (void)fileinformationclass;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(void* filehandle, void* event, void* apcroutine, void* apccontext, void* iostatusblock, void* buffer, uint32_t length, void* byteoffset, void* key) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*, void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtWriteFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, event, apcroutine, apccontext, iostatusblock, buffer, length, byteoffset, key);
    }
    errno = ENOSYS;
    return -1;
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
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(void* processhandle, uint32_t desiredaccess, void* tokenhandle) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("advapi32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "OpenProcessToken");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(processhandle, desiredaccess, tokenhandle);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)processhandle;
    (void)desiredaccess;
    (void)tokenhandle;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(void* lpdevicename, void* lptargetpath, uint32_t ucchmax) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "QueryDosDeviceW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpdevicename, lptargetpath, ucchmax);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)lpdevicename;
    (void)lptargetpath;
    (void)ucchmax;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(void* lpperformancecount) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "QueryPerformanceCounter");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpperformancecount);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpperformancecount;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(void* lpfrequency) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "QueryPerformanceFrequency");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfrequency);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpfrequency;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(void* hconsoleinput, void* lpbuffer, uint32_t nnumberofcharstoread, void* lpnumberofcharsread, void* pinputcontrol) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ReadConsoleW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hconsoleinput, lpbuffer, nnumberofcharstoread, lpnumberofcharsread, pinputcontrol);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hconsoleinput;
    (void)lpbuffer;
    (void)nnumberofcharstoread;
    (void)lpnumberofcharsread;
    (void)pinputcontrol;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysReadFile(void* hfile, void* lpbuffer, uint32_t nnumberofbytestoread, void* lpnumberofbytesread, void* lpoverlapped) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ReadFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpbuffer, nnumberofbytestoread, lpnumberofbytesread, lpoverlapped);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpbuffer;
    (void)nnumberofbytestoread;
    (void)lpnumberofbytesread;
    (void)lpoverlapped;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysReadFileEx(void* hfile, void* lpbuffer, uint32_t nnumberofbytestoread, void* lpoverlapped, void* lpcompletionroutine) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ReadFileEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpbuffer, nnumberofbytestoread, lpoverlapped, lpcompletionroutine);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpbuffer;
    (void)nnumberofbytestoread;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(void* srwlock) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ReleaseSRWLockExclusive");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(srwlock);
        return;
    }
    errno = ENOSYS;
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(void* srwlock) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ReleaseSRWLockShared");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(srwlock);
        return;
    }
    errno = ENOSYS;
#else
    (void)srwlock;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(void* lppathname) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "RemoveDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lppathname);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lppathname;
    errno = ENOSYS;
    return -1;
#endif
}

uint8_t stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(void* randombuffer, uint32_t randombufferlength) {
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("advapi32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SystemFunction036");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(randombuffer, randombufferlength);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)randombuffer;
    (void)randombufferlength;
    errno = ENOSYS;
    return 0;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(int32_t status) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "RtlNtStatusToDosError");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(status);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)status;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(void* lppathname) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetCurrentDirectoryW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lppathname);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lppathname;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(void* lpname, void* lpvalue) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetEnvironmentVariableW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpname, lpvalue);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpname;
    (void)lpvalue;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(void* lpfilename, uint32_t dwfileattributes) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetFileAttributesW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpfilename, dwfileattributes);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpfilename;
    (void)dwfileattributes;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(void* hfile, int32_t fileinformationclass, void* lpfileinformation, uint32_t dwbuffersize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int32_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetFileInformationByHandle");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, fileinformationclass, lpfileinformation, dwbuffersize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)fileinformationclass;
    (void)lpfileinformation;
    (void)dwbuffersize;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(void* hfile, int64_t lidistancetomove, void* lpnewfilepointer, uint32_t dwmovemethod) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, int64_t, void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetFilePointerEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lidistancetomove, lpnewfilepointer, dwmovemethod);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lidistancetomove;
    (void)lpnewfilepointer;
    (void)dwmovemethod;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetFileTime(void* hfile, void* lpcreationtime, void* lplastaccesstime, void* lplastwritetime) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetFileTime");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpcreationtime, lplastaccesstime, lplastwritetime);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpcreationtime;
    (void)lplastaccesstime;
    (void)lplastwritetime;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(void* hobject, uint32_t dwmask, uint32_t dwflags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetHandleInformation");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hobject, dwmask, dwflags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hobject;
    (void)dwmask;
    (void)dwflags;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysSetLastError(uint32_t dwerrcode) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetLastError");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(dwerrcode);
        return;
    }
    errno = ENOSYS;
#else
    (void)dwerrcode;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(uint32_t nstdhandle, void* hhandle) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetStdHandle");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(nstdhandle, hhandle);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)nstdhandle;
    (void)hhandle;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(void* stacksizeinbytes) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetThreadStackGuarantee");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(stacksizeinbytes);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)stacksizeinbytes;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(void* htimer, void* lpduetime, int32_t lperiod, void* pfncompletionroutine, void* lpargtocompletionroutine, int32_t fresume) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, int32_t, void*, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SetWaitableTimer");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(htimer, lpduetime, lperiod, pfncompletionroutine, lpargtocompletionroutine, fresume);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)htimer;
    (void)lpduetime;
    (void)lperiod;
    (void)pfncompletionroutine;
    (void)lpargtocompletionroutine;
    (void)fresume;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysSleep(uint32_t dwmilliseconds) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "Sleep");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(dwmilliseconds);
        return;
    }
    errno = ENOSYS;
#else
    (void)dwmilliseconds;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(void* conditionvariable, void* srwlock, uint32_t dwmilliseconds, uint32_t flags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SleepConditionVariableSRW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(conditionvariable, srwlock, dwmilliseconds, flags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)conditionvariable;
    (void)srwlock;
    (void)dwmilliseconds;
    (void)flags;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysSleepEx(uint32_t dwmilliseconds, int32_t balertable) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SleepEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwmilliseconds, balertable);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)dwmilliseconds;
    (void)balertable;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSwitchToThread(void) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "SwitchToThread");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return -1;
#else
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(void* hprocess, uint32_t uexitcode) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TerminateProcess");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hprocess, uexitcode);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hprocess;
    (void)uexitcode;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysTlsAlloc(void) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TlsAlloc");
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

int32_t stdSysPalWindowsCWindowssysWindowsSysTlsFree(uint32_t dwtlsindex) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TlsFree");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwtlsindex);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)dwtlsindex;
    errno = ENOSYS;
    return -1;
#endif
}

void* stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(uint32_t dwtlsindex) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TlsGetValue");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwtlsindex);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)dwtlsindex;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(uint32_t dwtlsindex, void* lptlsvalue) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TlsSetValue");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(dwtlsindex, lptlsvalue);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)dwtlsindex;
    (void)lptlsvalue;
    errno = ENOSYS;
    return -1;
#endif
}

uint8_t stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(void* srwlock) {
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TryAcquireSRWLockExclusive");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(srwlock);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)srwlock;
    errno = ENOSYS;
    return 0;
#endif
}

uint8_t stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(void* srwlock) {
#ifdef _WIN32
    typedef uint8_t (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "TryAcquireSRWLockShared");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(srwlock);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)srwlock;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysUnlockFile(void* hfile, uint32_t dwfileoffsetlow, uint32_t dwfileoffsethigh, uint32_t nnumberofbytestounlocklow, uint32_t nnumberofbytestounlockhigh) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint32_t, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "UnlockFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, dwfileoffsetlow, dwfileoffsethigh, nnumberofbytestounlocklow, nnumberofbytestounlockhigh);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)dwfileoffsetlow;
    (void)dwfileoffsethigh;
    (void)nnumberofbytestounlocklow;
    (void)nnumberofbytestounlockhigh;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(void* lpattributelist, uint32_t dwflags, uint64_t attribute, void* lpvalue, uint64_t cbsize, void* lppreviousvalue, void* lpreturnsize) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, uint64_t, void*, uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "UpdateProcThreadAttribute");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(lpattributelist, dwflags, attribute, lpvalue, cbsize, lppreviousvalue, lpreturnsize);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)lpattributelist;
    (void)dwflags;
    (void)attribute;
    (void)lpvalue;
    (void)cbsize;
    (void)lppreviousvalue;
    (void)lpreturnsize;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWSACleanup(void) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSACleanup");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return -1;
#else
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(uint64_t s, uint32_t dwprocessid, void* lpprotocolinfo) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSADuplicateSocketW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, dwprocessid, lpprotocolinfo);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)dwprocessid;
    (void)lpprotocolinfo;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(void) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSAGetLastError");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return -1;
#else
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWSARecv(uint64_t s, void* lpbuffers, uint32_t dwbuffercount, void* lpnumberofbytesrecvd, void* lpflags, void* lpoverlapped, void* lpcompletionroutine) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, uint32_t, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSARecv");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, lpbuffers, dwbuffercount, lpnumberofbytesrecvd, lpflags, lpoverlapped, lpcompletionroutine);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)lpbuffers;
    (void)dwbuffercount;
    (void)lpnumberofbytesrecvd;
    (void)lpflags;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWSASend(uint64_t s, void* lpbuffers, uint32_t dwbuffercount, void* lpnumberofbytessent, uint32_t dwflags, void* lpoverlapped, void* lpcompletionroutine) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, uint32_t, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSASend");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, lpbuffers, dwbuffercount, lpnumberofbytessent, dwflags, lpoverlapped, lpcompletionroutine);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)lpbuffers;
    (void)dwbuffercount;
    (void)lpnumberofbytessent;
    (void)dwflags;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    return -1;
#endif
}

uint64_t stdSysPalWindowsCWindowssysWindowsSysWSASocketW(int32_t af, int32_t type, int32_t protocol, void* lpprotocolinfo, uint32_t g, uint32_t dwflags) {
#ifdef _WIN32
    typedef uint64_t (__stdcall *km_fn_t)(int32_t, int32_t, int32_t, void*, uint32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSASocketW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(af, type, protocol, lpprotocolinfo, g, dwflags);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)af;
    (void)type;
    (void)protocol;
    (void)lpprotocolinfo;
    (void)g;
    (void)dwflags;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWSAStartup(uint16_t wversionrequested, void* lpwsadata) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint16_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WSAStartup");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(wversionrequested, lpwsadata);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)wversionrequested;
    (void)lpwsadata;
    errno = ENOSYS;
    return -1;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(uint32_t ncount, void* lphandles, int32_t bwaitall, uint32_t dwmilliseconds) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(uint32_t, void*, int32_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WaitForMultipleObjects");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(ncount, lphandles, bwaitall, dwmilliseconds);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)ncount;
    (void)lphandles;
    (void)bwaitall;
    (void)dwmilliseconds;
    errno = ENOSYS;
    return 0;
#endif
}

uint32_t stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(void* hhandle, uint32_t dwmilliseconds) {
#ifdef _WIN32
    typedef uint32_t (__stdcall *km_fn_t)(void*, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WaitForSingleObject");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hhandle, dwmilliseconds);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)hhandle;
    (void)dwmilliseconds;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(void* address, void* compareaddress, uint64_t addresssize, uint32_t dwmilliseconds) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint64_t, uint32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("api-ms-win-core-synch-l1-2-0.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WaitOnAddress");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(address, compareaddress, addresssize, dwmilliseconds);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)address;
    (void)compareaddress;
    (void)addresssize;
    (void)dwmilliseconds;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(void* conditionvariable) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WakeAllConditionVariable");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(conditionvariable);
        return;
    }
    errno = ENOSYS;
#else
    (void)conditionvariable;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(void* address) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("api-ms-win-core-synch-l1-2-0.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WakeByAddressAll");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(address);
        return;
    }
    errno = ENOSYS;
#else
    (void)address;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(void* address) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("api-ms-win-core-synch-l1-2-0.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WakeByAddressSingle");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(address);
        return;
    }
    errno = ENOSYS;
#else
    (void)address;
    errno = ENOSYS;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(void* conditionvariable) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WakeConditionVariable");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(conditionvariable);
        return;
    }
    errno = ENOSYS;
#else
    (void)conditionvariable;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(uint32_t codepage, uint32_t dwflags, void* lpwidecharstr, int32_t cchwidechar, void* lpmultibytestr, int32_t cbmultibyte, void* lpdefaultchar, void* lpuseddefaultchar) {
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

int32_t stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(void* hconsoleoutput, void* lpbuffer, uint32_t nnumberofcharstowrite, void* lpnumberofcharswritten, void* lpreserved) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WriteConsoleW");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hconsoleoutput, lpbuffer, nnumberofcharstowrite, lpnumberofcharswritten, lpreserved);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hconsoleoutput;
    (void)lpbuffer;
    (void)nnumberofcharstowrite;
    (void)lpnumberofcharswritten;
    (void)lpreserved;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(void* hfile, void* lpbuffer, uint32_t nnumberofbytestowrite, void* lpoverlapped, void* lpcompletionroutine) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, uint32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "WriteFileEx");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hfile, lpbuffer, nnumberofbytestowrite, lpoverlapped, lpcompletionroutine);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hfile;
    (void)lpbuffer;
    (void)nnumberofbytestowrite;
    (void)lpoverlapped;
    (void)lpcompletionroutine;
    errno = ENOSYS;
    return -1;
#endif
}

uint64_t stdSysPalWindowsCWindowssysWindowsSysAccept(uint64_t s, void* addr, void* addrlen) {
#ifdef _WIN32
    typedef uint64_t (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "accept");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, addr, addrlen);
    }
    errno = ENOSYS;
    return 0;
#else
    (void)s;
    (void)addr;
    (void)addrlen;
    errno = ENOSYS;
    return 0;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysBind(uint64_t s, void* name, int32_t namelen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "bind");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, name, namelen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysClosesocket(uint64_t s) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "closesocket");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysConnect(uint64_t s, void* name, int32_t namelen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "connect");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, name, namelen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    return -1;
#endif
}

void stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(void* paddrinfo) {
#ifdef _WIN32
    typedef void (__stdcall *km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "freeaddrinfo");
        }
    }
    if (km_fn_ptr != NULL) {
        km_fn_ptr(paddrinfo);
        return;
    }
    errno = ENOSYS;
#else
    (void)paddrinfo;
    errno = ENOSYS;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(void* pnodename, void* pservicename, void* phints, void* ppresult) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "getaddrinfo");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(pnodename, pservicename, phints, ppresult);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)pnodename;
    (void)pservicename;
    (void)phints;
    (void)ppresult;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetpeername(uint64_t s, void* name, void* namelen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "getpeername");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, name, namelen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetsockname(uint64_t s, void* name, void* namelen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "getsockname");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, name, namelen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)name;
    (void)namelen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysGetsockopt(uint64_t s, int32_t level, int32_t optname, void* optval, void* optlen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "getsockopt");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, level, optname, optval, optlen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)level;
    (void)optname;
    (void)optval;
    (void)optlen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(uint64_t s, int32_t cmd, void* argp) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "ioctlsocket");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, cmd, argp);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)cmd;
    (void)argp;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysListen(uint64_t s, int32_t backlog) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "listen");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, backlog);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)backlog;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysLstrlenW(void* lpstring) {
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

int32_t stdSysPalWindowsCWindowssysWindowsSysRecv(uint64_t s, void* buf, int32_t len, int32_t flags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "recv");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, buf, len, flags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysRecvfrom(uint64_t s, void* buf, int32_t len, int32_t flags, void* from, void* fromlen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "recvfrom");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, buf, len, flags, from, fromlen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    (void)from;
    (void)fromlen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSelect(int32_t nfds, void* readfds, void* writefds, void* exceptfds, void* timeout) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(int32_t, void*, void*, void*, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "select");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(nfds, readfds, writefds, exceptfds, timeout);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)nfds;
    (void)readfds;
    (void)writefds;
    (void)exceptfds;
    (void)timeout;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSend(uint64_t s, void* buf, int32_t len, int32_t flags) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "send");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, buf, len, flags);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSendto(uint64_t s, void* buf, int32_t len, int32_t flags, void* to, int32_t tolen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, void*, int32_t, int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "sendto");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, buf, len, flags, to, tolen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)buf;
    (void)len;
    (void)flags;
    (void)to;
    (void)tolen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysSetsockopt(uint64_t s, int32_t level, int32_t optname, void* optval, int32_t optlen) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t, int32_t, void*, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "setsockopt");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, level, optname, optval, optlen);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)level;
    (void)optname;
    (void)optval;
    (void)optlen;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCWindowssysWindowsSysShutdown(uint64_t s, int32_t how) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(uint64_t, int32_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ws2_32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "shutdown");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s, how);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)s;
    (void)how;
    errno = ENOSYS;
    return -1;
#endif
}

