SUMMARY = "libtiff"
LICENSE = "CLOSED"
MODULE = "graphicimage/libtiff"
SRCPV = "1"

inherit simplecmake encapsulate

AWK_BIN = "/opt/rust/bin/awk-rs"

python () {
    from bbdepend import BBDepend as BD
    from bbpaths  import BBPaths  as BP
    BD.depends(d, "zlib zstd libjpeg libwebp")
    BP.from_current(d, "zlib").set_vars(d)
    BP.from_current(d, "zstd").set_vars(d)
    BP.from_current(d, "libjpeg").set_vars(d)
    BP.from_current(d, "libwebp").set_vars(d)
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = "\
-Wno-sign-compare \
-Wno-type-limits \
"

CMAKE_EXE_LINKER_FLAGS = "${DEPEND_LIB_DIRS_SWITCH}"

CMAKE_OPTIONS = " \
    -DAWK=${AWK_BIN} \
    -Djpeg-prefer-standard=ON \
"

python do_patch() {
    import fileutils

    s = d.getVar('S')
    fileutils.remove_file(s + '/cmake/FindJPEG.cmake')
    fileutils.remove_file(s + '/cmake/FindWebP.cmake')
    fileutils.remove_file(s + '/cmake/FindZSTD.cmake')
    fileutils.remove_file(s + '/cmake/Findliblzma.cmake')
}

