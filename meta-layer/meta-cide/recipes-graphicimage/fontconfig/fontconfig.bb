SUMMARY = "fontconfig"
LICENSE = "CLOSED"
MODULE = "graphicimage/fontconfig"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson freetype brotli zlib libpng libjpeg libexpat liblzma bzip2 libxml2")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = '\
    -Wno-unused-function \
    -Wno-use-after-free \
'

MESON_C_FLAGS   = ""
MESON_CXX_FLAGS = ""
MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"
