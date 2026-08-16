S1UMMARY = "libvterm"
LICENSE = "CLOSED"
MODULE = "base/libvterm"
SRCPV = "1"

inherit simplemake encapsulate

MAKE_CFLAGS = "\
    -Wno-maybe-uninitialized \
"

DEPEND_LIST = "\
    libncurses \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

