#ifndef STD_SYS_PAL_ITRON_ABI_SYS_PAL_ITRON_ABI_WRAPPER_H
#define STD_SYS_PAL_ITRON_ABI_SYS_PAL_ITRON_ABI_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t stdSysPalItronAbiAcreTsk(void* pkCtsk);
int32_t stdSysPalItronAbiGetTid(void* pTskid);
int32_t stdSysPalItronAbiDlyTsk(uint32_t dlytim);
int32_t stdSysPalItronAbiTerTsk(int32_t tskid);
int32_t stdSysPalItronAbiDelTsk(int32_t tskid);
int32_t stdSysPalItronAbiGetPri(int32_t tskid, void* pTskpri);
int32_t stdSysPalItronAbiRotRdq(int32_t tskpri);
int32_t stdSysPalItronAbiSlpTsk(void);
int32_t stdSysPalItronAbiTslpTsk(uint32_t tmout);
int32_t stdSysPalItronAbiWupTsk(int32_t tskid);
int32_t stdSysPalItronAbiUnlCpu(void);
int32_t stdSysPalItronAbiDisDsp(void);
int32_t stdSysPalItronAbiEnaDsp(void);
int32_t stdSysPalItronAbiSnsDsp(void);
int32_t stdSysPalItronAbiGetTim(void* pSystim);
int32_t stdSysPalItronAbiAcreFlg(void* pkCflg);
int32_t stdSysPalItronAbiDelFlg(int32_t flgid);
int32_t stdSysPalItronAbiSetFlg(int32_t flgid, uint32_t setptn);
int32_t stdSysPalItronAbiClrFlg(int32_t flgid, uint32_t clrptn);
int32_t stdSysPalItronAbiWaiFlg(int32_t flgid, uint32_t waiptn, uint32_t wfmode, void* pFlgptn);
int32_t stdSysPalItronAbiTwaiFlg(int32_t flgid, uint32_t waiptn, uint32_t wfmode, void* pFlgptn, uint32_t tmout);
int32_t stdSysPalItronAbiAcreMtx(void* pkCmtx);
int32_t stdSysPalItronAbiDelMtx(int32_t tskid);
int32_t stdSysPalItronAbiLocMtx(int32_t mtxid);
int32_t stdSysPalItronAbiPlocMtx(int32_t mtxid);
int32_t stdSysPalItronAbiTlocMtx(int32_t mtxid, uint32_t tmout);
int32_t stdSysPalItronAbiUnlMtx(int32_t mtxid);
int32_t stdSysPalItronAbiExdTsk(void);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_PAL_ITRON_ABI_SYS_PAL_ITRON_ABI_WRAPPER_H */
