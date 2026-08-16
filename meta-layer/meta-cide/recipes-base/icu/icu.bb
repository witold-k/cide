SUMMARY = "icu"
LICENSE = "CLOSED"
MODULE = "base/icu/icu4c/source"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, "icu")
}
do_check_need_build[depends] += "${NATIVE_DEPEND_TARGETS}"

CONFIGURE_CFLAGS = ""
CONFIGURE_CXXFLAGS = "${CONFIGURE_CFLAGS}"
CONFIGURE_LDFLAGS = ""

ICU_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/icu${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
ICU_BUILD_DIR          = "${TOPDIR}/tmp/work/icu${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"

python do_patch() {
    bb.build.exec_func('do_autotools_autoreconf', d)
}

python do_configure:prepend() {
    if d.getVar('IS_NATIVE') != True:
        bd = d.getVar('ICU_BUILD_DIR')
        d.setVar('CONFIGURE_OPTIONS', '--with-cross-build=' + bd)
}

