#ifndef STD_SYS_CMATH_SYS_CMATH_WRAPPER_H
#define STD_SYS_CMATH_SYS_CMATH_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

double stdSysCmathAcos(double n);
double stdSysCmathAcosh(double n);
double stdSysCmathAsin(double n);
double stdSysCmathAsinh(double n);
double stdSysCmathAtan(double n);
double stdSysCmathAtan2(double a, double b);
double stdSysCmathCosh(double n);
double stdSysCmathExpm1(double n);
float stdSysCmathExpm1f(float n);
double stdSysCmathHypot(double x, double y);
float stdSysCmathHypotf(float x, float y);
double stdSysCmathLog1p(double n);
float stdSysCmathLog1pf(float n);
double stdSysCmathSinh(double n);
double stdSysCmathTan(double n);
double stdSysCmathTanh(double n);
double stdSysCmathTgamma(double n);
float stdSysCmathTgammaf(float n);
double stdSysCmathErf(double n);
float stdSysCmathErff(float n);
double stdSysCmathErfc(double n);
float stdSysCmathErfcf(float n);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_CMATH_SYS_CMATH_WRAPPER_H */
