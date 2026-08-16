SUMMARY = "cairo"
LICENSE = "CLOSED"
MODULE = "graphics/cairo"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "fontconfig freetype pixman brotli libexpat libxml2 libjpeg liblzma bzip2 libpng zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = " \
    -Wno-absolute-value \
    -Wno-calloc-transposed-args \
    -Wno-cast-function-type \
    -Wno-cpp \
    -Wno-enum-conversion \
    -Wno-format-truncation \
    -Wno-implicit-fallthrough \
    -Wno-int-in-bool-context \
    -Wno-misleading-indentation \
    -Wno-switch-enum \
    -Wno-unterminated-string-initialization \
    -Wno-unused-function \
    -Wno-unused-result \
    -Wno-unused-variable \
"

MESON_C_FLAGS    = "-std=gnu11"
MESON_CXX_FLAGS  = "-std=gnu++17"
MESON_LDFLAGS    = "${DEPEND_LIB_DIRS_SWITCH}"

MESON_OPTIONS   = " \
    -Dfreetype=enabled \
    -Dfontconfig=enabled \
    -Dxlib=disabled \
    -Dxcb=disabled \
    -Dxlib-xcb=disabled \
    -Dpng=enabled \
    -Dzlib=enabled \
"

