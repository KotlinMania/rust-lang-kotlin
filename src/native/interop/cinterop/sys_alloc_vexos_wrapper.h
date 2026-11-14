#ifndef STD_SYS_ALLOC_VEXOS_SYS_ALLOC_VEXOS_WRAPPER_H
#define STD_SYS_ALLOC_VEXOS_SYS_ALLOC_VEXOS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint8_t stdSysAllocVexosGetHEAPSTART(void);
void stdSysAllocVexosSetHEAPSTART(uint8_t km_val);
uint8_t stdSysAllocVexosGetHEAPEND(void);
void stdSysAllocVexosSetHEAPEND(uint8_t km_val);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_ALLOC_VEXOS_SYS_ALLOC_VEXOS_WRAPPER_H */
