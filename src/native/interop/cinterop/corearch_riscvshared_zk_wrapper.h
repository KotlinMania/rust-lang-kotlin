#ifndef RUSTLANG_COREARCH_RISCVSHARED_ZK_COREARCH_RISCVSHARED_ZK_WRAPPER_H
#define RUSTLANG_COREARCH_RISCVSHARED_ZK_COREARCH_RISCVSHARED_ZK_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangCorearchRiscvsharedZkSm4ed(int32_t rs1, int32_t rs2, int32_t bs);
int32_t rustlangCorearchRiscvsharedZkSm4ks(int32_t rs1, int32_t rs2, int32_t bs);
int32_t rustlangCorearchRiscvsharedZkSm3p0(int32_t rs1);
int32_t rustlangCorearchRiscvsharedZkSm3p1(int32_t rs1);
int32_t rustlangCorearchRiscvsharedZkSha256sig0(int32_t rs1);
int32_t rustlangCorearchRiscvsharedZkSha256sig1(int32_t rs1);
int32_t rustlangCorearchRiscvsharedZkSha256sum0(int32_t rs1);
int32_t rustlangCorearchRiscvsharedZkSha256sum1(int32_t rs1);
int64_t rustlangCorearchRiscvsharedZkXperm864(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscvsharedZkXperm464(int64_t rs1, int64_t rs2);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_RISCVSHARED_ZK_COREARCH_RISCVSHARED_ZK_WRAPPER_H */
