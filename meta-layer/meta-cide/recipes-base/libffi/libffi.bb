S1UMMARY = "libffi"
LICENSE = "CLOSED"
MODULE = "base/libffi"
SRCPV = "1"

inherit simpleconfigure encapsulate

CONFIGURE_COPY_SRC  = "true"
CONFIGURE_BUILD_DIR = "${B}"

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}

python do_install:append() {
    import bbtools

    bbtools.link_lib64(d)
}
