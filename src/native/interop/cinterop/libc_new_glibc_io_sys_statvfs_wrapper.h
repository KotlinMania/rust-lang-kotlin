#ifndef RUSTLANG_LIBC_NEW_GLIBC_IO_SYS_STATVFS_LIBC_NEW_GLIBC_IO_SYS_STATVFS_WRAPPER_H
#define RUSTLANG_LIBC_NEW_GLIBC_IO_SYS_STATVFS_LIBC_NEW_GLIBC_IO_SYS_STATVFS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewGlibcIoSysStatvfsStatvfs(const char* path, void* buf);
int32_t rustlangLibcNewGlibcIoSysStatvfsFstatvfs(int32_t fd, void* buf);
int32_t rustlangLibcNewGlibcIoSysStatvfsStatvfs64(const char* path, void* buf);
int32_t rustlangLibcNewGlibcIoSysStatvfsFstatvfs64(int32_t fd, void* buf);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_GLIBC_IO_SYS_STATVFS_LIBC_NEW_GLIBC_IO_SYS_STATVFS_WRAPPER_H */
