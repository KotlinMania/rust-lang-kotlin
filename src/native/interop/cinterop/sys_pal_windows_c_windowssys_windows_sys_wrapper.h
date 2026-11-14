#ifndef STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_WRAPPER_H
#define STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(void* srwlock);
void stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(void* srwlock);
void* stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(uint32_t first, void* handler);
int32_t stdSysPalWindowsCWindowssysWindowsSysCancelIo(void* hfile);
int32_t stdSysPalWindowsCWindowssysWindowsSysCloseHandle(void* hobject);
int32_t stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(void* lpstring1, int32_t cchcount1, void* lpstring2, int32_t cchcount2, int32_t bignorecase);
int32_t stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(void* lpexistingfilename, void* lpnewfilename, void* lpprogressroutine, void* lpdata, void* pbcancel, uint32_t dwcopyflags);
int32_t stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(void* lppathname, void* lpsecurityattributes);
void* stdSysPalWindowsCWindowssysWindowsSysCreateEventW(void* lpeventattributes, int32_t bmanualreset, int32_t binitialstate, void* lpname);
void* stdSysPalWindowsCWindowssysWindowsSysCreateFileW(void* lpfilename, uint32_t dwdesiredaccess, uint32_t dwsharemode, void* lpsecurityattributes, uint32_t dwcreationdisposition, uint32_t dwflagsandattributes, void* htemplatefile);
int32_t stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(void* lpfilename, void* lpexistingfilename, void* lpsecurityattributes);
void* stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(void* lpname, uint32_t dwopenmode, uint32_t dwpipemode, uint32_t nmaxinstances, uint32_t noutbuffersize, uint32_t ninbuffersize, uint32_t ndefaulttimeout, void* lpsecurityattributes);
int32_t stdSysPalWindowsCWindowssysWindowsSysCreatePipe(void* hreadpipe, void* hwritepipe, void* lppipeattributes, uint32_t nsize);
int32_t stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(void* lpapplicationname, void* lpcommandline, void* lpprocessattributes, void* lpthreadattributes, int32_t binherithandles, uint32_t dwcreationflags, void* lpenvironment, void* lpcurrentdirectory, void* lpstartupinfo, void* lpprocessinformation);
uint8_t stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(void* lpsymlinkfilename, void* lptargetfilename, uint32_t dwflags);
void* stdSysPalWindowsCWindowssysWindowsSysCreateThread(void* lpthreadattributes, uint64_t dwstacksize, void* lpstartaddress, void* lpparameter, uint32_t dwcreationflags, void* lpthreadid);
void* stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(void* lptimerattributes, void* lptimername, uint32_t dwflags, uint32_t dwdesiredaccess);
int32_t stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(void* lpfilename);
void stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(void* lpattributelist);
int32_t stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(void* hdevice, uint32_t dwiocontrolcode, void* lpinbuffer, uint32_t ninbuffersize, void* lpoutbuffer, uint32_t noutbuffersize, void* lpbytesreturned, void* lpoverlapped);
int32_t stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(void* hsourceprocesshandle, void* hsourcehandle, void* htargetprocesshandle, void* lptargethandle, uint32_t dwdesiredaccess, int32_t binherithandle, uint32_t dwoptions);
void stdSysPalWindowsCWindowssysWindowsSysExitProcess(uint32_t uexitcode);
int32_t stdSysPalWindowsCWindowssysWindowsSysFindClose(void* hfindfile);
void* stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(void* lpfilename, int32_t finfolevelid, void* lpfindfiledata, int32_t fsearchop, void* lpsearchfilter, uint32_t dwadditionalflags);
int32_t stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(void* hfindfile, void* lpfindfiledata);
uint32_t stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(void* lpcallback);
int32_t stdSysPalWindowsCWindowssysWindowsSysFlsFree(uint32_t dwflsindex);
void* stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(uint32_t dwflsindex);
int32_t stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(uint32_t dwflsindex, void* lpflsdata);
int32_t stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(void* hfile);
uint32_t stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(uint32_t dwflags, void* lpsource, uint32_t dwmessageid, uint32_t dwlanguageid, void* lpbuffer, uint32_t nsize, void* arguments);
int32_t stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(void* penv);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(uint16_t groupnumber);
void* stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(void);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(void* hconsolehandle, void* lpmode);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(void);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(uint32_t nbufferlength, void* lpbuffer);
void* stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(void);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(void);
void* stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(void);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(void);
void* stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(void);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(void* lpname, void* lpbuffer, uint32_t nsize);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(void* hprocess, void* lpexitcode);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(void* lpfilename);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(void* hfile, void* lpfileinformation);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(void* hfile, int32_t fileinformationclass, void* lpfileinformation, uint32_t dwbuffersize);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(void* hfile, void* lpfilesize);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFileType(void* hfile);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(void* hfile, void* lpszfilepath, uint32_t cchfilepath, uint32_t dwflags);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(void* lpfilename, uint32_t nbufferlength, void* lpbuffer, void* lpfilepart);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(void* name, int32_t namelen);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetLastError(void);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(void);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(void* hmodule, void* lpfilename, uint32_t nsize);
void* stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(void* lpmodulename);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(uint32_t dwflags, void* lpmodulename, void* phmodule);
void* stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(void* lpmodulename);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(void* hfile, void* lpoverlapped, void* lpnumberofbytestransferred, int32_t bwait);
void* stdSysPalWindowsCWindowssysWindowsSysGetProcAddress(void* hmodule, void* lpprocname);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetProcessId(void* process);
void* stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(uint32_t nstdhandle);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(void* lpbuffer, uint32_t usize);
void stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(void* lpsysteminfo);
void stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(void* lpsystemtimeasfiletime);
void stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(void* lpsystemtimeasfiletime);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(uint32_t nbufferlength, void* lpbuffer);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(void* htoken, void* lpprofiledir, void* lpcchsize);
uint32_t stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(void* lpbuffer, uint32_t usize);
int32_t stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(void* lpinitonce, uint32_t dwflags, void* fpending, void* lpcontext);
int32_t stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(void* lpinitonce, uint32_t dwflags, void* lpcontext);
int32_t stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(void* lpattributelist, uint32_t dwattributecount, uint32_t dwflags, void* lpsize);
int32_t stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(void);
void* stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(void* lplibfilename, void* hfile, uint32_t dwflags);
void* stdSysPalWindowsCWindowssysWindowsSysLocalFree(void* hmem);
int32_t stdSysPalWindowsCWindowssysWindowsSysLockFileEx(void* hfile, uint32_t dwflags, uint32_t dwreserved, uint32_t nnumberofbytestolocklow, uint32_t nnumberofbytestolockhigh, void* lpoverlapped);
int32_t stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(void* lpexistingfilename, void* lpnewfilename, uint32_t dwflags);
int32_t stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(uint32_t codepage, uint32_t dwflags, void* lpmultibytestr, int32_t cbmultibyte, void* lpwidecharstr, int32_t cchwidechar);
int32_t stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, void* allocationsize, uint32_t fileattributes, uint32_t shareaccess, uint32_t createdisposition, uint32_t createoptions, void* eabuffer, uint32_t ealength);
int32_t stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, uint32_t shareaccess, uint32_t openoptions);
int32_t stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(void* filehandle, void* iostatusblock, void* fsinformation, uint32_t length, int32_t fsinformationclass);
int32_t stdSysPalWindowsCWindowssysWindowsSysNtReadFile(void* filehandle, void* event, void* apcroutine, void* apccontext, void* iostatusblock, void* buffer, uint32_t length, void* byteoffset, void* key);
int32_t stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(void* filehandle, void* iostatusblock, void* fileinformation, uint32_t length, int32_t fileinformationclass);
int32_t stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(void* filehandle, void* event, void* apcroutine, void* apccontext, void* iostatusblock, void* buffer, uint32_t length, void* byteoffset, void* key);
int32_t stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(void* processhandle, uint32_t desiredaccess, void* tokenhandle);
uint32_t stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(void* lpdevicename, void* lptargetpath, uint32_t ucchmax);
int32_t stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(void* lpperformancecount);
int32_t stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(void* lpfrequency);
int32_t stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(void* hconsoleinput, void* lpbuffer, uint32_t nnumberofcharstoread, void* lpnumberofcharsread, void* pinputcontrol);
int32_t stdSysPalWindowsCWindowssysWindowsSysReadFile(void* hfile, void* lpbuffer, uint32_t nnumberofbytestoread, void* lpnumberofbytesread, void* lpoverlapped);
int32_t stdSysPalWindowsCWindowssysWindowsSysReadFileEx(void* hfile, void* lpbuffer, uint32_t nnumberofbytestoread, void* lpoverlapped, void* lpcompletionroutine);
void stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(void* srwlock);
void stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(void* srwlock);
int32_t stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(void* lppathname);
uint8_t stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(void* randombuffer, uint32_t randombufferlength);
uint32_t stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(int32_t status);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(void* lppathname);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(void* lpname, void* lpvalue);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(void* lpfilename, uint32_t dwfileattributes);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(void* hfile, int32_t fileinformationclass, void* lpfileinformation, uint32_t dwbuffersize);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(void* hfile, int64_t lidistancetomove, void* lpnewfilepointer, uint32_t dwmovemethod);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetFileTime(void* hfile, void* lpcreationtime, void* lplastaccesstime, void* lplastwritetime);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(void* hobject, uint32_t dwmask, uint32_t dwflags);
void stdSysPalWindowsCWindowssysWindowsSysSetLastError(uint32_t dwerrcode);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(uint32_t nstdhandle, void* hhandle);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(void* stacksizeinbytes);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(void* htimer, void* lpduetime, int32_t lperiod, void* pfncompletionroutine, void* lpargtocompletionroutine, int32_t fresume);
void stdSysPalWindowsCWindowssysWindowsSysSleep(uint32_t dwmilliseconds);
int32_t stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(void* conditionvariable, void* srwlock, uint32_t dwmilliseconds, uint32_t flags);
uint32_t stdSysPalWindowsCWindowssysWindowsSysSleepEx(uint32_t dwmilliseconds, int32_t balertable);
int32_t stdSysPalWindowsCWindowssysWindowsSysSwitchToThread(void);
int32_t stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(void* hprocess, uint32_t uexitcode);
uint32_t stdSysPalWindowsCWindowssysWindowsSysTlsAlloc(void);
int32_t stdSysPalWindowsCWindowssysWindowsSysTlsFree(uint32_t dwtlsindex);
void* stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(uint32_t dwtlsindex);
int32_t stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(uint32_t dwtlsindex, void* lptlsvalue);
uint8_t stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(void* srwlock);
uint8_t stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(void* srwlock);
int32_t stdSysPalWindowsCWindowssysWindowsSysUnlockFile(void* hfile, uint32_t dwfileoffsetlow, uint32_t dwfileoffsethigh, uint32_t nnumberofbytestounlocklow, uint32_t nnumberofbytestounlockhigh);
int32_t stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(void* lpattributelist, uint32_t dwflags, uint64_t attribute, void* lpvalue, uint64_t cbsize, void* lppreviousvalue, void* lpreturnsize);
int32_t stdSysPalWindowsCWindowssysWindowsSysWSACleanup(void);
int32_t stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(uint64_t s, uint32_t dwprocessid, void* lpprotocolinfo);
int32_t stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(void);
int32_t stdSysPalWindowsCWindowssysWindowsSysWSARecv(uint64_t s, void* lpbuffers, uint32_t dwbuffercount, void* lpnumberofbytesrecvd, void* lpflags, void* lpoverlapped, void* lpcompletionroutine);
int32_t stdSysPalWindowsCWindowssysWindowsSysWSASend(uint64_t s, void* lpbuffers, uint32_t dwbuffercount, void* lpnumberofbytessent, uint32_t dwflags, void* lpoverlapped, void* lpcompletionroutine);
uint64_t stdSysPalWindowsCWindowssysWindowsSysWSASocketW(int32_t af, int32_t type, int32_t protocol, void* lpprotocolinfo, uint32_t g, uint32_t dwflags);
int32_t stdSysPalWindowsCWindowssysWindowsSysWSAStartup(uint16_t wversionrequested, void* lpwsadata);
uint32_t stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(uint32_t ncount, void* lphandles, int32_t bwaitall, uint32_t dwmilliseconds);
uint32_t stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(void* hhandle, uint32_t dwmilliseconds);
int32_t stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(void* address, void* compareaddress, uint64_t addresssize, uint32_t dwmilliseconds);
void stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(void* conditionvariable);
void stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(void* address);
void stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(void* address);
void stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(void* conditionvariable);
int32_t stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(uint32_t codepage, uint32_t dwflags, void* lpwidecharstr, int32_t cchwidechar, void* lpmultibytestr, int32_t cbmultibyte, void* lpdefaultchar, void* lpuseddefaultchar);
int32_t stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(void* hconsoleoutput, void* lpbuffer, uint32_t nnumberofcharstowrite, void* lpnumberofcharswritten, void* lpreserved);
int32_t stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(void* hfile, void* lpbuffer, uint32_t nnumberofbytestowrite, void* lpoverlapped, void* lpcompletionroutine);
uint64_t stdSysPalWindowsCWindowssysWindowsSysAccept(uint64_t s, void* addr, void* addrlen);
int32_t stdSysPalWindowsCWindowssysWindowsSysBind(uint64_t s, void* name, int32_t namelen);
int32_t stdSysPalWindowsCWindowssysWindowsSysClosesocket(uint64_t s);
int32_t stdSysPalWindowsCWindowssysWindowsSysConnect(uint64_t s, void* name, int32_t namelen);
void stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(void* paddrinfo);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(void* pnodename, void* pservicename, void* phints, void* ppresult);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetpeername(uint64_t s, void* name, void* namelen);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetsockname(uint64_t s, void* name, void* namelen);
int32_t stdSysPalWindowsCWindowssysWindowsSysGetsockopt(uint64_t s, int32_t level, int32_t optname, void* optval, void* optlen);
int32_t stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(uint64_t s, int32_t cmd, void* argp);
int32_t stdSysPalWindowsCWindowssysWindowsSysListen(uint64_t s, int32_t backlog);
int32_t stdSysPalWindowsCWindowssysWindowsSysLstrlenW(void* lpstring);
int32_t stdSysPalWindowsCWindowssysWindowsSysRecv(uint64_t s, void* buf, int32_t len, int32_t flags);
int32_t stdSysPalWindowsCWindowssysWindowsSysRecvfrom(uint64_t s, void* buf, int32_t len, int32_t flags, void* from, void* fromlen);
int32_t stdSysPalWindowsCWindowssysWindowsSysSelect(int32_t nfds, void* readfds, void* writefds, void* exceptfds, void* timeout);
int32_t stdSysPalWindowsCWindowssysWindowsSysSend(uint64_t s, void* buf, int32_t len, int32_t flags);
int32_t stdSysPalWindowsCWindowssysWindowsSysSendto(uint64_t s, void* buf, int32_t len, int32_t flags, void* to, int32_t tolen);
int32_t stdSysPalWindowsCWindowssysWindowsSysSetsockopt(uint64_t s, int32_t level, int32_t optname, void* optval, int32_t optlen);
int32_t stdSysPalWindowsCWindowssysWindowsSysShutdown(uint64_t s, int32_t how);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_SYS_PAL_WINDOWS_C_WINDOWSSYS_WINDOWS_SYS_WRAPPER_H */
