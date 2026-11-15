#ifndef RUSTLANG_STD_SYS_CMATH_STD_SYS_CMATH_WRAPPER_H
#define RUSTLANG_STD_SYS_CMATH_STD_SYS_CMATH_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

double rustlangStdSysCmathAcos(double n);
double rustlangStdSysCmathAcosh(double n);
double rustlangStdSysCmathAsin(double n);
double rustlangStdSysCmathAsinh(double n);
double rustlangStdSysCmathAtan(double n);
double rustlangStdSysCmathAtan2(double a, double b);
double rustlangStdSysCmathCosh(double n);
double rustlangStdSysCmathExpm1(double n);
float rustlangStdSysCmathExpm1f(float n);
double rustlangStdSysCmathHypot(double x, double y);
float rustlangStdSysCmathHypotf(float x, float y);
double rustlangStdSysCmathLog1p(double n);
float rustlangStdSysCmathLog1pf(float n);
double rustlangStdSysCmathSinh(double n);
double rustlangStdSysCmathTan(double n);
double rustlangStdSysCmathTanh(double n);
double rustlangStdSysCmathTgamma(double n);
float rustlangStdSysCmathTgammaf(float n);
double rustlangStdSysCmathErf(double n);
float rustlangStdSysCmathErff(float n);
double rustlangStdSysCmathErfc(double n);
float rustlangStdSysCmathErfcf(float n);
float rustlangStdSysCmathAcosf(float n);
float rustlangStdSysCmathAcoshf(float n);
float rustlangStdSysCmathAsinf(float n);
float rustlangStdSysCmathAsinhf(float n);
float rustlangStdSysCmathAtan2f(float a, float b);
float rustlangStdSysCmathAtanf(float n);
float rustlangStdSysCmathCoshf(float n);
float rustlangStdSysCmathSinhf(float n);
float rustlangStdSysCmathTanf(float n);
float rustlangStdSysCmathTanhf(float n);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_CMATH_STD_SYS_CMATH_WRAPPER_H */
