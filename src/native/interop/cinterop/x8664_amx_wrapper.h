#ifndef COREARCH_X8664_AMX_X8664_AMX_WRAPPER_H
#define COREARCH_X8664_AMX_X8664_AMX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX8664AmxLdtilecfg(void* memAddr);
void corearchX8664AmxSttilecfg(void* memAddr);
void corearchX8664AmxTileloadd64(int8_t dst, void* base, uint64_t stride);
void corearchX8664AmxTileloaddt164(int8_t dst, void* base, uint64_t stride);
void corearchX8664AmxTilerelease(void);
void corearchX8664AmxTilestored64(int8_t dst, void* base, uint64_t stride);
void corearchX8664AmxTilezero(int8_t dst);
void corearchX8664AmxTdpbf16ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpbuud(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpbusd(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpbsud(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpbssd(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpfp16ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTcmmimfp16ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTcmmrlfp16ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpbf8ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdpbhf8ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdphbf8ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTdphf8ps(int8_t dst, int8_t a, int8_t b);
void corearchX8664AmxTileloaddrs64(int8_t dst, void* base, uint64_t stride);
void corearchX8664AmxTileloaddrst164(int8_t dst, void* base, uint64_t stride);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X8664_AMX_X8664_AMX_WRAPPER_H */
