#ifndef RUSTLANG_LIBC_NEW_GLIBC_SIGNAL_LIBC_NEW_GLIBC_SIGNAL_WRAPPER_H
#define RUSTLANG_LIBC_NEW_GLIBC_SIGNAL_LIBC_NEW_GLIBC_SIGNAL_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewGlibcSignalSigaction(int32_t signum, void* act, void* oldact);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_GLIBC_SIGNAL_LIBC_NEW_GLIBC_SIGNAL_WRAPPER_H */
