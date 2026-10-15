SUMMARY = "tig"
LICENSE = "CLOSED"
MODULE = "ide/tig"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses libreadline")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_OPTIONS = " -DTIG_SYSCONFDIR:PATH=/etc"

CMAKE_C_FLAGS = "\
    -Wno-format-truncation \
    -Wno-int-conversion \
    -Wno-missing-field-initializers \
    -Wno-sign-compare \
    -Wno-stringop-overflow \
    -Wno-unused-parameter \
"

python do_patch() {
    import fileutils

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')
    fileutils.copy_file(fd + '/files/CMakeLists.txt', s + '/CMakeLists.txt')
}

