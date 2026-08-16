SUMMARY = "libuuid"
LICENSE = "CLOSED"
MODULE = "base/libuuid"
SRCPV = "1"

SKIP_BUILD = 'True'

inherit simpleconfigure encapsulate

# python do_patch() {
#     bb.build.exec_func('do_autotools_autoreconf', d)
# }

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

