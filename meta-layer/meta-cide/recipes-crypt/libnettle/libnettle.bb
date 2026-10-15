S1UMMARY = "libnettle"
LICENSE = "CLOSED"
MODULE = "crypt/nettle"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "gmp")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    bb.build.exec_func('do_autotools_bootstrap', d)
}

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_CFLAGS   = ""
CONFIGURE_CXXFLAGS = ""
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

TEXINDEX_AWK = "/usr/bin/gawk"
export TEXINDEX_AWK

python do_install:append() {
    import fileutils

    fd    = d.getVar('FILE_DIRNAME') + '/files/'
    instd = d.getVar('INSTALL_DIR')
    tp    = d.getVar('TARGET_PREFIX_DIR')

    os.chdir(instd + '/' + tp)
    if not os.path.exists('lib'):
        if os.path.exists('lib64'):
            os.rename('lib64', 'lib')
}
