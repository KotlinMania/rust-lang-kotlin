#include "sys_alloc_unix_wrapper.h"
#include <errno.h>
#include <stdlib.h>

void stdSysAllocUnixFree(void* ptr) {
    free(ptr);
}

