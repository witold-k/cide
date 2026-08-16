SUMMARY = "libtermkey"
LICENSE = "CLOSED"
MODULE = "base/libtermkey"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses unibilium")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


NOWARN = ' \
    -Wno-implicit-fallthrough \
    -Wno-unused-parameter \
    -Wno-sign-compare \
'

python do_patch() {
    import patcher
    import fileutils

    s = d.getVar('S')
    b = d.getVar('B')
    fd = d.getVar('FILE_DIRNAME')

    fileutils.copy_file(fd + '/files/CMakeLists.txt', s + '/CMakeLists.txt')

    p = patcher.Patcher(s + '/termkey.h.in', None)
    p.replace('@@', '@')
    p.writeTo(b + '/include/termkey.h.in')
}

