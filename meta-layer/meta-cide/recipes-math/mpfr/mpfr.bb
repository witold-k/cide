S1UMMARY = "mpfr"
LICENSE = "CLOSED"
MODULE = "math/mpfr"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "gmp")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_BUILD_DIR     = "${B}"
CONFIGURE_CONFIGURE_DIR = "${B}"

NOWARN = ""

CONFIGURE_CFLAGS   = ""
CONFIGURE_CXXFLAGS = ""
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}
