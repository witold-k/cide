SUMMARY = "libvips"
LICENSE = "CLOSED"
MODULE = "graphics/libvips"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "freetype fribidi libwebp pixman libexpat libpng libjpeg libffi libtiff libultrahdr libxml2 brotli bzip2 liblzma zlib zstd glib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"
MESON_LDFLAGS = "${LIB_FFI_FLAGS} ${DEPEND_LIB_DIRS_SWITCH} -lsharpyuv"

NOWARN = " \
    -Wno-unused-but-set-variable \
"

