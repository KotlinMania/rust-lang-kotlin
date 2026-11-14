#ifndef COREARCH_RISCV64_ZK_RISCV64_ZK_WRAPPER_H
#define COREARCH_RISCV64_ZK_RISCV64_ZK_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int64_t corearchRiscv64ZkAes64es(int64_t rs1, int64_t rs2);
int64_t corearchRiscv64ZkAes64esm(int64_t rs1, int64_t rs2);
int64_t corearchRiscv64ZkAes64ds(int64_t rs1, int64_t rs2);
int64_t corearchRiscv64ZkAes64dsm(int64_t rs1, int64_t rs2);
int64_t corearchRiscv64ZkAes64im(int64_t rs1);
int64_t corearchRiscv64ZkSha512sig0(int64_t rs1);
int64_t corearchRiscv64ZkSha512sig1(int64_t rs1);
int64_t corearchRiscv64ZkSha512sum0(int64_t rs1);
int64_t corearchRiscv64ZkSha512sum1(int64_t rs1);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_RISCV64_ZK_RISCV64_ZK_WRAPPER_H */
