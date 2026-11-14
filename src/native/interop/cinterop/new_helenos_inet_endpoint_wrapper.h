#ifndef LIBC_NEW_HELENOS_INET_ENDPOINT_NEW_HELENOS_INET_ENDPOINT_WRAPPER_H
#define LIBC_NEW_HELENOS_INET_ENDPOINT_NEW_HELENOS_INET_ENDPOINT_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void libcNewHelenosInetEndpointInetEpInit(void* ep);
void libcNewHelenosInetEndpointInetEp2Init(void* epp);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_INET_ENDPOINT_NEW_HELENOS_INET_ENDPOINT_WRAPPER_H */
