SUMMARY = "gengen"
LICENSE = "CLOSED"
MODULE = "base/gengen"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, "gengen")
}
do_check_need_build[depends] += "${NATIVE_DEPEND_TARGETS}"

GENGEN_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/gengen${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
GENGEN_BUILD_DIR          = "${TOPDIR}/tmp/work/gengen${CURRENT_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
BUILD_GENGEN              = "${GENGEN_BUILD_DIR}/src"
INSTALLED_GENGEN          = "${GENGEN_NATIVE_INSTALL_DIR}/bin"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

#CONFIGURE_COMMON   = "${DEPEND_INCLUDE_DIRS_SWITCH}"
#CONFIGURE_CFLAGS   = "${CONFIGURE_COMMON}"
#CONFIGURE_CXXFLAGS = "${CONFIGURE_COMMON}"
#CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

python do_patch() {
    import patcher

    s = d.getVar('S')
    b = d.getVar('B')
    md = d.getVar('MODULE_DIR')

    ggbd = d.getVar('GENGEN_BUILD_DIR')
    tests_dir = ggbd + '/tests'
    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'

    gengen = d.getVar('BUILD_GENGEN')
    p = patcher.Patcher(s + '/doc/Makefile.am', None)
    p.replace( \
        '$(top_builddir)/src/gengen', \
        gengen + '/gengen' \
    )
    p.replace( \
        'PROGEXE = $(top_builddir)/src', \
        'PROGEXE = ' + gengen \
    )
    p.write()

    p = patcher.Patcher(s + '/tests/Makefile.am', None)
    p.replace( \
        '$(top_builddir)/src/gengen', \
        gengen + '/gengen' \
    )
    p.replace( \
        'PROGEXE = $(top_builddir)/src', \
        'PROGEXE = ' + gengen \
    )
    p.replace( \
        '$(top_builddir)/tests', \
        tests_dir \
    )
    p.write()

    p = patcher.Patcher(s + '/tests/otherdir/Makefile.am', None)
    p.replace( \
        '$(top_builddir)/src/gengen', \
        gengen + '/gengen' \
    )
    p.replace( \
        'PROGEXE = $(top_builddir)/src', \
        'PROGEXE = ' + gengen \
    )
    p.write()

    p = patcher.Patcher(s + '/src/skels/Makefile.am', None)
    p.replace( \
        'PROGNAME = $(top_builddir)/src', \
        'PROGNAME = ' + gengen \
    )
    p.write()

    bb.build.exec_func('do_autotools_autoreconf', d)
}

