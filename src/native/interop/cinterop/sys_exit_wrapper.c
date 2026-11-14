#include "sys_exit_wrapper.h"
#include <errno.h>
#include <stdlib.h>

void stdSysExitExit(int32_t status) {
    exit(status);
}

