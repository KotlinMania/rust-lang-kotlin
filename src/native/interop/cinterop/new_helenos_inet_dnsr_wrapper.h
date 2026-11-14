#ifndef LIBC_NEW_HELENOS_INET_DNSR_NEW_HELENOS_INET_DNSR_WRAPPER_H
#define LIBC_NEW_HELENOS_INET_DNSR_NEW_HELENOS_INET_DNSR_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t libcNewHelenosInetDnsrDnsrName2host(const char* name, void* info, uint32_t ipver);
void libcNewHelenosInetDnsrDnsrHostinfoDestroy(void* info);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_INET_DNSR_NEW_HELENOS_INET_DNSR_WRAPPER_H */
