SUMMARY = "libpsl"
LICENSE = "CLOSED"
MODULE = "base/libpsl"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libidn2 libunistring")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"
NOWARN = '\
'

MESON_C_FLAGS   = ''
MESON_CXX_FLAGS = ''

