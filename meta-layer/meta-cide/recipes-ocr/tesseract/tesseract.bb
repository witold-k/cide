SUMMARY = "tesseract"
LICENSE = "CLOSED"
MODULE = "ocr/tesseract"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    from bbpaths  import BBPaths  as BP
    BD.depends(d, " \
        curl \
        pango icu \
        glib  \
        cairo fontconfig freetype pixman  \
        fribidi harfbuzz libunistring libidn2 libpsl \
        leptonica libjpeg libtiff libpng liblzma libffi \
        brotli libexpat libxml2 bzip2 zlib zstd \
        curl openssl \
    ")
    BP.from_current(d, "leptonica").set_vars(d)
}

do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_C_FLAGS = "${DEPEND_INCLUDE_DIRS_SWITCH} -I${LEPTONICA_INCLUDE_DIR}/leptonica"
CMAKE_CXX_FLAGS = "${DEPEND_INCLUDE_DIRS_SWITCH} -I${LEPTONICA_INCLUDE_DIR}/leptonica"
CMAKE_EXE_LINKER_FLAGS = "\
    -lpng -ljpeg -ltiff -lz \
    ${DEPEND_LIB_DIRS_SWITCH} \
"

NOWARN = ' \
    -Wno-class-memaccess \
    -Wno-extra \
    -Wno-sign-compare \
    -Wno-stringop-truncation \
    -Wno-switch \
    -Wno-unused-parameter \
'

CMAKE_OPTIONS = " \
    -DLEPT_TIFF_RESULT=TRUE \
    -DHAVE_TIFF=TRUE \
    -DHAVE_LIBTIFF=TRUE \
"

