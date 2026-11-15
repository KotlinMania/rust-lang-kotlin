#ifndef BACKTRACE_WINDOWS_SYS_WINDOWS_SYS_WRAPPER_H
#define BACKTRACE_WINDOWS_SYS_WINDOWS_SYS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t backtraceWindowsSysEnumerateLoadedModulesW64(void* hprocess, void* enumloadedmodulescallback, void* usercontext);
int32_t backtraceWindowsSysStackWalk64(uint32_t machinetype, void* hprocess, void* hthread, void* stackframe, void* contextrecord, void* readmemoryroutine, void* functiontableaccessroutine, void* getmodulebaseroutine, void* translateaddress);
int32_t backtraceWindowsSysStackWalkEx(uint32_t machinetype, void* hprocess, void* hthread, void* stackframe, void* contextrecord, void* readmemoryroutine, void* functiontableaccessroutine, void* getmodulebaseroutine, void* translateaddress, uint32_t flags);
uint32_t backtraceWindowsSysSymAddrIncludeInlineTrace(void* hprocess, uint64_t address);
int32_t backtraceWindowsSysSymFromAddrW(void* hprocess, uint64_t address, void* displacement, void* symbol);
int32_t backtraceWindowsSysSymFromInlineContextW(void* hprocess, uint64_t address, uint32_t inlinecontext, void* displacement, void* symbol);
void* backtraceWindowsSysSymFunctionTableAccess64(void* hprocess, uint64_t addrbase);
int32_t backtraceWindowsSysSymGetLineFromAddrW64(void* hprocess, uint64_t dwaddr, void* pdwdisplacement, void* line);
int32_t backtraceWindowsSysSymGetLineFromInlineContextW(void* hprocess, uint64_t dwaddr, uint32_t inlinecontext, uint64_t qwmodulebaseaddress, void* pdwdisplacement, void* line);
uint64_t backtraceWindowsSysSymGetModuleBase64(void* hprocess, uint64_t qwaddr);
uint32_t backtraceWindowsSysSymGetOptions(void);
int32_t backtraceWindowsSysSymGetSearchPathW(void* hprocess, void* searchpatha, uint32_t searchpathlength);
int32_t backtraceWindowsSysSymInitializeW(void* hprocess, void* usersearchpath, int32_t finvadeprocess);
int32_t backtraceWindowsSysSymQueryInlineTrace(void* hprocess, uint64_t startaddress, uint32_t startcontext, uint64_t startretaddress, uint64_t curaddress, void* curcontext, void* curframeindex);
uint32_t backtraceWindowsSysSymSetOptions(uint32_t symoptions);
int32_t backtraceWindowsSysSymSetSearchPathW(void* hprocess, void* searchpatha);
int32_t backtraceWindowsSysCloseHandle(void* hobject);
void* backtraceWindowsSysCreateFileMappingA(void* hfile, void* lpfilemappingattributes, uint32_t flprotect, uint32_t dwmaximumsizehigh, uint32_t dwmaximumsizelow, void* lpname);
void* backtraceWindowsSysCreateMutexA(void* lpmutexattributes, int32_t binitialowner, void* lpname);
void* backtraceWindowsSysCreateToolhelp32Snapshot(uint32_t dwflags, uint32_t th32processid);
void* backtraceWindowsSysGetCurrentProcess(void);
uint32_t backtraceWindowsSysGetCurrentProcessId(void);
void* backtraceWindowsSysGetCurrentThread(void);
void* backtraceWindowsSysGetProcAddress(void* hmodule, void* lpprocname);
void* backtraceWindowsSysLoadLibraryA(void* lplibfilename);
int32_t backtraceWindowsSysModule32FirstW(void* hsnapshot, void* lpme);
int32_t backtraceWindowsSysModule32NextW(void* hsnapshot, void* lpme);
int32_t backtraceWindowsSysReleaseMutex(void* hmutex);
void backtraceWindowsSysRtlCaptureContext(void* contextrecord);
void* backtraceWindowsSysRtlLookupFunctionEntry(uint64_t controlpc, void* imagebase, void* historytable);
void* backtraceWindowsSysRtlVirtualUnwind(uint32_t handlertype, uint64_t imagebase, uint64_t controlpc, void* functionentry, void* contextrecord, void* handlerdata, void* establisherframe, void* contextpointers);
uint32_t backtraceWindowsSysWaitForSingleObjectEx(void* hhandle, uint32_t dwmilliseconds, int32_t balertable);
int32_t backtraceWindowsSysWideCharToMultiByte(uint32_t codepage, uint32_t dwflags, void* lpwidecharstr, int32_t cchwidechar, void* lpmultibytestr, int32_t cbmultibyte, void* lpdefaultchar, void* lpuseddefaultchar);
int32_t backtraceWindowsSysLstrlenW(void* lpstring);

#ifdef __cplusplus
}
#endif

#endif /* BACKTRACE_WINDOWS_SYS_WINDOWS_SYS_WRAPPER_H */
