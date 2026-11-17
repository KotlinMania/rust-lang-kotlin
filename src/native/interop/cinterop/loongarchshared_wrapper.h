#ifndef COREARCH_LOONGARCHSHARED_LOONGARCHSHARED_WRAPPER_H
#define COREARCH_LOONGARCHSHARED_LOONGARCHSHARED_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchLoongarchsharedExternDbar(int32_t a);
void corearchLoongarchsharedExternIbar(int32_t a);
void corearchLoongarchsharedExternMovgr2fcsr(int32_t a, int32_t b);
int32_t corearchLoongarchsharedExternMovfcsr2gr(int32_t a);
int32_t corearchLoongarchsharedExternIocsrrdB(int32_t a);
int32_t corearchLoongarchsharedExternIocsrrdH(int32_t a);
int32_t corearchLoongarchsharedExternIocsrrdW(int32_t a);
void corearchLoongarchsharedExternIocsrwrB(int32_t a, int32_t b);
void corearchLoongarchsharedExternIocsrwrH(int32_t a, int32_t b);
void corearchLoongarchsharedExternIocsrwrW(int32_t a, int32_t b);
void corearchLoongarchsharedBreakTarget(int32_t a);
int32_t corearchLoongarchsharedExternCpucfg(int32_t a);
void corearchLoongarchsharedExternSyscall(int32_t a);
float corearchLoongarchsharedExternFrecipeS(float a);
double corearchLoongarchsharedExternFrecipeD(double a);
float corearchLoongarchsharedExternFrsqrteS(float a);
double corearchLoongarchsharedExternFrsqrteD(double a);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_LOONGARCHSHARED_LOONGARCHSHARED_WRAPPER_H */
