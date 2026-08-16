SUMMARY = "gettext"
LICENSE = "CLOSED"
MODULE = "base/gettext"
SRCPV = "1"

SKIP_BUILD = "True"

inherit simpleconfigure encapsulate

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

python do_patch() {
    md = d.getVar('MODULE_DIR')
    os.environ['GNULIB_SRCDIR'] = md + '/base/gnulib'
    bb.build.exec_func('do_autotools_autogen', d)
}
