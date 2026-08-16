SUMMARY = "libmpack"
LICENSE = "CLOSED"
MODULE = "base/libmpack"
SRCPV = "1"

# simplemake encapsulate
#
# do_check_need_build[depends] += "msgpack-c:do_check_populate"

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/src/core.h', None)
    p.insert_after('#include <stddef.h>', '#include <stdbool.h>')
    p.write()
}

