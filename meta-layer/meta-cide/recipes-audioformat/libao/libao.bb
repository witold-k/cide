SUMMARY = "libao"
LICENSE = "CLOSED"
MODULE = "audioformat/libao"
SRCPV = "1"

inherit simpleconfigure encapsulate

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}
