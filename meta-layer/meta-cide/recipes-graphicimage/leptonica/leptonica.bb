SUMMARY = "leptonica"
LICENSE = "CLOSED"
MODULE = "graphicimage/leptonica"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "zlib libwebp libtiff libpng libjpeg")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import fileutils as fu
    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')
    os.system('git apply ' + fd + '/pngpriv.patch')
    if fu.file_contains('CMakeLists.txt', 'M_LIBRARY ""'):
        fu.replace_in_file('CMakeLists.txt', 'M_LIBRARY ""', 'M_LIBRARY "-lm"')
}


NOWARN = '\
-Wno-calloc-transposed-args \
-Wno-clobbered \
-Wno-maybe-uninitialized \
-Wno-sign-compare \
-Wno-stringop-overflow \
-Wno-type-limits \
-Wno-unused-but-set-variable \
-Wno-unused-function \
-Wno-unused-parameter \
-Wno-unused-variable \
-Wno-parentheses \
'

