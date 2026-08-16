SUMMARY = "libjpeg"
LICENSE = "CLOSED"
MODULE = "graphicimage/libjpeg"
SRCPV = "1"

inherit simplecmake encapsulate

# python () {
#     from bbdepend import BBDepend as BD
#     BD.depends(d, "zlib")
# }
# do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = "\
    -Wno-clobbered \
    -Wno-sign-compare \
    -Wno-stringop-overflow \
    -Wno-unused-parameter \
"

