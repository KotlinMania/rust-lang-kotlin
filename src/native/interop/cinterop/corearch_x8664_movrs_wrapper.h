#ifndef RUSTLANG_COREARCH_X8664_MOVRS_COREARCH_X8664_MOVRS_WRAPPER_H
#define RUSTLANG_COREARCH_X8664_MOVRS_COREARCH_X8664_MOVRS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int8_t rustlangCorearchX8664MovrsMovrsqi(void* src);
int16_t rustlangCorearchX8664MovrsMovrshi(void* src);
int32_t rustlangCorearchX8664MovrsMovrssi(void* src);
int64_t rustlangCorearchX8664MovrsMovrsdi(void* src);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X8664_MOVRS_COREARCH_X8664_MOVRS_WRAPPER_H */
