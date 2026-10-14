SUMMARY = "libpsl"
LICENSE = "CLOSED"
MODULE = "base/libpsl"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson libidn2 libunistring")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

MESON_C_FLAGS  = "${DEPEND_INCLUDE_DIRS_SWITCH}"
MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"
NOWARN = '\
'


