SUMMARY = "ninja"
LICENSE = "CLOSED"
MODULE = "buildsystems/ninja"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "googletest")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


NOWARN = ' \
    -Wno-sign-compare \
    -Wno-type-limits \
    -Wno-unused-parameter \
    -Wno-unused-variable \
    -Wno-address \
    -Wno-nonnull-compare \
'

CMAKE_C_FLAGS   = '${NOWARN}'
CMAKE_CXX_FLAGS = '${NOWARN}'

