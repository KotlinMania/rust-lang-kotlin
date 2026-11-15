#ifndef RUSTLANG_STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_WRAPPER_H
#define RUSTLANG_STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(void* srwlock);
void rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(void* srwlock);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(uint32_t first, void* handler);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(void* hfile);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(void* hobject);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(void* lpstring1, int32_t cchcount1, void* lpstring2, int32_t cchcount2, int32_t bignorecase);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(void* lpexistingfilename, void* lpnewfilename, void* lpprogressroutine, void* lpdata, void* pbcancel, uint32_t dwcopyflags);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(void* lppathname, void* lpsecurityattributes);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(void* lpeventattributes, int32_t bmanualreset, int32_t binitialstate, void* lpname);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(void* lpfilename, uint32_t dwdesiredaccess, uint32_t dwsharemode, void* lpsecurityattributes, uint32_t dwcreationdisposition, uint32_t dwflagsandattributes, void* htemplatefile);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(void* lpfilename, void* lpexistingfilename, void* lpsecurityattributes);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(void* lpname, uint32_t dwopenmode, uint32_t dwpipemode, uint32_t nmaxinstances, uint32_t noutbuffersize, uint32_t ninbuffersize, uint32_t ndefaulttimeout, void* lpsecurityattributes);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(void* hreadpipe, void* hwritepipe, void* lppipeattributes, uint32_t nsize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(void* lpapplicationname, void* lpcommandline, void* lpprocessattributes, void* lpthreadattributes, int32_t binherithandles, uint32_t dwcreationflags, void* lpenvironment, void* lpcurrentdirectory, void* lpstartupinfo, void* lpprocessinformation);
uint8_t rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(void* lpsymlinkfilename, void* lptargetfilename, uint32_t dwflags);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(void* lpthreadattributes, uint64_t dwstacksize, void* lpstartaddress, void* lpparameter, uint32_t dwcreationflags, void* lpthreadid);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(void* lptimerattributes, void* lptimername, uint32_t dwflags, uint32_t dwdesiredaccess);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(void* lpfilename);
void rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(void* lpattributelist);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(void* hdevice, uint32_t dwiocontrolcode, void* lpinbuffer, uint32_t ninbuffersize, void* lpoutbuffer, uint32_t noutbuffersize, void* lpbytesreturned, void* lpoverlapped);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(void* hsourceprocesshandle, void* hsourcehandle, void* htargetprocesshandle, void* lptargethandle, uint32_t dwdesiredaccess, int32_t binherithandle, uint32_t dwoptions);
void rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uint32_t uexitcode);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(void* hfindfile);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(void* lpfilename, int32_t finfolevelid, void* lpfindfiledata, int32_t fsearchop, void* lpsearchfilter, uint32_t dwadditionalflags);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(void* hfindfile, void* lpfindfiledata);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(void* lpcallback);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(uint32_t dwflsindex);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(uint32_t dwflsindex);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(uint32_t dwflsindex, void* lpflsdata);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(void* hfile);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(uint32_t dwflags, void* lpsource, uint32_t dwmessageid, uint32_t dwlanguageid, void* lpbuffer, uint32_t nsize, void* arguments);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(void* penv);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(uint16_t groupnumber);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(void);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(void* hconsolehandle, void* lpmode);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(void);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(uint32_t nbufferlength, void* lpbuffer);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(void);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(void);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(void);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(void);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(void);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(void* lpname, void* lpbuffer, uint32_t nsize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(void* hprocess, void* lpexitcode);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(void* lpfilename);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(void* hfile, void* lpfileinformation);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(void* hfile, int32_t fileinformationclass, void* lpfileinformation, uint32_t dwbuffersize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(void* hfile, void* lpfilesize);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(void* hfile);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(void* hfile, void* lpszfilepath, uint32_t cchfilepath, uint32_t dwflags);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(void* lpfilename, uint32_t nbufferlength, void* lpbuffer, void* lpfilepart);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(void* name, int32_t namelen);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError(void);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(void);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(void* hmodule, void* lpfilename, uint32_t nsize);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(void* lpmodulename);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(uint32_t dwflags, void* lpmodulename, void* phmodule);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(void* lpmodulename);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(void* hfile, void* lpoverlapped, void* lpnumberofbytestransferred, int32_t bwait);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress(void* hmodule, void* lpprocname);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(void* process);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(uint32_t nstdhandle);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(void* lpbuffer, uint32_t usize);
void rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(void* lpsysteminfo);
void rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(void* lpsystemtimeasfiletime);
void rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(void* lpsystemtimeasfiletime);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(uint32_t nbufferlength, void* lpbuffer);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(void* htoken, void* lpprofiledir, void* lpcchsize);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(void* lpbuffer, uint32_t usize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(void* lpinitonce, uint32_t dwflags, void* fpending, void* lpcontext);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(void* lpinitonce, uint32_t dwflags, void* lpcontext);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(void* lpattributelist, uint32_t dwattributecount, uint32_t dwflags, void* lpsize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(void);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(void* lplibfilename, void* hfile, uint32_t dwflags);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(void* hmem);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(void* hfile, uint32_t dwflags, uint32_t dwreserved, uint32_t nnumberofbytestolocklow, uint32_t nnumberofbytestolockhigh, void* lpoverlapped);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(void* lpexistingfilename, void* lpnewfilename, uint32_t dwflags);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(uint32_t codepage, uint32_t dwflags, void* lpmultibytestr, int32_t cbmultibyte, void* lpwidecharstr, int32_t cchwidechar);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, void* allocationsize, uint32_t fileattributes, uint32_t shareaccess, uint32_t createdisposition, uint32_t createoptions, void* eabuffer, uint32_t ealength);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, uint32_t shareaccess, uint32_t openoptions);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(void* filehandle, void* iostatusblock, void* fsinformation, uint32_t length, int32_t fsinformationclass);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(void* filehandle, void* event, void* apcroutine, void* apccontext, void* iostatusblock, void* buffer, uint32_t length, void* byteoffset, void* key);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(void* filehandle, void* iostatusblock, void* fileinformation, uint32_t length, int32_t fileinformationclass);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(void* filehandle, void* event, void* apcroutine, void* apccontext, void* iostatusblock, void* buffer, uint32_t length, void* byteoffset, void* key);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(void* processhandle, uint32_t desiredaccess, void* tokenhandle);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(void* lpdevicename, void* lptargetpath, uint32_t ucchmax);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(void* lpperformancecount);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(void* lpfrequency);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(void* hconsoleinput, void* lpbuffer, uint32_t nnumberofcharstoread, void* lpnumberofcharsread, void* pinputcontrol);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(void* hfile, void* lpbuffer, uint32_t nnumberofbytestoread, void* lpnumberofbytesread, void* lpoverlapped);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(void* hfile, void* lpbuffer, uint32_t nnumberofbytestoread, void* lpoverlapped, void* lpcompletionroutine);
void rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(void* srwlock);
void rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(void* srwlock);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(void* lppathname);
uint8_t rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(void* randombuffer, uint32_t randombufferlength);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(int32_t status);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(void* lppathname);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(void* lpname, void* lpvalue);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(void* lpfilename, uint32_t dwfileattributes);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(void* hfile, int32_t fileinformationclass, void* lpfileinformation, uint32_t dwbuffersize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(void* hfile, int64_t lidistancetomove, void* lpnewfilepointer, uint32_t dwmovemethod);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(void* hfile, void* lpcreationtime, void* lplastaccesstime, void* lplastwritetime);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(void* hobject, uint32_t dwmask, uint32_t dwflags);
void rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(uint32_t dwerrcode);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(uint32_t nstdhandle, void* hhandle);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(void* stacksizeinbytes);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(void* htimer, void* lpduetime, int32_t lperiod, void* pfncompletionroutine, void* lpargtocompletionroutine, int32_t fresume);
void rustlangStdSysPalWindowsCWindowssysWindowsSysSleep(uint32_t dwmilliseconds);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(void* conditionvariable, void* srwlock, uint32_t dwmilliseconds, uint32_t flags);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(uint32_t dwmilliseconds, int32_t balertable);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread(void);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(void* hprocess, uint32_t uexitcode);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc(void);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(uint32_t dwtlsindex);
void* rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(uint32_t dwtlsindex);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(uint32_t dwtlsindex, void* lptlsvalue);
uint8_t rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(void* srwlock);
uint8_t rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(void* srwlock);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(void* hfile, uint32_t dwfileoffsetlow, uint32_t dwfileoffsethigh, uint32_t nnumberofbytestounlocklow, uint32_t nnumberofbytestounlockhigh);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(void* lpattributelist, uint32_t dwflags, uint64_t attribute, void* lpvalue, uint64_t cbsize, void* lppreviousvalue, void* lpreturnsize);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup(void);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(uint64_t s, uint32_t dwprocessid, void* lpprotocolinfo);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(void);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(uint64_t s, void* lpbuffers, uint32_t dwbuffercount, void* lpnumberofbytesrecvd, void* lpflags, void* lpoverlapped, void* lpcompletionroutine);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(uint64_t s, void* lpbuffers, uint32_t dwbuffercount, void* lpnumberofbytessent, uint32_t dwflags, void* lpoverlapped, void* lpcompletionroutine);
uint64_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(int32_t af, int32_t type, int32_t protocol, void* lpprotocolinfo, uint32_t g, uint32_t dwflags);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(uint16_t wversionrequested, void* lpwsadata);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(uint32_t ncount, void* lphandles, int32_t bwaitall, uint32_t dwmilliseconds);
uint32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(void* hhandle, uint32_t dwmilliseconds);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(void* address, void* compareaddress, uint64_t addresssize, uint32_t dwmilliseconds);
void rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(void* conditionvariable);
void rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(void* address);
void rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(void* address);
void rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(void* conditionvariable);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(uint32_t codepage, uint32_t dwflags, void* lpwidecharstr, int32_t cchwidechar, void* lpmultibytestr, int32_t cbmultibyte, void* lpdefaultchar, void* lpuseddefaultchar);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(void* hconsoleoutput, void* lpbuffer, uint32_t nnumberofcharstowrite, void* lpnumberofcharswritten, void* lpreserved);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(void* hfile, void* lpbuffer, uint32_t nnumberofbytestowrite, void* lpoverlapped, void* lpcompletionroutine);
uint64_t rustlangStdSysPalWindowsCWindowssysWindowsSysAccept(uint64_t s, void* addr, void* addrlen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysBind(uint64_t s, void* name, int32_t namelen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(uint64_t s);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysConnect(uint64_t s, void* name, int32_t namelen);
void rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(void* paddrinfo);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(void* pnodename, void* pservicename, void* phints, void* ppresult);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(uint64_t s, void* name, void* namelen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(uint64_t s, void* name, void* namelen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(uint64_t s, int32_t level, int32_t optname, void* optval, void* optlen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(uint64_t s, int32_t cmd, void* argp);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysListen(uint64_t s, int32_t backlog);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(void* lpstring);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysRecv(uint64_t s, void* buf, int32_t len, int32_t flags);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(uint64_t s, void* buf, int32_t len, int32_t flags, void* from, void* fromlen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSelect(int32_t nfds, void* readfds, void* writefds, void* exceptfds, void* timeout);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSend(uint64_t s, void* buf, int32_t len, int32_t flags);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSendto(uint64_t s, void* buf, int32_t len, int32_t flags, void* to, int32_t tolen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(uint64_t s, int32_t level, int32_t optname, void* optval, int32_t optlen);
int32_t rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(uint64_t s, int32_t how);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_WRAPPER_H */
