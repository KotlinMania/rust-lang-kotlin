#ifndef RUSTLANG_COREARCH_X8664_AMX_COREARCH_X8664_AMX_WRAPPER_H
#define RUSTLANG_COREARCH_X8664_AMX_COREARCH_X8664_AMX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX8664AmxLdtilecfg(void* memAddr);
void rustlangCorearchX8664AmxSttilecfg(void* memAddr);
void rustlangCorearchX8664AmxTileloadd64(int8_t dst, void* base, uint64_t stride);
void rustlangCorearchX8664AmxTileloaddt164(int8_t dst, void* base, uint64_t stride);
void rustlangCorearchX8664AmxTilerelease(void);
void rustlangCorearchX8664AmxTilestored64(int8_t dst, void* base, uint64_t stride);
void rustlangCorearchX8664AmxTilezero(int8_t dst);
void rustlangCorearchX8664AmxTdpbf16ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpbuud(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpbusd(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpbsud(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpbssd(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpfp16ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTcmmimfp16ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTcmmrlfp16ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpbf8ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdpbhf8ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdphbf8ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTdphf8ps(int8_t dst, int8_t a, int8_t b);
void rustlangCorearchX8664AmxTileloaddrs64(int8_t dst, void* base, uint64_t stride);
void rustlangCorearchX8664AmxTileloaddrst164(int8_t dst, void* base, uint64_t stride);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X8664_AMX_COREARCH_X8664_AMX_WRAPPER_H */
