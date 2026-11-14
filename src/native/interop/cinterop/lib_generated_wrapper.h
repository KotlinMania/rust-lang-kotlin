#ifndef WASIP1_LIB_GENERATED_LIB_GENERATED_WRAPPER_H
#define WASIP1_LIB_GENERATED_LIB_GENERATED_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t wasip1LibGeneratedArgsGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedArgsSizesGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedEnvironGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedEnvironSizesGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedClockResGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedClockTimeGet(int32_t arg0, int64_t arg1, int32_t arg2);
int32_t wasip1LibGeneratedFdAdvise(int32_t arg0, int64_t arg1, int64_t arg2, int32_t arg3);
int32_t wasip1LibGeneratedFdAllocate(int32_t arg0, int64_t arg1, int64_t arg2);
int32_t wasip1LibGeneratedFdClose(int32_t arg0);
int32_t wasip1LibGeneratedFdDatasync(int32_t arg0);
int32_t wasip1LibGeneratedFdFdstatGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedFdFdstatSetFlags(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedFdFdstatSetRights(int32_t arg0, int64_t arg1, int64_t arg2);
int32_t wasip1LibGeneratedFdFilestatGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedFdFilestatSetSize(int32_t arg0, int64_t arg1);
int32_t wasip1LibGeneratedFdFilestatSetTimes(int32_t arg0, int64_t arg1, int64_t arg2, int32_t arg3);
int32_t wasip1LibGeneratedFdPread(int32_t arg0, int32_t arg1, int32_t arg2, int64_t arg3, int32_t arg4);
int32_t wasip1LibGeneratedFdPrestatGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedFdPrestatDirName(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t wasip1LibGeneratedFdPwrite(int32_t arg0, int32_t arg1, int32_t arg2, int64_t arg3, int32_t arg4);
int32_t wasip1LibGeneratedFdRead(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3);
int32_t wasip1LibGeneratedFdReaddir(int32_t arg0, int32_t arg1, int32_t arg2, int64_t arg3, int32_t arg4);
int32_t wasip1LibGeneratedFdRenumber(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedFdSeek(int32_t arg0, int64_t arg1, int32_t arg2, int32_t arg3);
int32_t wasip1LibGeneratedFdSync(int32_t arg0);
int32_t wasip1LibGeneratedFdTell(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedFdWrite(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3);
int32_t wasip1LibGeneratedPathCreateDirectory(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t wasip1LibGeneratedPathFilestatGet(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4);
int32_t wasip1LibGeneratedPathFilestatSetTimes(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int64_t arg4, int64_t arg5, int32_t arg6);
int32_t wasip1LibGeneratedPathLink(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5, int32_t arg6);
int32_t wasip1LibGeneratedPathOpen(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int64_t arg5, int64_t arg6, int32_t arg7, int32_t arg8);
int32_t wasip1LibGeneratedPathReadlink(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5);
int32_t wasip1LibGeneratedPathRemoveDirectory(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t wasip1LibGeneratedPathRename(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5);
int32_t wasip1LibGeneratedPathSymlink(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4);
int32_t wasip1LibGeneratedPathUnlinkFile(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t wasip1LibGeneratedPollOneoff(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3);
void wasip1LibGeneratedProcExit(int32_t arg0);
int32_t wasip1LibGeneratedProcRaise(int32_t arg0);
int32_t wasip1LibGeneratedSchedYield(void);
int32_t wasip1LibGeneratedRandomGet(int32_t arg0, int32_t arg1);
int32_t wasip1LibGeneratedSockAccept(int32_t arg0, int32_t arg1, int32_t arg2);
int32_t wasip1LibGeneratedSockRecv(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4, int32_t arg5);
int32_t wasip1LibGeneratedSockSend(int32_t arg0, int32_t arg1, int32_t arg2, int32_t arg3, int32_t arg4);
int32_t wasip1LibGeneratedSockShutdown(int32_t arg0, int32_t arg1);

#ifdef __cplusplus
}
#endif

#endif /* WASIP1_LIB_GENERATED_LIB_GENERATED_WRAPPER_H */
