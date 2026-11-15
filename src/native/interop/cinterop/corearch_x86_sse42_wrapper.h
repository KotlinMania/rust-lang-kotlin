#ifndef RUSTLANG_COREARCH_X86_SSE42_COREARCH_X86_SSE42_WRAPPER_H
#define RUSTLANG_COREARCH_X86_SSE42_COREARCH_X86_SSE42_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint32_t rustlangCorearchX86Sse42Crc32328(uint32_t crc, uint8_t v);
uint32_t rustlangCorearchX86Sse42Crc323216(uint32_t crc, uint16_t v);
uint32_t rustlangCorearchX86Sse42Crc323232(uint32_t crc, uint32_t v);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_SSE42_COREARCH_X86_SSE42_WRAPPER_H */
