SUMMARY = "libxsfp"
LICENSE = "CLOSED"
MODULE = "audioformat/libxspf"
SRCPV = "1"

SKIP_BUILD = "True"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libexpat liburiparser libcpptest libxml2")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_CFLAGS   = ""
CONFIGURE_CXXFLAGS = ""
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}

