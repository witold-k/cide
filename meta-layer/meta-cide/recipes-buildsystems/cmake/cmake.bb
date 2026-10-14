SUMMARY = "cmake"
LICENSE = "CLOSED"
MODULE = "buildsystems/cmake"
SRCPV = "1"

SKIP_BUILD = "True"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "openssl curl googletest zlib zstd libexpat")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = "\
    -Wno-unused-function \
    -Wno-array-bounds \
"

CMAKE_C_FLAGS = "${NOWARN}"
CMAKE_CXX_FLAGS = "${NOWARN}"
CMAKE_OPTIONS = "-DCMAKE_USE_SYSTEM_ZLIB:BOOL=ON"

python do_patch() {
    import fileutils

    f = d.getVar('PROJECT_ROOT') + '/modulerefs/cmake/'
    s1 = d.getVar('S') + '/Utilities/cmcurl/CMake/'
    s2 = d.getVar('S') + '/Modules/'
    fileutils.copy_file(f + 'FindZLIB.cmake', s1 + 'FindZLIB.cmake')
    fileutils.copy_file(f + 'FindZLIB.cmake', s2 + 'FindZLIB.cmake')

#    fileutils.remove_file(s + 'FindCURL.cmake')
#    fileutils.remove_file(s + 'FindEXPAT.cmake')
#    fileutils.remove_file(s + 'FindLibUV.cmake')
#    fileutils.remove_file(s + 'FindOpenSSL.cmake')
#    fileutils.remove_file(s + 'FindZLIB.cmake')
#    fileutils.remove_file(s + 'FindZstd.cmake')
}

