#ifndef COREARCH_LOONGARCHSHARED_LOONGARCHSHARED_WRAPPER_H
#define COREARCH_LOONGARCHSHARED_LOONGARCHSHARED_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchLoongarchsharedDbar(int32_t a);
void corearchLoongarchsharedIbar(int32_t a);
void corearchLoongarchsharedMovgr2fcsr(int32_t a, int32_t b);
int32_t corearchLoongarchsharedMovfcsr2gr(int32_t a);
int32_t corearchLoongarchsharedIocsrrdB(int32_t a);
int32_t corearchLoongarchsharedIocsrrdH(int32_t a);
int32_t corearchLoongarchsharedIocsrrdW(int32_t a);
void corearchLoongarchsharedIocsrwrB(int32_t a, int32_t b);
void corearchLoongarchsharedIocsrwrH(int32_t a, int32_t b);
void corearchLoongarchsharedIocsrwrW(int32_t a, int32_t b);
void corearchLoongarchsharedBreakTarget(int32_t a);
int32_t corearchLoongarchsharedCpucfg(int32_t a);
void corearchLoongarchsharedSyscall(int32_t a);
float corearchLoongarchsharedFrecipeS(float a);
double corearchLoongarchsharedFrecipeD(double a);
float corearchLoongarchsharedFrsqrteS(float a);
double corearchLoongarchsharedFrsqrteD(double a);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_LOONGARCHSHARED_LOONGARCHSHARED_WRAPPER_H */
