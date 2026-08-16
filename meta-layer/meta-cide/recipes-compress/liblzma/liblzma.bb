SUMMARY = "liblzma"
LICENSE = "CLOSED"
MODULE = "compress/liblzma"
SRCPV = "1"

inherit simplecmake encapsulate

#python () {
#    from bbdepend import BBDepend as BD
#    BD.depends(d, "")
#}
#do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_LDFLAGS = "${DEPEND_LIB_DIRS_SWITCH}"

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/CMakeLists.txt', None)
    p.replace('NOT TUKLIB_CPUCORES_FOUND OR NOT TUKLIB_PHYSMEM_FOUND', 'NOT TUKLIB_CPUCORES_FOUND AND NOT TUKLIB_PHYSMEM_FOUND')
    p.delete_lines('set(SANDBOX_COMPILE_DEFINITION "HAVE_CAP_RIGHTS_LIMIT")')
    p.delete_lines('check_symbol_exists(optreset getopt.h HAVE_OPTRESET)')
    p.write()

    p = patcher.Patcher(s + '/src/xz/message.c', None)
    p.replace('static char buf[16]', 'static char buf[32]')
    p.write()

#HAVE_USABLE_CLMUL
}

CMAKE_C_FLAGS = " \
    -fPIC \
    -Wno-unterminated-string-initialization \
"

CMAKE_OPTIONS = "-DXZ_NLS=OFF"
