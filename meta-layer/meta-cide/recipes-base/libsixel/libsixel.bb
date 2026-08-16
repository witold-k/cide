SUMMARY = "libsixel"
LICENSE = "CLOSED"
MODULE = "base/libsixel"
SRCPV = "1"

inherit simpleconfigure encapsulate

#
# !!!! ATTENTION !!!!
#
# removed ../libunistring.la and other
# to make this build running
#

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libpng zstd zlib openssl libidn2 libjpeg brotli libpsl libunistring")
}

do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

python do_patch() {
    bb.warn("checkout: https://www.arewesixelyet.com")
    bb.build.exec_func('do_autotools_aclocal', d)
}

NOWARN = "\
    -Wno-implicit-function-declaration \
    -Wno-builtin-declaration-mismatch \
"

CONFIGURE_CFLAGS   = ""
CONFIGURE_CXXFLAGS = ""
CONFIGURE_LDFLAGS  = "-Wl,-rpath=${LIB_ZLIB_DIR} -Wl,-rpath-link=${LIB_ZLIB_DIR} ${DEPEND_LIB_DIRS_SWITCH}"

CONFIGURE_OPTIONS = "--disable-python"

