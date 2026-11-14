#ifndef LIBC_HELENOS_HELENOS_WRAPPER_H
#define LIBC_HELENOS_HELENOS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* libcHelenosGetENVIRON(void);
const char* libcHelenosStrError(int32_t err);
const char* libcHelenosStrErrorName(int32_t err);
int32_t libcHelenosPthreadKeyCreate(void* key, void* destructor);
void* libcHelenosPthreadGetspecific(int32_t key);
int32_t libcHelenosPthreadSetspecific(int32_t key, void* value);
int32_t libcHelenosPthreadKeyDelete(int32_t key);
int32_t libcHelenosClockGetres(int32_t clockId, void* res);
int32_t libcHelenosClockGettime(int32_t clockId, void* tp);
int32_t libcHelenosClockSettime(int32_t clockId, void* tp);
int32_t libcHelenosClockNanosleep(int32_t clockId, int32_t flags, void* rqtp, void* rmtp);
int32_t libcHelenosGetpid(void);
char* libcHelenosGetcwd(char* buf, uint64_t size);
int32_t libcHelenosChdir(const char* buf);
int32_t libcHelenosIsatty(int32_t fd);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_HELENOS_HELENOS_WRAPPER_H */
