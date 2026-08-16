SUMMARY = "brotli"
LICENSE = "CLOSED"
MODULE = "compress/brotli"
SRCPV = "1"

inherit simplecmake encapsulate

NOWARN = "\
-Wno-array-bounds \
"

python do_patch() {
    import patcher
    s = d.getVar('S')
    p = patcher.Patcher(s + '/CMakeLists.txt', None)
    p.replace( \
        'target_link_libraries(brotli ${BROTLI_LIBRARIES})', \
        'target_link_libraries(brotli ${BROTLI_LIBRARIES} ${M_LIBRARY})'
    )
    p.insert_lines_after(
        'project(brotli C)', [ 'find_package(M)' ]
    )
    p.write()
}
