SUMMARY = "libxml2"
LICENSE = "CLOSED"
MODULE = "base/libxml2"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "liblzma zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_OPTIONS = " \
    -DLIBXML2_WITH_ICONV:BOOL=OFF \
    -DLIBXML2_WITH_ICU:BOOL=OFF \
    -DLIBXML2_WITH_PYTHON:BOOL=OFF \
    -DLIBXML2_WITH_TESTS:BOOL=OFF \
"
CMAKE_C_FLAGS = "-Wno-format-extra-args"
