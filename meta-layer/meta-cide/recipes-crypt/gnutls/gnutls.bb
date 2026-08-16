inherit simpleconfigure encapsulate

SUMMARY = "gnutls"
LICENSE = "CLOSED"
MODULE = "crypt/gnutls"
SRCPV = "1"

SKIP_BUILD = 'True'

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "gmp brotli libidn2 libev4 libnettle libtasn1 libunistring p11-kit zlib zstd")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

TASN1_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/libtasn1${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

NOWARN = " \
    -Wno-analyzer-fd-leak \
    -Wno-analyzer-fd-use-without-check \
    -Wno-analyzer-use-of-uninitialized-value \
    -Wno-cast-function-type \
    -Wno-int-conversion \
    -Wno-missing-include-dirs \
    -Wno-nested-externs \
    -Wno-shadow \
    -Wno-stringop-overflow \
    -Wno-type-limits \
    -Wno-unterminated-string-initialization \
    -Wno-unused-variable \
"

CONFIGURE_OPTIONS = "--with-included-unistring --disable-nls --without-idn"

CONFIGURE_CFLAGS   = ""
CONFIGURE_CXXFLAGS = ""
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

CONFIGURE_MAKE_OPTIONS = "ASN1PARSER=${TASN1_NATIVE_INSTALL_DIR}/bin/asn1Parser"

#
# need patched (add sha.h) file from nettle/files
#
python do_patch() {
    import fileutils, patcher, ripgrep

    md = d.getVar('MODULE_DIR')
    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'

    s = d.getVar('S')

    cmd = '--no-git --skip-po --gnulib-srcdir=' + md + '/base/gnulib'
    d.setVar('CONFIGURE_BOOTSTRAP_OPTIONS', cmd)
    bb.build.exec_func('do_autotools_bootstrap', d)
}

python do_configure:prepend() {
    from bbdepend import BBDepend as BD
    bd = BD.createAll(d, "gmp brotli libidn2 libev4 libnettle libtasn1 libunistring p11-kit zlib zstd")

    bd.setEnvLib('GMP_LIBS', 'gmp', 'libgmp.so')
    bd.setEnvLib('HOGWEED_LIBS', 'libnettle', 'libhogweed.so')
    bd.setEnvLib('LIBBROTLIDEC_LIBS', 'brotli', 'libbrotlidec.so')
    bd.setEnvLib('LIBBROTLIENC_LIBS', 'brotli', 'libbrotlienc.so')
    bd.setEnvLib('LIBEV_LIBS', 'libev4', 'libev.so')
    bd.setEnvLib('LIBIDN2_LIBS', 'libidn2', 'libidn2.so')
    bd.setEnvLib('LIBTASN1_LIBS','libtasn1', 'libtasn1.so')
    bd.setEnvLib('LIBUNISTRING', 'libunistring', 'libunsistring.so')
    #bd.setEnvLib('LIBZSTD_LIBS', 'zstd', 'libzstd.so')
    bd.setEnvLib('NETTLE_LIBS', 'libnettle', 'libnettle.so')
    bd.setEnvLib('P11_KIT_LIBS', 'p11-kit', 'libp11-kit.so')
    bd.setEnvLib('ZLIB_LIBS', 'zlib', 'libz.so')

    md = d.getVar('MODULE_DIR')
    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'
}

