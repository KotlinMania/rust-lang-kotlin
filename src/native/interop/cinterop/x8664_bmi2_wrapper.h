#ifndef COREARCH_X8664_BMI2_X8664_BMI2_WRAPPER_H
#define COREARCH_X8664_BMI2_X8664_BMI2_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t corearchX8664Bmi2X86Bmi2Bzhi64(uint64_t x, uint64_t y);
uint64_t corearchX8664Bmi2X86Bmi2Pdep64(uint64_t x, uint64_t y);
uint64_t corearchX8664Bmi2X86Bmi2Pext64(uint64_t x, uint64_t y);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X8664_BMI2_X8664_BMI2_WRAPPER_H */
