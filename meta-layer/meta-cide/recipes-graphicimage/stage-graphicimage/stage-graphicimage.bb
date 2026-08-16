inherit stage

SUMMARY = "wait till recipes-graphics is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    fontconfig \
    freetype \
    fribidi \
    leptonica \
    libjpeg \
    libtiff \
    libultrahdr \
    libwebp \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

