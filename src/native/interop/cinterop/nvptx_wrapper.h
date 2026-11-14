#ifndef COREARCH_NVPTX_NVPTX_WRAPPER_H
#define COREARCH_NVPTX_NVPTX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchNvptxSyncthreads(uint32_t a);
uint32_t corearchNvptxBlockDimX(void);
uint32_t corearchNvptxBlockDimY(void);
uint32_t corearchNvptxBlockDimZ(void);
uint32_t corearchNvptxBlockIdxX(void);
uint32_t corearchNvptxBlockIdxY(void);
uint32_t corearchNvptxBlockIdxZ(void);
uint32_t corearchNvptxGridDimX(void);
uint32_t corearchNvptxGridDimY(void);
uint32_t corearchNvptxGridDimZ(void);
uint32_t corearchNvptxThreadIdxX(void);
uint32_t corearchNvptxThreadIdxY(void);
uint32_t corearchNvptxThreadIdxZ(void);
int32_t corearchNvptxVprintf(void* format, void* valist);
void* corearchNvptxMalloc(uint64_t size);
void corearchNvptxFree(void* ptr);
void corearchNvptxAssertfail(void* message, void* file, uint32_t line, void* function, uint64_t charSize);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_NVPTX_NVPTX_WRAPPER_H */
