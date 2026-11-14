#include "new_helenos_direntmod_dirent_mod_wrapper.h"
#include <errno.h>
#if !defined(_WIN32)
#include <dirent.h>
#endif

void* libcNewHelenosDirentmodDirentModOpendir(const char* name) {
#if !defined(_WIN32)
    return (void*)opendir(name);
#else
    (void)name;
    errno = ENOSYS;
    return NULL;
#endif
}

void* libcNewHelenosDirentmodDirentModReaddir(void* dir) {
#if !defined(_WIN32)
    return (void*)readdir((DIR*)dir);
#else
    (void)dir;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t libcNewHelenosDirentmodDirentModClosedir(void* dir) {
#if !defined(_WIN32)
    return (int32_t)closedir((DIR*)dir);
#else
    (void)dir;
    errno = ENOSYS;
    return -1;
#endif
}

void libcNewHelenosDirentmodDirentModRewinddir(void* dir) {
#if !defined(_WIN32)
    rewinddir((DIR*)dir);
#else
    (void)dir;
    errno = ENOSYS;
#endif
}

