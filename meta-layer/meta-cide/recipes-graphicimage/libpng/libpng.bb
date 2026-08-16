SUMMARY = "libpng"
LICENSE = "CLOSED"
MODULE = "graphicimage/libpng"
SRCPV = "1"

inherit simplecmake encapsulate

#AWK_BIN = "/bin/false"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import fileutils as fu
    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')
    os.system('git -C '  + s + ' apply ' + fd + '/pngpriv.patch')
    if fu.file_contains('CMakeLists.txt', 'M_LIBRARY ""'):
        fu.replace_in_file('CMakeLists.txt', 'M_LIBRARY ""', 'M_LIBRARY "-lm"')

    # awk = d.getVar('AWK_BIN')
    # if not os.path.exists(awk):
    #     raise Exception("awk not found: " + awk)
}


NOWARN = '\
-Wno-maybe-uninitialized \
-Wno-stringop-overflow \
'

CMAKE_OPTIONS = " \
    -DHAVE_LD_VERSION_SCRIPT=OFF \
    -DPNG_LIBCONF_HEADER=${S}/scripts/pnglibconf.h.prebuilt \
"
#    -DAWK=${AWK_BIN}
