SUMMARY = "sysfsutils"
LICENSE = "CLOSED"
MODULE = "base/sysfsutils"
SRCPV = "1"

inherit simplecmake encapsulate

python do_patch() {
    import fileutils

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME') + '/files/'
    fileutils.copy_file(fd + 'CMakeLists.txt', s + '/CMakeLists.txt')
    fileutils.copy_file(fd + 'config.h', s + '/lib/config.h')
}
