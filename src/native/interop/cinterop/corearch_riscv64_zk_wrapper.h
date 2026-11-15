#ifndef RUSTLANG_COREARCH_RISCV64_ZK_COREARCH_RISCV64_ZK_WRAPPER_H
#define RUSTLANG_COREARCH_RISCV64_ZK_COREARCH_RISCV64_ZK_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int64_t rustlangCorearchRiscv64ZkAes64es(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscv64ZkAes64esm(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscv64ZkAes64ds(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscv64ZkAes64dsm(int64_t rs1, int64_t rs2);
int64_t rustlangCorearchRiscv64ZkAes64im(int64_t rs1);
int64_t rustlangCorearchRiscv64ZkSha512sig0(int64_t rs1);
int64_t rustlangCorearchRiscv64ZkSha512sig1(int64_t rs1);
int64_t rustlangCorearchRiscv64ZkSha512sum0(int64_t rs1);
int64_t rustlangCorearchRiscv64ZkSha512sum1(int64_t rs1);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_RISCV64_ZK_COREARCH_RISCV64_ZK_WRAPPER_H */
