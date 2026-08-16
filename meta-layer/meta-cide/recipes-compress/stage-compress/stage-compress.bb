inherit stage

SUMMARY = "wait till recipes-base is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    brotli \
    bzip2 \
    liblzma \
    zlib \
    zstd \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.get("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

