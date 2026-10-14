SUMMARY = "lpeg"
LICENSE = "CLOSED"
MODULE = "interpreter/lpeg"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "lua luajit")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_C_FLAGS   = '-Wno-implicit-fallthrough'
CMAKE_CXX_FLAGS = '-Wno-implicit-fallthrough'

python do_patch() {
    import patcher

    s = d.getVar('S')

    if os.path.exists(s + '/cmake/FindLua.cmake'):
        os.remove(s + '/cmake/FindLua.cmake')

    p = patcher.Patcher(s + '/cmake/lua.cmake', None)
    p.replace('Lua REQUIRED', 'LuaJit REQUIRED')
    p.write()
}

python do_install:append() {
    import fileutils
    inst = d.getVar('INSTALL_DIR')
    tpd  = d.getVar('TARGET_PREFIX_DIR')
    lib  = inst + '/' + tpd + '/lib'
    fileutils.rel_link(lib + '/lua/lpeg.so', lib + '/liblpeg.so')
}
