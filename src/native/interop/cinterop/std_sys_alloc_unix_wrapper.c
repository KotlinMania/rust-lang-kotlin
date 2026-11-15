#include "std_sys_alloc_unix_wrapper.h"
#include <errno.h>
#include <stdlib.h>

void rustlangStdSysAllocUnixFree(void* ptr) {
    free(ptr);
}

