#include "std_sys_exit_wrapper.h"
#include <errno.h>
#include <stdlib.h>

void rustlangStdSysExitExit(int32_t status) {
    exit(status);
}

