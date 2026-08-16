SUMMARY = "msgpack-c"
LICENSE = "CLOSED"
MODULE = "base/msgpack-c"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "jsonc googletest zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

