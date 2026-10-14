SUMMARY = "getgenopt"
LICENSE = "CLOSED"
MODULE = "base/gengetopt"
SRCPV = "1"

inherit simpleconfigure encapsulate

SKIP_BUILD = "True"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "gengen")
    BD.native_depends(d, "gengetopt")
}
do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

GENGEN_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/gengen${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
GENGEN_BUILD_DIR          = "${TOPDIR}/tmp/work/gengen${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
INSTALLED_GENGEN          = "${GENGEN_NATIVE_INSTALL_DIR}/bin"

GENGETOPT_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/gengetopt${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
GENGETOPT_BUILD_DIR          = "${TOPDIR}/tmp/work/gengetopt${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
BUILD_GENGETOPT              = "${GENGETOPT_BUILD_DIR}/src"
INSTALLED_GENGETOPT          = "${GENGETOPT_NATIVE_INSTALL_DIR}/bin"


CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_OPTIONS = "--with-gengen=${INSTALLED_GENGEN}/gengen"

#CONFIGURE_COMMON   = "${DEPEND_INCLUDE_DIRS_SWITCH}"
#CONFIGURE_CFLAGS   = "${CONFIGURE_COMMON}"
#CONFIGURE_CXXFLAGS = "${CONFIGURE_COMMON}"
#CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

python do_patch() {
    import patcher

    s = d.getVar('S')
    b = d.getVar('B')
    md = d.getVar('MODULE_DIR')

    ggbd = d.getVar('GENGETOPT_BUILD_DIR')
    bds  = ggbd + '/src'

    tests_dir = ggbd + '/tests'
    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'

    p = patcher.Patcher(s + '/doc/Makefile.am', None)
    p.replace( \
        'NEW_GENGETOPT = $(top_builddir)/src', \
        'NEW_GENGETOPT = ' + bds \
    )
    p.write()

    p = patcher.Patcher(s + '/tests/Makefile.am', None)
    p.replace( \
        'NEW_GENGETOPT = $(top_builddir)/src', \
        'NEW_GENGETOPT = ' + bds \
    )
    p.write()


    bb.build.exec_func('do_autotools_autoreconf', d)
}

