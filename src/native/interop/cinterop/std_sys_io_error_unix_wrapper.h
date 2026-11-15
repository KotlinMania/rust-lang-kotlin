#ifndef RUSTLANG_STD_SYS_IO_ERROR_UNIX_STD_SYS_IO_ERROR_UNIX_WRAPPER_H
#define RUSTLANG_STD_SYS_IO_ERROR_UNIX_STD_SYS_IO_ERROR_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangStdSysIoErrorUnixGetLIBCERRNO(void);
void rustlangStdSysIoErrorUnixSetLIBCERRNO(int32_t km_val);
void* rustlangStdSysIoErrorUnixErrnoLocation(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_IO_ERROR_UNIX_STD_SYS_IO_ERROR_UNIX_WRAPPER_H */
