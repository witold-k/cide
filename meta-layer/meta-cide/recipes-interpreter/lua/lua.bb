SUMMARY = "lua"
LICENSE = "CLOSED"
MODULE = "interpreter/lua"
SRCPV = "1"

inherit simplecmake encapsulate

python do_patch() {
    import fileutils

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')
    fileutils.copy_file(fd + '/files/CMakeLists.txt', s + '/CMakeLists.txt')
}

python do_install:append() {
    import fileutils
    inst = d.getVar('INSTALL_DIR')
    tpd  = d.getVar('TARGET_PREFIX_DIR')
    libd = inst + '/' + tpd + '/lib/'
    rs = fileutils.rel_link(libd + 'liblua.a', libd + 'liblua5.1.a')
}
