SUMMARY = "unibilium"
LICENSE = "CLOSED"
MODULE = "base/unibilium"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import fileutils

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')
    fileutils.copy_file(fd + '/files/CMakeLists.txt', s + '/CMakeLists.txt')
}

