SUMMARY = "librtlsdr"
LICENSE = "CLOSED"
MODULE = "sdr/librtlsdr"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libusb")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


python do_patch() {
    import patcher
    s = d.getVar('S')

    p = patcher.Patcher(s + "/CMakeLists.txt", None)
    p.replace( \
        "pkg_check_modules(LIBUSB libusb-1.0 IMPORTED_TARGET)", \
        "find_package(Libusb-1.0)", \
    )
    p.write()
}
