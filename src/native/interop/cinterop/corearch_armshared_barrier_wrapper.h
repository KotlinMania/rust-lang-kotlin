#ifndef RUSTLANG_COREARCH_ARMSHARED_BARRIER_COREARCH_ARMSHARED_BARRIER_WRAPPER_H
#define RUSTLANG_COREARCH_ARMSHARED_BARRIER_COREARCH_ARMSHARED_BARRIER_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchArmsharedBarrierDmb(int32_t arg0);
void rustlangCorearchArmsharedBarrierDsb(int32_t arg0);
void rustlangCorearchArmsharedBarrierIsb(int32_t arg0);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_ARMSHARED_BARRIER_COREARCH_ARMSHARED_BARRIER_WRAPPER_H */
