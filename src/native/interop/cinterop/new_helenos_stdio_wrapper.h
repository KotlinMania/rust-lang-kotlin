#ifndef LIBC_NEW_HELENOS_STDIO_NEW_HELENOS_STDIO_WRAPPER_H
#define LIBC_NEW_HELENOS_STDIO_NEW_HELENOS_STDIO_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* libcNewHelenosStdioGetSTDIN(void);
void* libcNewHelenosStdioGetSTDOUT(void);
void* libcNewHelenosStdioGetSTDERR(void);
int32_t libcNewHelenosStdioFputc(int32_t c, void* stream);
int32_t libcNewHelenosStdioFputs(const char* s, void* stream);
void* libcNewHelenosStdioFopen(const char* filename, const char* mode);
int32_t libcNewHelenosStdioFclose(void* stream);
int32_t libcNewHelenosStdioFflush(void* stream);
uint64_t libcNewHelenosStdioFread(void* buf, uint64_t size, uint64_t nmemb, void* stream);
uint64_t libcNewHelenosStdioFwrite(void* buf, uint64_t size, uint64_t nmemb, void* stream);
int32_t libcNewHelenosStdioFseek(void* stream, int64_t offset, int32_t whence);
int64_t libcNewHelenosStdioFtell(void* stream);
int32_t libcNewHelenosStdioFileno(void* stream);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_STDIO_NEW_HELENOS_STDIO_WRAPPER_H */
