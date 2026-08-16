SUMMARY = "pixman"
LICENSE = "CLOSED"
MODULE = "graphics/pixman"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libpng zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = " \
-Wno-attributes \
-Wno-maybe-uninitialized \
"

MESON_C_FLAGS    = "${DEPEND_INCLUDE_DIRS_SWITCH}"
MESON_CXX_FLAGS  = "${DEPEND_INCLUDE_DIRS_SWITCH}"
MESON_LDFLAGS    = "${DEPEND_LIB_DIRS_SWITCH}"
