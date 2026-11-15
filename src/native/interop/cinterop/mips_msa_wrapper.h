#ifndef COREARCH_MIPS_MSA_MIPS_MSA_WRAPPER_H
#define COREARCH_MIPS_MSA_MIPS_MSA_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t corearchMipsMsaMsaCfcmsa(int32_t a);
void corearchMipsMsaMsaCtcmsa(int32_t imm5, int32_t a);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_MIPS_MSA_MIPS_MSA_WRAPPER_H */
