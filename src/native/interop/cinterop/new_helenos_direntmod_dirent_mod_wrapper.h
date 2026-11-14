#ifndef LIBC_NEW_HELENOS_DIRENTMOD_DIRENT_MOD_NEW_HELENOS_DIRENTMOD_DIRENT_MOD_WRAPPER_H
#define LIBC_NEW_HELENOS_DIRENTMOD_DIRENT_MOD_NEW_HELENOS_DIRENTMOD_DIRENT_MOD_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* libcNewHelenosDirentmodDirentModOpendir(const char* name);
void* libcNewHelenosDirentmodDirentModReaddir(void* dir);
int32_t libcNewHelenosDirentmodDirentModClosedir(void* dir);
void libcNewHelenosDirentmodDirentModRewinddir(void* dir);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_DIRENTMOD_DIRENT_MOD_NEW_HELENOS_DIRENTMOD_DIRENT_MOD_WRAPPER_H */
