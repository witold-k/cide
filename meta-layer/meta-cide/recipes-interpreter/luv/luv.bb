SUMMARY = "luv"
LICENSE = "CLOSED"
MODULE = "interpreter/luv"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "lua luajit libuv")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_OPTIONS = "\
-DWITH_LUA_ENGINE=LuaJIT \
-DWITH_SHARED_LIBUV:BOOL=on \
-DWITH_SHARED_LUA:BOOL=on \
-DLUA_BUILD_TYPE=System \
-DBUILD_STATIC_LIBS=on \
"

CMAKE_C_FLAGS = "\
-Wno-unused-function \
-Wno-sign-compare \
-Wno-maybe-uninitialized \
"

python do_patch() {
    import fileutils, patcher
    s = d.getVar('S')
#    p = patcher.Patcher(s + '/src/process.c', None)
#    p.replace('options.stdio[i].data.fd', 'options.stdio[i].data.file')
#    p.write()
    fileutils.remove_file(s + '/cmake/Modules/FindLibuv.cmake')
    fileutils.remove_file(s + '/cmake/Modules/FindLua.cmake')
    fileutils.remove_file(s + '/cmake/Modules/FindLuaJIT.cmake')
}

python do_install:append() {
    import fileutils
    inst = d.getVar('INSTALL_DIR')
    tpd  = d.getVar('TARGET_PREFIX_DIR')
    lib  = inst + '/' + tpd + '/lib'
    fileutils.rel_link(lib + '/lua/luv.so', lib + '/libluv.so')
}
