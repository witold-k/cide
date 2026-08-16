inherit stage

SUMMARY = "wait till recipes-documents is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    libharu \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

