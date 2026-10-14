SUMMARY = "luabitop"
LICENSE = "CLOSED"
MODULE = "interpreter/luabitop"
SRCPV = "1"

inherit simplecmake encapsulate

SKIP_TEST = "True"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "lua luajit")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

python do_patch() {
    import patcher

    s = d.getVar('S')

    if os.path.exists(s + '/cmake/FindLua.cmake'):
        os.remove(s + '/cmake/FindLua.cmake')

    p = patcher.Patcher(s + '/cmake/lua.cmake', None)
    p.replace('Lua REQUIRED', 'LuaJit REQUIRED')
    p.write()
}

CMAKE_C_FLAGS = "\
    -Wno-misleading-indentation \
"
