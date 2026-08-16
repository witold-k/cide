SUMMARY = "freetype"
LICENSE = "CLOSED"
MODULE = "graphicimage/freetype"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "brotli zlib libpng libjpeg liblzma bzip2")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = '\
'

MESON_C_FLAGS = "${DEPEND_INCLUDE_DIRS_SWITCH}"
MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"
