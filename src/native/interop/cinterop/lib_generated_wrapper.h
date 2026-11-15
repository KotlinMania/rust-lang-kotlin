#ifndef RUSTLANG_LIB_GENERATED_LIB_GENERATED_WRAPPER_H
#define RUSTLANG_LIB_GENERATED_LIB_GENERATED_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibGeneratedArgsGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedArgsSizesGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedEnvironGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedEnvironSizesGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedClockResGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedClockTimeGet(int32_t arg0, int64_t arg1, int32_t arg2);
int32_t rustlangLibGeneratedFdAdvise(int32_t arg0, int64_t arg1, int64_t arg2, int32_t arg3);
int32_t rustlangLibGeneratedFdAllocate(int32_t arg0, int64_t arg1, int64_t arg2);
int32_t rustlangLibGeneratedFdClose(int32_t arg0);
int32_t rustlangLibGeneratedFdDatasync(int32_t arg0);
int32_t rustlangLibGeneratedFdFdstatGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedFdFdstatSetFlags(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedFdFdstatSetRights(int32_t arg0, int64_t arg1, int64_t arg2);
int32_t rustlangLibGeneratedFdFilestatGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedFdFilestatSetSize(int32_t arg0, int64_t arg1);
int32_t rustlangLibGeneratedFdFilestatSetTimes(int32_t arg0, int64_t arg1, int64_t arg2, int32_t arg3);
int32_t rustlangLibGeneratedFdPread(int32_t arg0, int32_t arg1, int32_t arg2, int64_t arg3, int32_t arg4);
int32_t rustlangLibGeneratedFdPrestatGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedFdPrestatDirName(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t rustlangLibGeneratedFdPwrite(int32_t arg0, int32_t arg1, int32_t arg2, int64_t arg3, int32_t arg4);
int32_t rustlangLibGeneratedFdRead(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3);
int32_t rustlangLibGeneratedFdReaddir(int32_t arg0, int32_t arg1, int32_t arg2, int64_t arg3, int32_t arg4);
int32_t rustlangLibGeneratedFdRenumber(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedFdSeek(int32_t arg0, int64_t arg1, int32_t arg2, int32_t arg3);
int32_t rustlangLibGeneratedFdSync(int32_t arg0);
int32_t rustlangLibGeneratedFdTell(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedFdWrite(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3);
int32_t rustlangLibGeneratedPathCreateDirectory(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t rustlangLibGeneratedPathFilestatGet(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4);
int32_t rustlangLibGeneratedPathFilestatSetTimes(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int64_t arg4, int64_t arg5, int32_t arg6);
int32_t rustlangLibGeneratedPathLink(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5, int32_t arg6);
int32_t rustlangLibGeneratedPathOpen(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int64_t arg5, int64_t arg6, int32_t arg7, int32_t arg8);
int32_t rustlangLibGeneratedPathReadlink(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5);
int32_t rustlangLibGeneratedPathRemoveDirectory(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t rustlangLibGeneratedPathRename(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5);
int32_t rustlangLibGeneratedPathSymlink(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4);
int32_t rustlangLibGeneratedPathUnlinkFile(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t rustlangLibGeneratedPollOneoff(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3);
void rustlangLibGeneratedProcExit(int32_t arg0);
int32_t rustlangLibGeneratedProcRaise(int32_t arg0);
int32_t rustlangLibGeneratedSchedYield(void);
int32_t rustlangLibGeneratedRandomGet(int32_t arg0, int32_t arg1);
int32_t rustlangLibGeneratedSockAccept(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t rustlangLibGeneratedSockRecv(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5);
int32_t rustlangLibGeneratedSockSend(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4);
int32_t rustlangLibGeneratedSockShutdown(int32_t arg0, int32_t arg1);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIB_GENERATED_LIB_GENERATED_WRAPPER_H */
