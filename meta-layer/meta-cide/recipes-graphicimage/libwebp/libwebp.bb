SUMMARY = "libwebp"
LICENSE = "CLOSED"
MODULE = "graphicimage/libwebp"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "zlib libjpeg libpng")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = "\
"

CMAKE_C_FLAGS   = ''
CMAKE_CXX_FLAGS = ''
CMAKE_EXE_LINKER_FLAGS = "${DEPEND_LIB_DIRS_SWITCH} -lpng -lz"

