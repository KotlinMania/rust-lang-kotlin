#ifndef COREARCH_RISCVSHARED_ZK_RISCVSHARED_ZK_WRAPPER_H
#define COREARCH_RISCVSHARED_ZK_RISCVSHARED_ZK_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t corearchRiscvsharedZkSm4ed(int32_t rs1, int32_t rs2, int32_t bs);
int32_t corearchRiscvsharedZkSm4ks(int32_t rs1, int32_t rs2, int32_t bs);
int32_t corearchRiscvsharedZkSm3p0(int32_t rs1);
int32_t corearchRiscvsharedZkSm3p1(int32_t rs1);
int32_t corearchRiscvsharedZkSha256sig0(int32_t rs1);
int32_t corearchRiscvsharedZkSha256sig1(int32_t rs1);
int32_t corearchRiscvsharedZkSha256sum0(int32_t rs1);
int32_t corearchRiscvsharedZkSha256sum1(int32_t rs1);
int64_t corearchRiscvsharedZkXperm864(int64_t rs1, int64_t rs2);
int64_t corearchRiscvsharedZkXperm464(int64_t rs1, int64_t rs2);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_RISCVSHARED_ZK_RISCVSHARED_ZK_WRAPPER_H */
