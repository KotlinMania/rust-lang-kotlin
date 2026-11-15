#ifndef STD_SYS_FS_UNIX_SYS_FS_UNIX_WRAPPER_H
#define STD_SYS_FS_UNIX_SYS_FS_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t stdSysFsUnixOpen(const char* pathname, int32_t flags, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t stdSysFsUnixClose(int32_t fd);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_FS_UNIX_SYS_FS_UNIX_WRAPPER_H */
