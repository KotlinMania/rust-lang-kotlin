#ifndef STD_SYS_IO_ERROR_UNIX_SYS_IO_ERROR_UNIX_WRAPPER_H
#define STD_SYS_IO_ERROR_UNIX_SYS_IO_ERROR_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t stdSysIoErrorUnixGetLIBCERRNO(void);
void stdSysIoErrorUnixSetLIBCERRNO(int32_t km_val);
void* stdSysIoErrorUnixErrnoLocation(void);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_IO_ERROR_UNIX_SYS_IO_ERROR_UNIX_WRAPPER_H */
