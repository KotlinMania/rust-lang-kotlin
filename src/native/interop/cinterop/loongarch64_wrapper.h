#ifndef COREARCH_LOONGARCH64_LOONGARCH64_WRAPPER_H
#define COREARCH_LOONGARCH64_LOONGARCH64_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t corearchLoongarch64CrcWBW(int32_t a, int32_t b);
int32_t corearchLoongarch64CrcWHW(int32_t a, int32_t b);
int32_t corearchLoongarch64CrcWWW(int32_t a, int32_t b);
int32_t corearchLoongarch64CrcWDW(int64_t a, int32_t b);
int32_t corearchLoongarch64CrccWBW(int32_t a, int32_t b);
int32_t corearchLoongarch64CrccWHW(int32_t a, int32_t b);
int32_t corearchLoongarch64CrccWWW(int32_t a, int32_t b);
int32_t corearchLoongarch64CrccWDW(int64_t a, int32_t b);
void corearchLoongarch64Cacop(int64_t a, int64_t b, int64_t c);
int64_t corearchLoongarch64Csrrd(int32_t a);
int64_t corearchLoongarch64Csrwr(int64_t a, int32_t b);
int64_t corearchLoongarch64Csrxchg(int64_t a, int64_t b, int32_t c);
int64_t corearchLoongarch64IocsrrdD(int32_t a);
void corearchLoongarch64IocsrwrD(int64_t a, int32_t b);
void corearchLoongarch64Asrtle(int64_t a, int64_t b);
void corearchLoongarch64Asrtgt(int64_t a, int64_t b);
int64_t corearchLoongarch64Lddir(int64_t a, int64_t b);
void corearchLoongarch64Ldpte(int64_t a, int64_t b);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_LOONGARCH64_LOONGARCH64_WRAPPER_H */
