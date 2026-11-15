#ifndef RUSTLANG_COREARCH_MIPS_MSA_COREARCH_MIPS_MSA_WRAPPER_H
#define RUSTLANG_COREARCH_MIPS_MSA_COREARCH_MIPS_MSA_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangCorearchMipsMsaMsaCfcmsa(int32_t a);
void rustlangCorearchMipsMsaMsaCtcmsa(int32_t imm5, int32_t a);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_MIPS_MSA_COREARCH_MIPS_MSA_WRAPPER_H */
