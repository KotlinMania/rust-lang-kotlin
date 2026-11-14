#ifndef PANICUNWIND_MIRI_MIRI_WRAPPER_H
#define PANICUNWIND_MIRI_MIRI_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void panicunwindMiriMiriStartUnwind(void* payload);

#ifdef __cplusplus
}
#endif

#endif /* PANICUNWIND_MIRI_MIRI_WRAPPER_H */
