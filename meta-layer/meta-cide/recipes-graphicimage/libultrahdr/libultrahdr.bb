SUMMARY = "libultrahdr"
LICENSE = "CLOSED"
MODULE = "graphicimage/libultrahdr"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libjpeg libpng libtiff zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_LDFLAGS = "${DEPEND_LIB_DIRS_SWITCH}"

NOWARN = "\
    -Wno-maybe-uninitialized \
    -Wno-sign-compare \
    -Wno-type-limits \
    -Wno-unused-parameter \
    -Wno-unused-variable \
"

FEATURES = "\
    -DUHDR_BUILD_DEPS:BOOL=OFF \
    -DUHDR_ENABLE_INSTALL:BOOL=ON \
"

CMAKE_OPTIONS   = '${FEATURES}'

python do_patch() {
    import patcher

    s = d.getVar('S')

    cmf = s + '/CMakeLists.txt'
    p = patcher.Patcher(cmf, None)
    p.replace('UHDR_ENABLE_INSTALL FALSE', 'UHDR_ENABLE_INSTALL TRUE')
    p.replace('UHDR_BUILD_DEPS TRUE', 'UHDR_BUILD_DEPS FALSE')
    p.write()

    p = patcher.Patcher(s + '/lib/include/ultrahdr/ultrahdrcommon.h', None)
    p.insert_after( \
        '#include <vector>', \
        '#include <stdint.h>', \
    )
    p.write()
}
