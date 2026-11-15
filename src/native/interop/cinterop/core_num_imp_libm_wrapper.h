#ifndef RUSTLANG_CORE_NUM_IMP_LIBM_CORE_NUM_IMP_LIBM_WRAPPER_H
#define RUSTLANG_CORE_NUM_IMP_LIBM_CORE_NUM_IMP_LIBM_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

double rustlangCoreNumImpLibmPowidf2(double a, int32_t b);
float rustlangCoreNumImpLibmPowisf2(float a, int32_t b);
double rustlangCoreNumImpLibmCbrt(double x);
float rustlangCoreNumImpLibmCbrtf(float n);
double rustlangCoreNumImpLibmCeil(double x);
float rustlangCoreNumImpLibmCeilf(float x);
double rustlangCoreNumImpLibmCopysign(double x, double y);
float rustlangCoreNumImpLibmCopysignf(float x, float y);
double rustlangCoreNumImpLibmFabs(double x);
float rustlangCoreNumImpLibmFabsf(float x);
double rustlangCoreNumImpLibmFdim(double a, double b);
float rustlangCoreNumImpLibmFdimf(float a, float b);
double rustlangCoreNumImpLibmFloor(double x);
float rustlangCoreNumImpLibmFloorf(float x);
double rustlangCoreNumImpLibmFma(double x, double y, double z);
float rustlangCoreNumImpLibmFmaf(float x, float y, float z);
double rustlangCoreNumImpLibmFmax(double x, double y);
float rustlangCoreNumImpLibmFmaxf(float x, float y);
double rustlangCoreNumImpLibmFmaximum(double x, double y);
float rustlangCoreNumImpLibmFmaximumf(float x, float y);
double rustlangCoreNumImpLibmFmin(double x, double y);
float rustlangCoreNumImpLibmFminf(float x, float y);
double rustlangCoreNumImpLibmFminimum(double x, double y);
float rustlangCoreNumImpLibmFminimumf(float x, float y);
double rustlangCoreNumImpLibmFmod(double x, double y);
float rustlangCoreNumImpLibmFmodf(float x, float y);
double rustlangCoreNumImpLibmRint(double x);
float rustlangCoreNumImpLibmRintf(float x);
double rustlangCoreNumImpLibmRound(double x);
double rustlangCoreNumImpLibmRoundeven(double x);
float rustlangCoreNumImpLibmRoundevenf(float x);
float rustlangCoreNumImpLibmRoundf(float x);
double rustlangCoreNumImpLibmSqrt(double x);
float rustlangCoreNumImpLibmSqrtf(float x);
double rustlangCoreNumImpLibmTrunc(double x);
float rustlangCoreNumImpLibmTruncf(float x);
float rustlangCoreNumImpLibmAcosf(float n);
float rustlangCoreNumImpLibmAsinf(float n);
float rustlangCoreNumImpLibmAtan2f(float a, float b);
float rustlangCoreNumImpLibmAtanf(float n);
float rustlangCoreNumImpLibmCosf(float x);
float rustlangCoreNumImpLibmCoshf(float n);
float rustlangCoreNumImpLibmErfcf(float x);
float rustlangCoreNumImpLibmErff(float x);
float rustlangCoreNumImpLibmExp2f(float x);
float rustlangCoreNumImpLibmExpf(float x);
float rustlangCoreNumImpLibmExpm1f(float n);
float rustlangCoreNumImpLibmHypotf(float x, float y);
float rustlangCoreNumImpLibmLdexpf(float f, int32_t n);
float rustlangCoreNumImpLibmLog10f(float x);
float rustlangCoreNumImpLibmLog1pf(float n);
float rustlangCoreNumImpLibmLog2f(float x);
float rustlangCoreNumImpLibmLogf(float x);
float rustlangCoreNumImpLibmPowf(float x, float y);
float rustlangCoreNumImpLibmSinf(float x);
float rustlangCoreNumImpLibmSinhf(float n);
float rustlangCoreNumImpLibmTanf(float n);
float rustlangCoreNumImpLibmTanhf(float n);
float rustlangCoreNumImpLibmTgammaf(float x);
double rustlangCoreNumImpLibmAcos(double x);
double rustlangCoreNumImpLibmAsin(double x);
double rustlangCoreNumImpLibmAtan(double x);
double rustlangCoreNumImpLibmAtan2(double x, double y);
double rustlangCoreNumImpLibmCos(double x);
double rustlangCoreNumImpLibmCosh(double x);
double rustlangCoreNumImpLibmErf(double x);
double rustlangCoreNumImpLibmErfc(double x);
double rustlangCoreNumImpLibmExp(double x);
double rustlangCoreNumImpLibmExp2(double x);
double rustlangCoreNumImpLibmExpm1(double x);
double rustlangCoreNumImpLibmHypot(double x, double y);
double rustlangCoreNumImpLibmLdexp(double f, int32_t n);
double rustlangCoreNumImpLibmLog(double x);
double rustlangCoreNumImpLibmLog10(double x);
double rustlangCoreNumImpLibmLog1p(double x);
double rustlangCoreNumImpLibmLog2(double x);
double rustlangCoreNumImpLibmPow(double x, double y);
double rustlangCoreNumImpLibmSin(double x);
double rustlangCoreNumImpLibmSinh(double x);
double rustlangCoreNumImpLibmTan(double x);
double rustlangCoreNumImpLibmTanh(double x);
double rustlangCoreNumImpLibmTgamma(double x);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_CORE_NUM_IMP_LIBM_CORE_NUM_IMP_LIBM_WRAPPER_H */
