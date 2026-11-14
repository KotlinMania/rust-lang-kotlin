#ifndef LIBC_NEW_HELENOS_VFS_VFS_NEW_HELENOS_VFS_VFS_WRAPPER_H
#define LIBC_NEW_HELENOS_VFS_VFS_NEW_HELENOS_VFS_VFS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t libcNewHelenosVfsVfsVfsFhandle(void* file, void* handle);
int32_t libcNewHelenosVfsVfsVfsStat(int32_t handle, void* arg1Stat);
int32_t libcNewHelenosVfsVfsVfsLinkPath(const char* path, int32_t kind, void* linkedfd);
int32_t libcNewHelenosVfsVfsVfsUnlinkPath(const char* path);
int32_t libcNewHelenosVfsVfsVfsRenamePath(const char* oldpath, const char* newpath);
int32_t libcNewHelenosVfsVfsVfsStatPath(const char* path, void* arg1Stat);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_VFS_VFS_NEW_HELENOS_VFS_VFS_WRAPPER_H */
