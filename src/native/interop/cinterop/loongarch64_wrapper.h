#ifndef COREARCH_LOONGARCH64_LOONGARCH64_WRAPPER_H
#define COREARCH_LOONGARCH64_LOONGARCH64_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t corearchLoongarch64ExternCrcWBW(int32_t a, int32_t b);
int32_t corearchLoongarch64ExternCrcWHW(int32_t a, int32_t b);
int32_t corearchLoongarch64ExternCrcWWW(int32_t a, int32_t b);
int32_t corearchLoongarch64ExternCrcWDW(int64_t a, int32_t b);
int32_t corearchLoongarch64ExternCrccWBW(int32_t a, int32_t b);
int32_t corearchLoongarch64ExternCrccWHW(int32_t a, int32_t b);
int32_t corearchLoongarch64ExternCrccWWW(int32_t a, int32_t b);
int32_t corearchLoongarch64ExternCrccWDW(int64_t a, int32_t b);
void corearchLoongarch64Cacop(int64_t a, int64_t b, int64_t c);
int64_t corearchLoongarch64Csrrd(int32_t a);
int64_t corearchLoongarch64Csrwr(int64_t a, int32_t b);
int64_t corearchLoongarch64Csrxchg(int64_t a, int64_t b, int32_t c);
int64_t corearchLoongarch64ExternIocsrrdD(int32_t a);
void corearchLoongarch64ExternIocsrwrD(int64_t a, int32_t b);
void corearchLoongarch64ExternAsrtle(int64_t a, int64_t b);
void corearchLoongarch64ExternAsrtgt(int64_t a, int64_t b);
int64_t corearchLoongarch64Lddir(int64_t a, int64_t b);
void corearchLoongarch64Ldpte(int64_t a, int64_t b);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_LOONGARCH64_LOONGARCH64_WRAPPER_H */
