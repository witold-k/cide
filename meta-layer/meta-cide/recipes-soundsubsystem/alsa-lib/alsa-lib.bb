SUMMARY = "alsa-lib"
LICENSE = "CLOSED"
MODULE = "soundsubsystem/alsa-lib"
SRCPV = "1"

inherit simpleconfigure encapsulate

# python () {
#     from bbdepend import BBDepend as BD
#     BD.depends(d, "")
# }
# do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/Makefile.am', None)
    p.replace('SUBDIRS=doc', 'SUBDIRS=')
    p.write()

    bb.build.exec_func('do_autotools_autoreconf', d)
}

CONFIGURE_DEEPCOPY_SRC  = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

