SUMMARY = "libexpat"
LICENSE = "CLOSED"
MODULE = "base/libexpat"
SRCPV = "1"

inherit simplecmake encapsulate

CMAKE_OPTIONS = "\
-DEXPAT_BUILD_DOCS:BOOL=OFF\
"

S = "${SOURCE_DIR}/expat"

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/expat_config.h.cmake', None)
    p.replace('#cmakedefine off_t @off_t@', '//#cmakedefine <DISABLED> off_t @off_t@')
    p.write()
}
