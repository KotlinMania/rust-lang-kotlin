#ifndef RUSTLANG_TEST_TERM_WIN_TEST_TERM_WIN_WRAPPER_H
#define RUSTLANG_TEST_TERM_WIN_TEST_TERM_WIN_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangTestTermWinSetConsoleTextAttribute(void* handle, uint16_t attr);
void* rustlangTestTermWinGetStdHandle(uint32_t which);
int32_t rustlangTestTermWinGetConsoleScreenBufferInfo(void* handle, void* info);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_TEST_TERM_WIN_TEST_TERM_WIN_WRAPPER_H */
