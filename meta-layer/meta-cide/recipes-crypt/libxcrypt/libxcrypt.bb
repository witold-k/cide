SUMMARY = "libxcrypt"
LICENSE = "CLOSED"
MODULE = "crypt/libxcrypt"
SRCPV = "1"

inherit simpleconfigure encapsulate

#python () {
#    from bbdepend import BBDepend as BD
#    BD.depends(d, "")
#}
#do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_CFLAGS = ""
CONFIGURE_CXXFLAGS = "${CONFIGURE_CFLAGS}"
CONFIGURE_LDFLAGS = ""

CONFIGURE_OPTIONS = "\
"

python do_patch() {
    bb.build.exec_func('do_autotools_autoreconf', d)
}

