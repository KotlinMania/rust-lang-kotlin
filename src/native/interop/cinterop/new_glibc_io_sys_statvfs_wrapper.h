#ifndef LIBC_NEW_GLIBC_IO_SYS_STATVFS_NEW_GLIBC_IO_SYS_STATVFS_WRAPPER_H
#define LIBC_NEW_GLIBC_IO_SYS_STATVFS_NEW_GLIBC_IO_SYS_STATVFS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t libcNewGlibcIoSysStatvfsStatvfs(const char* path, void* buf);
int32_t libcNewGlibcIoSysStatvfsFstatvfs(int32_t fd, void* buf);
int32_t libcNewGlibcIoSysStatvfsStatvfs64(const char* path, void* buf);
int32_t libcNewGlibcIoSysStatvfsFstatvfs64(int32_t fd, void* buf);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_GLIBC_IO_SYS_STATVFS_NEW_GLIBC_IO_SYS_STATVFS_WRAPPER_H */
