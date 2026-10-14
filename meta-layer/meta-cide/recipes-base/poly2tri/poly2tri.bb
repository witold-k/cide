SUMMARY = "poly2tri"
LICENSE = "CLOSED"
MODULE = "base/poly2tri"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

