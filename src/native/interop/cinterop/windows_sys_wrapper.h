#ifndef RUSTLANG_WINDOWS_SYS_WINDOWS_SYS_WRAPPER_H
#define RUSTLANG_WINDOWS_SYS_WINDOWS_SYS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangWindowsSysEnumerateLoadedModulesW64(void* hprocess, void* enumloadedmodulescallback, void* usercontext);
int32_t rustlangWindowsSysStackWalk64(uint32_t machinetype, void* hprocess, void* hthread, void* stackframe, void* contextrecord, void* readmemoryroutine, void* functiontableaccessroutine, void* getmodulebaseroutine, void* translateaddress);
int32_t rustlangWindowsSysStackWalkEx(uint32_t machinetype, void* hprocess, void* hthread, void* stackframe, void* contextrecord, void* readmemoryroutine, void* functiontableaccessroutine, void* getmodulebaseroutine, void* translateaddress, uint32_t flags);
uint32_t rustlangWindowsSysSymAddrIncludeInlineTrace(void* hprocess, uint64_t address);
int32_t rustlangWindowsSysSymFromAddrW(void* hprocess, uint64_t address, void* displacement, void* symbol);
int32_t rustlangWindowsSysSymFromInlineContextW(void* hprocess, uint64_t address, uint32_t inlinecontext, void* displacement, void* symbol);
void* rustlangWindowsSysSymFunctionTableAccess64(void* hprocess, uint64_t addrbase);
int32_t rustlangWindowsSysSymGetLineFromAddrW64(void* hprocess, uint64_t dwaddr, void* pdwdisplacement, void* line);
int32_t rustlangWindowsSysSymGetLineFromInlineContextW(void* hprocess, uint64_t dwaddr, uint32_t inlinecontext, uint64_t qwmodulebaseaddress, void* pdwdisplacement, void* line);
uint64_t rustlangWindowsSysSymGetModuleBase64(void* hprocess, uint64_t qwaddr);
uint32_t rustlangWindowsSysSymGetOptions(void);
int32_t rustlangWindowsSysSymGetSearchPathW(void* hprocess, void* searchpatha, uint32_t searchpathlength);
int32_t rustlangWindowsSysSymInitializeW(void* hprocess, void* usersearchpath, int32_t finvadeprocess);
int32_t rustlangWindowsSysSymQueryInlineTrace(void* hprocess, uint64_t startaddress, uint32_t startcontext, uint64_t startretaddress, uint64_t curaddress, void* curcontext, void* curframeindex);
uint32_t rustlangWindowsSysSymSetOptions(uint32_t symoptions);
int32_t rustlangWindowsSysSymSetSearchPathW(void* hprocess, void* searchpatha);
int32_t rustlangWindowsSysCloseHandle(void* hobject);
void* rustlangWindowsSysCreateFileMappingA(void* hfile, void* lpfilemappingattributes, uint32_t flprotect, uint32_t dwmaximumsizehigh, uint32_t dwmaximumsizelow, void* lpname);
void* rustlangWindowsSysCreateMutexA(void* lpmutexattributes, int32_t binitialowner, void* lpname);
void* rustlangWindowsSysCreateToolhelp32Snapshot(uint32_t dwflags, uint32_t th32processid);
void* rustlangWindowsSysGetCurrentProcess(void);
uint32_t rustlangWindowsSysGetCurrentProcessId(void);
void* rustlangWindowsSysGetCurrentThread(void);
void* rustlangWindowsSysGetProcAddress(void* hmodule, void* lpprocname);
void* rustlangWindowsSysLoadLibraryA(void* lplibfilename);
int32_t rustlangWindowsSysModule32FirstW(void* hsnapshot, void* lpme);
int32_t rustlangWindowsSysModule32NextW(void* hsnapshot, void* lpme);
int32_t rustlangWindowsSysReleaseMutex(void* hmutex);
void rustlangWindowsSysRtlCaptureContext(void* contextrecord);
void* rustlangWindowsSysRtlLookupFunctionEntry(uint64_t controlpc, void* imagebase, void* historytable);
void* rustlangWindowsSysRtlVirtualUnwind(uint32_t handlertype, uint64_t imagebase, uint64_t controlpc, void* functionentry, void* contextrecord, void* handlerdata, void* establisherframe, void* contextpointers);
uint32_t rustlangWindowsSysWaitForSingleObjectEx(void* hhandle, uint32_t dwmilliseconds, int32_t balertable);
int32_t rustlangWindowsSysWideCharToMultiByte(uint32_t codepage, uint32_t dwflags, void* lpwidecharstr, int32_t cchwidechar, void* lpmultibytestr, int32_t cbmultibyte, void* lpdefaultchar, void* lpuseddefaultchar);
int32_t rustlangWindowsSysLstrlenW(void* lpstring);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_WINDOWS_SYS_WINDOWS_SYS_WRAPPER_H */
