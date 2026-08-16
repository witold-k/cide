SUMMARY = "cmph"
LICENSE = "CLOSED"
MODULE = "base/cmph-git"
SRCPV = "1"

inherit simpleconfigure encapsulate

python do_patch() {
    bb.build.exec_func('do_autotools_autoreconf', d)
}

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

INCLUDE_CMPH_DIR = "${S}/src"

CONFIGURE_CFLAGS = "-I${INCLUDE_CMPH_DIR}"
#CONFIGURE_LDFLAGS = "-L${LIB_CMPH_DIR}"
