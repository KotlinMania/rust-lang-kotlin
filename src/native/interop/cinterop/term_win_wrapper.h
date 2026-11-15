#ifndef TEST_TERM_WIN_TERM_WIN_WRAPPER_H
#define TEST_TERM_WIN_TERM_WIN_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t testTermWinSetConsoleTextAttribute(void* handle, uint16_t attr);
void* testTermWinGetStdHandle(uint32_t which);
int32_t testTermWinGetConsoleScreenBufferInfo(void* handle, void* info);

#ifdef __cplusplus
}
#endif

#endif /* TEST_TERM_WIN_TERM_WIN_WRAPPER_H */
