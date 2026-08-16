#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(
    int argc,
    const char * argv[]
) {
    if (argc < 2) return 0;

    char buffer[256];
    snprintf(
        buffer, sizeof(buffer),
        "/opt/cide/natiive/wrapper/lib/" LD_LINUX_SO
        "--library-path /opt/cide/native/wrapper/lib/:/opt/cide/native/lib/lib:/opt/cide/native/lib:/opt/cide/native/lib64 "
        "--list /opt/cide/native/bin/%s",
        argv[1]
    );

    int ret = system(sys);
    free(sys);
    return ret;
}
