UMMARY = "liboggz"
LICENSE = "CLOSED"
MODULE = "audioformat/liboggz"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "ogg")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}
