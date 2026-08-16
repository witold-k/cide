SUMMARY = "libusb"
LICENSE = "CLOSED"
MODULE = "base/libusb"
SRCPV = "1"

inherit simplecmake encapsulate

CMAKE_C_FLAGS = " \
    -Wno-c++-compat \
    -Wno-declaration-after-statement \
"

python do_patch() {
    import fileutils

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME') + '/files/'
    fileutils.copy_file(fd + 'CMakeLists.txt', s + '/CMakeLists.txt')
    fileutils.copy_file(fd + 'config.h', s + '/libusb/config.h')
}
