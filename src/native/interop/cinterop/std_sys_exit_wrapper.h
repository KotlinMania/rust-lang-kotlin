#ifndef RUSTLANG_STD_SYS_EXIT_STD_SYS_EXIT_WRAPPER_H
#define RUSTLANG_STD_SYS_EXIT_STD_SYS_EXIT_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangStdSysExitExit(int32_t status);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_EXIT_STD_SYS_EXIT_WRAPPER_H */
