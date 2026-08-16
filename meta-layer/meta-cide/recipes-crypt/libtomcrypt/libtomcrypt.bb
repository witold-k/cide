SUMMARY = "libtomcrypt"
LICENSE = "CLOSED"
MODULE = "crypt/libtomcrypt"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libtommath")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


NOWARN = "\
    -Wno-unterminated-string-initialization \
"
