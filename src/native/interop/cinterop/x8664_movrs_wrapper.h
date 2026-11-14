#ifndef COREARCH_X8664_MOVRS_X8664_MOVRS_WRAPPER_H
#define COREARCH_X8664_MOVRS_X8664_MOVRS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int8_t corearchX8664MovrsMovrsqi(void* src);
int16_t corearchX8664MovrsMovrshi(void* src);
int32_t corearchX8664MovrsMovrssi(void* src);
int64_t corearchX8664MovrsMovrsdi(void* src);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X8664_MOVRS_X8664_MOVRS_WRAPPER_H */
