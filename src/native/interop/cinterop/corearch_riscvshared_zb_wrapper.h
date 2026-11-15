#ifndef RUSTLANG_COREARCH_RISCVSHARED_ZB_COREARCH_RISCVSHARED_ZB_WRAPPER_H
#define RUSTLANG_COREARCH_RISCVSHARED_ZB_COREARCH_RISCVSHARED_ZB_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int64_t rustlangCorearchRiscvsharedZbOrcB64(int64_t rs1);
int64_t rustlangCorearchRiscvsharedZbClmul64(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscvsharedZbClmulh64(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscvsharedZbClmulr64(int64_t rs1, int64_t rs2);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_RISCVSHARED_ZB_COREARCH_RISCVSHARED_ZB_WRAPPER_H */
