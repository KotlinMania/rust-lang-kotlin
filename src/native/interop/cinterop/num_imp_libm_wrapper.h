#ifndef CORE_NUM_IMP_LIBM_NUM_IMP_LIBM_WRAPPER_H
#define CORE_NUM_IMP_LIBM_NUM_IMP_LIBM_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

double coreNumImpLibmPowidf2(double a, int32_t b);
float coreNumImpLibmPowisf2(float a, int32_t b);
double coreNumImpLibmCbrt(double x);
float coreNumImpLibmCbrtf(float n);
double coreNumImpLibmCeil(double x);
float coreNumImpLibmCeilf(float x);
double coreNumImpLibmCopysign(double x, double y);
float coreNumImpLibmCopysignf(float x, float y);
double coreNumImpLibmFabs(double x);
float coreNumImpLibmFabsf(float x);
double coreNumImpLibmFdim(double a, double b);
float coreNumImpLibmFdimf(float a, float b);
double coreNumImpLibmFloor(double x);
float coreNumImpLibmFloorf(float x);
double coreNumImpLibmFma(double x, double y, double z);
float coreNumImpLibmFmaf(float x, float y, float z);
double coreNumImpLibmFmax(double x, double y);
float coreNumImpLibmFmaxf(float x, float y);
double coreNumImpLibmFmaximum(double x, double y);
float coreNumImpLibmFmaximumf(float x, float y);
double coreNumImpLibmFmin(double x, double y);
float coreNumImpLibmFminf(float x, float y);
double coreNumImpLibmFminimum(double x, double y);
float coreNumImpLibmFminimumf(float x, float y);
double coreNumImpLibmFmod(double x, double y);
float coreNumImpLibmFmodf(float x, float y);
double coreNumImpLibmRint(double x);
float coreNumImpLibmRintf(float x);
double coreNumImpLibmRound(double x);
double coreNumImpLibmRoundeven(double x);
float coreNumImpLibmRoundevenf(float x);
float coreNumImpLibmRoundf(float x);
double coreNumImpLibmSqrt(double x);
float coreNumImpLibmSqrtf(float x);
double coreNumImpLibmTrunc(double x);
float coreNumImpLibmTruncf(float x);
double coreNumImpLibmAcos(double x);
double coreNumImpLibmAsin(double x);
double coreNumImpLibmAtan(double x);
double coreNumImpLibmAtan2(double x, double y);
double coreNumImpLibmCos(double x);
double coreNumImpLibmCosh(double x);
double coreNumImpLibmErf(double x);
double coreNumImpLibmErfc(double x);
double coreNumImpLibmExp(double x);
double coreNumImpLibmExp2(double x);
double coreNumImpLibmExpm1(double x);
double coreNumImpLibmHypot(double x, double y);
double coreNumImpLibmLdexp(double f, int32_t n);
double coreNumImpLibmLog(double x);
double coreNumImpLibmLog10(double x);
double coreNumImpLibmLog1p(double x);
double coreNumImpLibmLog2(double x);
double coreNumImpLibmPow(double x, double y);
double coreNumImpLibmSin(double x);
double coreNumImpLibmSinh(double x);
double coreNumImpLibmTan(double x);
double coreNumImpLibmTanh(double x);
double coreNumImpLibmTgamma(double x);

#ifdef __cplusplus
}
#endif

#endif /* CORE_NUM_IMP_LIBM_NUM_IMP_LIBM_WRAPPER_H */
