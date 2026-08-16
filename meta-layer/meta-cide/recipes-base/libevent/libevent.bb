SUMMARY = "libevent"
LICENSE = "CLOSED"
MODULE = "base/libevent"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "openssl")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

#CONFIGURE_OPTIONS = "--disable-openssl"

CMAKE_C_FLAGS = " \
    -Wno-unterminated-string-initialization \
"

CMAKE_OPTIONS = " \
    -DEVENT__DISABLE_OPENSSL:BOOL=ON \
    -DEVENT__DISABLE_MBEDTLS:BOOL=ON \
    "

python do_patch() {
    import patcher
    s = d.getVar('S')

    p = patcher.Patcher(s + '/test/regress_main.c', None)
    p.insert_after("#include <stdlib.h>", \
        "#include <limits.h>")
    p.write()
}
