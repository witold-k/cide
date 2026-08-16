SUMMARY = "pcre2"
LICENSE = "CLOSED"
MODULE = "base/pcre2"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libreadline bzip2 zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import patcher
    s = d.getVar('S')

#    p = patcher.Patcher(s + '/config-cmake.h.in', None)
#    p.delete_lines('HAVE_WINDOWS_H')
#    p.write()

    p = patcher.Patcher(s + '/CMakeLists.txt', None)
    p.delete_lines('-mshstk')
    p.write()
}

CMAKE_OPTIONS = " \
    -DPCRE2_BUILD_TESTS:BOOL=off \
"
