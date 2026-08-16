SUMMARY = "curl"
LICENSE = "CLOSED"
MODULE = "net/curl"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "openssl libidn2 libunistring brotli libpsl zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


CMAKE_C_FLAGS = " \
    -DHAVE_GETSOCKNAME=1 \
    -DHAVE_STRDUP=1 \
    -DSIZEOF_CURL_OFF_T=8 \
    -DSTDC_HEADERS=1 \
    -Wno-discarded-qualifiers \
    -Wno-implicit-function-declaration \
    -Wno-nested-externs \
    -Wno-nonnull-compare \
    -Wno-overflow \
    -Wno-redundant-decls \
    -Wno-return-type \
    -Wno-unused-function \
    -Wno-unused-parameter \
    -Wno-unused-variable \
    -Wno-cast-qual \
"

USE_HAVE_FLAGS = "on"

CMAKE_OPTIONS = " \
    -DUSE_LIBIDN2:BOOL=OFF \
    -DCURL_BROTLI=OFF \
    -DCURL_USE_LIBPSL=OFF \
    -DUSE_OPENSSL:BOOL=ON \
    -DCURL_USE_OPENSSL:BOOL=ON \
    -DCMAKE_C_FLAGS_CMDL='${CMAKE_C_FLAGS}' \
"

python do_patch() {
    import patcher, fileutils

    s = d.getVar('S')

    p = patcher.Patcher(s + '/lib/connect.c', None)
    p.replace('name:NULL', 'name:""')
    p.write()

    p = patcher.Patcher(s + '/CMakeLists.txt', None)
    p.replace('set(in_addr_t "unsigned long")', 'set(HAVE_IN_ADDR_T 1)')
    p.delete_lines('unused-parameter')
    p.write()

    p = patcher.Patcher(s + '/CMake/PickyWarnings.cmake', None)
    p.delete_lines('-Wunused-parameter')
    p.write()

    p = patcher.Patcher(s + '/src/CMakeLists.txt', None)
    p.replace( \
        'target_link_libraries(${EXE_NAME} ${LIB_SELECTED_FOR_EXE} ${CURL_LIBS})', \
        'target_link_libraries(${EXE_NAME} ${LIB_SELECTED_FOR_EXE} ${CURL_LIBS} ${M_LIBRARY})'
    )
    p.insert_lines_after(
        'add_definitions("-DBUILDING_CURL")', [ 'find_package(M)' ]
    )
    p.write()
}
