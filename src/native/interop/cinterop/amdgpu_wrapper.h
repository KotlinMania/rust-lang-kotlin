#ifndef COREARCH_AMDGPU_AMDGPU_WRAPPER_H
#define COREARCH_AMDGPU_AMDGPU_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint32_t corearchAmdgpuLlvmWorkitemIdX(void);
uint32_t corearchAmdgpuLlvmWorkitemIdY(void);
uint32_t corearchAmdgpuLlvmWorkitemIdZ(void);
uint32_t corearchAmdgpuLlvmWorkgroupIdX(void);
uint32_t corearchAmdgpuLlvmWorkgroupIdY(void);
uint32_t corearchAmdgpuLlvmWorkgroupIdZ(void);
uint32_t corearchAmdgpuLlvmGroupstaticsize(void);
uint64_t corearchAmdgpuLlvmDispatchId(void);
uint32_t corearchAmdgpuLlvmWavefrontsize(void);
void corearchAmdgpuLlvmSBarrier(void);
void corearchAmdgpuLlvmSBarrierSignal(int32_t barrierType);
bool corearchAmdgpuLlvmSBarrierSignalIsfirst(int32_t barrierType);
void corearchAmdgpuLlvmSBarrierWait(int16_t barrierType);
uint32_t corearchAmdgpuLlvmSGetBarrierState(int32_t barrierType);
void corearchAmdgpuLlvmWaveBarrier(void);
void corearchAmdgpuLlvmSchedBarrier(uint32_t mask);
void corearchAmdgpuLlvmSchedGroupBarrier(uint32_t mask, uint32_t size, uint32_t syncId);
void corearchAmdgpuLlvmSSleep(uint32_t count);
void corearchAmdgpuLlvmSSethalt(uint32_t value);
int64_t corearchAmdgpuLlvmSGetpc(void);
uint32_t corearchAmdgpuLlvmMbcntLo(uint32_t value, uint32_t init);
uint32_t corearchAmdgpuLlvmMbcntHi(uint32_t value, uint32_t init);
uint64_t corearchAmdgpuLlvmBallot(bool b);
bool corearchAmdgpuLlvmInverseBallot(uint64_t value);
uint32_t corearchAmdgpuLlvmWaveReduceUmin(uint32_t value, uint32_t strategy);
int32_t corearchAmdgpuLlvmWaveReduceMin(int32_t value, uint32_t strategy);
uint32_t corearchAmdgpuLlvmWaveReduceUmax(uint32_t value, uint32_t strategy);
int32_t corearchAmdgpuLlvmWaveReduceMax(int32_t value, uint32_t strategy);
uint32_t corearchAmdgpuLlvmWaveReduceAdd(uint32_t value, uint32_t strategy);
uint32_t corearchAmdgpuLlvmWaveReduceAnd(uint32_t value, uint32_t strategy);
uint32_t corearchAmdgpuLlvmWaveReduceOr(uint32_t value, uint32_t strategy);
uint32_t corearchAmdgpuLlvmWaveReduceXor(uint32_t value, uint32_t strategy);
uint32_t corearchAmdgpuLlvmReadfirstlaneU32(uint32_t value);
uint64_t corearchAmdgpuLlvmReadfirstlaneU64(uint64_t value);
uint32_t corearchAmdgpuLlvmReadlaneU32(uint32_t value, uint32_t lane);
uint64_t corearchAmdgpuLlvmReadlaneU64(uint64_t value, uint32_t lane);
uint32_t corearchAmdgpuLlvmWritelaneU32(uint32_t value, uint32_t lane, uint32_t arg2Default);
uint64_t corearchAmdgpuLlvmWritelaneU64(uint64_t value, uint32_t lane, uint64_t arg2Default);
void corearchAmdgpuLlvmEndpgm(void);
uint32_t corearchAmdgpuLlvmUpdateDpp(uint32_t old, uint32_t src, uint32_t dppCtrl, uint32_t rowMask, uint32_t bankMask, bool boundControl);
uint64_t corearchAmdgpuLlvmSMemrealtime(void);
uint32_t corearchAmdgpuLlvmDsPermute(uint32_t lane, uint32_t value);
uint32_t corearchAmdgpuLlvmDsBpermute(uint32_t lane, uint32_t value);
uint32_t corearchAmdgpuLlvmPerm(uint32_t src0, uint32_t src1, uint32_t selector);
uint32_t corearchAmdgpuLlvmPermlane16U32(uint32_t old, uint32_t src0, uint32_t src1, uint32_t src2, bool fi, bool boundControl);
uint32_t corearchAmdgpuLlvmPermlanex16U32(uint32_t old, uint32_t src0, uint32_t src1, uint32_t src2, bool fi, bool boundControl);
uint32_t corearchAmdgpuLlvmSGetWaveidInWorkgroup(void);
uint32_t corearchAmdgpuLlvmPermlane64U32(uint32_t value);
uint32_t corearchAmdgpuLlvmPermlane16Var(uint32_t old, uint32_t src0, uint32_t src1, bool fi, bool boundControl);
uint32_t corearchAmdgpuLlvmPermlanex16Var(uint32_t old, uint32_t src0, uint32_t src1, bool fi, bool boundControl);
uint32_t corearchAmdgpuLlvmWaveId(void);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_AMDGPU_AMDGPU_WRAPPER_H */
