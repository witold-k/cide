SUMMARY = "libmpack-lua"
LICENSE = "CLOSED"
MODULE = "base/libmpack-lua"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "lua luajit msgpack-c libmpack")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import fileutils
    import patcher

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')

    fileutils.copy_files(fd + '/files', s)

    p = patcher.Patcher(s + '/lmpack.c', None)
    p.replace('mpack-src', '../libmpack')
    p.write()
}
