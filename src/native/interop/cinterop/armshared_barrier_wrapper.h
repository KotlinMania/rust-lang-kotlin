#ifndef COREARCH_ARMSHARED_BARRIER_ARMSHARED_BARRIER_WRAPPER_H
#define COREARCH_ARMSHARED_BARRIER_ARMSHARED_BARRIER_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchArmsharedBarrierExternDmb(int32_t arg0);
void corearchArmsharedBarrierExternDsb(int32_t arg0);
void corearchArmsharedBarrierExternIsb(int32_t arg0);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_ARMSHARED_BARRIER_ARMSHARED_BARRIER_WRAPPER_H */
