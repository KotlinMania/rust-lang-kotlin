#ifndef COREARCH_RISCVSHARED_ZB_RISCVSHARED_ZB_WRAPPER_H
#define COREARCH_RISCVSHARED_ZB_RISCVSHARED_ZB_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int64_t corearchRiscvsharedZbOrcB64(int64_t rs1);
int64_t corearchRiscvsharedZbClmul64(int64_t rs1, int64_t rs2);
int64_t corearchRiscvsharedZbClmulh64(int64_t rs1, int64_t rs2);
int64_t corearchRiscvsharedZbClmulr64(int64_t rs1, int64_t rs2);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_RISCVSHARED_ZB_RISCVSHARED_ZB_WRAPPER_H */
