S1UMMARY = "libtasn1"
LICENSE = "CLOSED"
MODULE = "base/libtasn1"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, "libtasn1")
    BD.depends(d, "glib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

TASN1_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/libtasn1${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
TASN1_BUILD_DIR          = "${TOPDIR}/tmp/work/libtasn1${CURRENT_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
BUILD_ASN1               = "${TASN1_BUILD_DIR}/src"
INSTALLED_ASN1           = "${TASN1_NATIVE_INSTALL_DIR}/bin"

NOWARN = " \
    -Wno-analyzer-null-dereference \
    -Wno-analyzer-use-of-uninitialized-value \
"

python do_patch() {
    import fileutils
    import patcher

    s = d.getVar('S')
    md = d.getVar('MODULE_DIR')

    p = patcher.Patcher('tests/Makefile.am', None)
    p.replace('$(top_builddir)/src', '$(ASN1_BIN_DIR)')
    p.write()

    p = patcher.Patcher('doc/Makefile.am', None)
    p.replace('$(AM_V_GEN)$(top_builddir)/src', '$(ASN1_BIN_DIR)')
    p.replace('$(top_builddir)/src/asn1', '$(ASN1_BIN_DIR)/asn1')
    p.write()

    p = patcher.Patcher('examples/Makefile.am', None)
    p.replace('../src/asn1', '$(ASN1_BIN_DIR)/asn1')
    p.write()

    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'
    bb.build.exec_func('do_autotools_bootstrap', d)
}

python do_compile:prepend() {
    if d.getVar('IS_NATIVE') != True:
        p = d.getVar('INSTALLED_ASN1')
        d.setVar('CONFIGURE_MAKE_OPTIONS', 'ASN1_BIN_DIR=' + p)
    else:
        p = d.getVar('BUILD_ASN1')
        d.setVar('CONFIGURE_MAKE_OPTIONS', 'ASN1_BIN_DIR=' + p)
}

python do_compile:prepend() {
    if d.getVar('IS_NATIVE') != True:
        p = d.getVar('INSTALLED_ASN1')
        os.environ['PARSER'] = p + '/asn1Parser'
}

