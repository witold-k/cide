SUMMARY = "libharu"
LICENSE = "CLOSED"
MODULE = "documents/libharu"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "zlib libpng")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import cmakepatch

    s = d.getVar('S')
    cp = cmakepatch.CMakePatch(s + '/CMakeLists.txt')
    cp.replace('(haru)', '(cmake/modules/haru.cmake)')
    cp.replace('(summary)', '(cmake/modules/summary.cmake)')
    cp.replace_find_library_as_package('M_LIB', 'M REQUIRED')
    cp.delete_lines('CMAKE_MODULE_PATH')
    cp.write()
}


NOWARN = ' \
    -Wno-cast-function-type \
    -Wno-enum-conversion \
    -Wno-misleading-indentation \
    -Wno-sign-compare \
    -Wno-unused-but-set-variable \
    -Wno-unused-function \
    -Wno-unused-parameter \
    -Wno-unused-variable \
'
CMAKE_C_FLAGS = ''

CMAKE_OPTIONS = '-DBUILD_SHARED_LIBS:BOOL=off'

