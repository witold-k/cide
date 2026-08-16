SUMMARY = "harfbuzz"
LICENSE = "CLOSED"
MODULE = "graphics/harfbuzz"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "cairo fontconfig freetype glib libffi pixman libexpat brotli zlib libpng libjpeg libxml2 liblzma bzip2")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = '\
'

MESON_C_FLAGS   = ''
MESON_CXX_FLAGS = ''
MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

MESON_OPTIONS = " \
    -Ddocs=disabled \
    -Dicu=disabled \
"
