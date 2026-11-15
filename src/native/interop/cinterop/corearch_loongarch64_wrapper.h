#ifndef RUSTLANG_COREARCH_LOONGARCH64_COREARCH_LOONGARCH64_WRAPPER_H
#define RUSTLANG_COREARCH_LOONGARCH64_COREARCH_LOONGARCH64_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangCorearchLoongarch64CrcWBW(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrcWHW(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrcWWW(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrcWDW(int64_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrccWBW(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrccWHW(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrccWWW(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch64CrccWDW(int64_t a, int32_t b);
void rustlangCorearchLoongarch64Cacop(int64_t a, int64_t b, int64_t c);
int64_t rustlangCorearchLoongarch64Csrrd(int32_t a);
int64_t rustlangCorearchLoongarch64Csrwr(int64_t a, int32_t b);
int64_t rustlangCorearchLoongarch64Csrxchg(int64_t a, int64_t b, int32_t c);
int64_t rustlangCorearchLoongarch64IocsrrdD(int32_t a);
void rustlangCorearchLoongarch64IocsrwrD(int64_t a, int32_t b);
void rustlangCorearchLoongarch64Asrtle(int64_t a, int64_t b);
void rustlangCorearchLoongarch64Asrtgt(int64_t a, int64_t b);
int64_t rustlangCorearchLoongarch64Lddir(int64_t a, int64_t b);
void rustlangCorearchLoongarch64Ldpte(int64_t a, int64_t b);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_LOONGARCH64_COREARCH_LOONGARCH64_WRAPPER_H */
