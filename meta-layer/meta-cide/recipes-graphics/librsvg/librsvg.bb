SUMMARY = "librsvg"
LICENSE = "CLOSED"
MODULE = "graphics/librsvg"
SRCPV = "1"

SKIP_BUILD = 'True'

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "pango cairo")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

