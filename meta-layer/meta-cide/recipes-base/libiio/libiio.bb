SUMMARY = "libiio"
LICENSE = "CLOSED"
MODULE = "base/libiio"
SRCPV = "1"

# XML Problems takes wrong library
SKIP_BUILD = 'True'

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libxml2 zstd")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


python do_patch() {
    import cmakepatch
    import patcher

    s = d.getVar('S')
    cp = cmakepatch.CMakePatch(s + '/CMakeLists.txt')
    cp.replace_find_module('LibXml2 QUIET NO_MODULE NO_SYSTEM_ENVIRONMENT_PATH', 'LibXml2 REQUIRED')
    cp.replace_find_module('LibXml2 QUIET NO_MODULE', 'LibXml2 REQUIRED')
    cp.replace_find_library_as_package('LIBZSTD_LIBRARIES', 'Zstd REQUIRED')
    cp.delete_find_path('LIBZSTD_INCLUDE_DIR')
    cp.insert_after( \
        'target_sources(iio PRIVATE iiod-client.c)', \
        'target_include_directories(iio PRIVATE ${LIBZSTD_INCLUDE_DIR})')
    cp.write()

    cp = cmakepatch.CMakePatch(s + '/utils/CMakeLists.txt')
    cp.insert_lines_after('cmake_minimum_required', [ \
        'find_package(Zstd REQUIRED)', \
        'find_package(LibXml2 REQUIRED)', \
        'find_package(LibLZMA REQUIRED)', \
        'find_package(Zlib REQUIRED)' \
    ])
    cp.replace(\
        'target_link_libraries(${test} LINK_PRIVATE iio iio_tests_helper)', \
        'target_link_libraries(${test} LINK_PRIVATE iio iio_tests_helper \
        ${LIBXML2_LIBRARIES} ${LIBZSTD_LIBRARIES} \
        ${LIBLZMA_LIBRARIES} ${ZLIB_LIBRARIES} \
    )')
    cp.write()

    p = patcher.Patcher(s + '/xml.c', None)
    p.replace('XML_PARSE_DTDVALID', 'XML_DOC_DTDVALID')
    p.write()
}


CMAKE_LIBRARY_XML2_PATH = "${TOPDIR}/tmp/work/libxml2${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"
CMAKE_BUILD_RPATH = "${CMAKE_LIBRARY_XML2_PATH}"
CMAKE_INSTALL_RPATH = "${CMAKE_LIBRARY_XML2_PATH}"


CMAKE_C_FLAGS = "\
    -Wno-implicit-function-declaration \
    -Wno-int-conversion \
    -Wno-sign-compare \
    -Wno-type-limits \
    -Wno-unused-parameter \
    -Wno-deprecated-declarations \
    -Wno-parentheses \
    -Wno-unused-but-set-variable \
"

CMAKE_OPTIONS = " \
    -DWITH_USB_BACKEND:BOOL=OFF \
    -DWITH_NETWORK_BACKEND:BOOL=ON \
    -DHAVE_DNS_SD:BOOL=OFF \
    -DWITH_AIO:BOOL=OFF \
    -DWITH_IIOD:BOOL=OFF \
    -DWITH_ZSTD:BOOL=ON \
    -DNO_THREADS:BOOL=OFF \
    -DWITH_XML_BACKEND:BOOL=ON \
    -DWITH_TESTS=OFF \
"
