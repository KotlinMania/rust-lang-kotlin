#ifndef RUSTLANG_COREARCH_X86_BMI2_COREARCH_X86_BMI2_WRAPPER_H
#define RUSTLANG_COREARCH_X86_BMI2_COREARCH_X86_BMI2_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint32_t rustlangCorearchX86Bmi2X86Bmi2Bzhi32(uint32_t x, uint32_t y);
uint32_t rustlangCorearchX86Bmi2X86Bmi2Pdep32(uint32_t x, uint32_t y);
uint32_t rustlangCorearchX86Bmi2X86Bmi2Pext32(uint32_t x, uint32_t y);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_BMI2_COREARCH_X86_BMI2_WRAPPER_H */
