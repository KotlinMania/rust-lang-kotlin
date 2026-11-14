#ifndef LIBC_NEW_GLIBC_SIGNAL_NEW_GLIBC_SIGNAL_WRAPPER_H
#define LIBC_NEW_GLIBC_SIGNAL_NEW_GLIBC_SIGNAL_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t libcNewGlibcSignalSigaction(int32_t signum, void* act, void* oldact);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_GLIBC_SIGNAL_NEW_GLIBC_SIGNAL_WRAPPER_H */
