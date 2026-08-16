SUMMARY = "pango"
LICENSE = "CLOSED"
MODULE = "graphics/pango"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, " \
        glib  \
        cairo fontconfig freetype pixman  \
        fribidi harfbuzz \
        brotli libexpat libxml2 bzip2 libjpeg libpng liblzma zlib libffi \
    ")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = ' \
'

MESON_C_FLAGS   = ""
MESON_CXX_FLAGS = ""
MESON_LDFLAGS   = "${DEPEND_LIB_DIRS_SWITCH}"

MESON_OPTIONS = " \
    -Dfontconfig=enabled \
    -Dcairo=enabled \
    -Dxft=disabled \
    -Dfreetype=enabled \
"
