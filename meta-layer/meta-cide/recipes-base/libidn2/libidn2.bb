SUMMARY = "libidn2"
LICENSE = "CLOSED"
MODULE = "base/libidn2"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, "libidn2")
    BD.depends(d, "glib libunistring")
}
do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

IDN2_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/libidn2${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
INSTALLED_GENDATA       = "${IDN2_NATIVE_INSTALL_DIR}/lib"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_CFLAGS   = ""
CONFIGURE_CXXFLAGS = ""
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

CONFIGURE_OPTIONS = " \
    --disable-doc \
"

python do_patch() {
    import patcher

    s = d.getVar('S')
    md = d.getVar('MODULE_DIR')

    p = patcher.Patcher(s + '/bootstrap', None)
    p.replace('eval "$cmd"', 'bash -c eval "$cmd"')
    p.write()

    p = patcher.Patcher(s + '/lib/Makefile.am', None)
    p.replace( \
        '$(builddir)/gendata$(EXEEXT) <', \
        d.getVar('INSTALLED_GENDATA') + '/gendata$(EXEEXT) <' \
    )
    p.replace( \
        '$(builddir)/gentr46map$(EXEEXT) >', \
        d.getVar('INSTALLED_GENDATA') + '/gentr46map$(EXEEXT) >' \
    )
    p.write()

    p = patcher.Patcher(s + '/bootstrap-funclib.sh', None)
    p.replace( \
        ' cmd=$(printf "$po_download_command_format" "$subdir" "$domain")', \
        ' # cmd=$(printf "$po_download_command_format" "$subdir" "$domain")', \
    )
    p.write()

    os.makedirs('po/.reference', exist_ok=True)
#   wget -r -l1 --no-parent -A "libidn2*" -nd https://translationproject.org/PO-files/de -P po/.reference/

    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'
    bb.build.exec_func('do_autotools_bootstrap', d)
}

