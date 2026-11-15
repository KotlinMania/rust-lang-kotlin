#ifndef RUSTLANG_COREARCH_LOONGARCHSHARED_COREARCH_LOONGARCHSHARED_WRAPPER_H
#define RUSTLANG_COREARCH_LOONGARCHSHARED_COREARCH_LOONGARCHSHARED_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchLoongarchsharedDbar(int32_t a);
void rustlangCorearchLoongarchsharedIbar(int32_t a);
void rustlangCorearchLoongarchsharedMovgr2fcsr(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarchsharedMovfcsr2gr(int32_t a);
int32_t rustlangCorearchLoongarchsharedIocsrrdB(int32_t a);
int32_t rustlangCorearchLoongarchsharedIocsrrdH(int32_t a);
int32_t rustlangCorearchLoongarchsharedIocsrrdW(int32_t a);
void rustlangCorearchLoongarchsharedIocsrwrB(int32_t a, int32_t b);
void rustlangCorearchLoongarchsharedIocsrwrH(int32_t a, int32_t b);
void rustlangCorearchLoongarchsharedIocsrwrW(int32_t a, int32_t b);
void rustlangCorearchLoongarchsharedBreakTarget(int32_t a);
int32_t rustlangCorearchLoongarchsharedCpucfg(int32_t a);
void rustlangCorearchLoongarchsharedSyscall(int32_t a);
float rustlangCorearchLoongarchsharedFrecipeS(float a);
double rustlangCorearchLoongarchsharedFrecipeD(double a);
float rustlangCorearchLoongarchsharedFrsqrteS(float a);
double rustlangCorearchLoongarchsharedFrsqrteD(double a);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_LOONGARCHSHARED_COREARCH_LOONGARCHSHARED_WRAPPER_H */
