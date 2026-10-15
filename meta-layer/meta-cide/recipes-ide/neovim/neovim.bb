inherit simplecmake encapsulate

SUMMARY = "neovim"
LICENSE = "CLOSED"
MODULE = "ide/neovim"
SRCPV = "1"

DEPEND_LIST = "\
    jsonc \
    libmpack \
    libmpack-lua \
    libncurses \
    libreadline \
    libtermkey \
    libuv \
    libvterm \
    lpeg \
    luabitop \
    luajit \
    luv \
    msgpack-c \
    slang \
    tree-sitter \
    utf8proc \
    zlib \
"
python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, 'neovim ' + d.getVar('DEPEND_LIST'))
    BD.depends(d, d.getVar('DEPEND_LIST'))
}
do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

LPEG_BASE = "${TOPDIR}/tmp/work/lpeg${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_LPEG_DIR = "${LPEG_BASE}/lib"

MPACK_BASE = "${TOPDIR}/tmp/work/libmpack-lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_MPACK_DIR = "${MPACK_BASE}/lib"

#LUA_BASE = "${TOPDIR}/tmp/work/luajit${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
# need native executable
NATIVE_NVIM_BASE = "${TOPDIR}/tmp/work/neovim${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
NATIVE_NVIM_BIN  = "${NATIVE_NVIM_BASE}/bin/nvim"
LUA_BASE = "${TOPDIR}/tmp/work/luajit${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
LUA_NVIM_LIB = "${TOPDIR}/tmp/work/neovim${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}/lib/libnlua0.so"

NOWARN = "\
-Wno-array-bounds \
-Wno-dangling-pointer \
-Wno-error \
-Wno-error=pedantic \
-Wno-format-truncation \
-Wno-maybe-uninitialized \
-Wno-missing-field-initializers \
-Wno-overlength-strings \
-Wno-sign-compare \
-Wno-sign-conversion \
"

CMAKE_C_FLAGS = " \
-D_DEFAULT_SOURCE \
-DHAVE_BE64TOH \
"

CMAKE_CXX_FLAGS = " \
-D_DEFAULT_SOURCE \
-DHAVE_BE64TOH \
"

CMAKE_EXE_LINKER_FLAGS = "-lm ${DEPEND_LIB_DIRS_SWITCH}"

do_patch_script() {
    cd ${S}
    find cmake -iname "Find*.cmake" -not -path cmake/Find.cmake -exec rm {} \;
    find cmake.deps -iname "Build*.cmake" -exec rm {} \;

    if grep -q 'VV_PROGPATH' src/nvim/ui_client.c;
    then
        sed -i 's|VV_PROGPATH|VV_PROGNAME|g' src/nvim/ui_client.c;
    fi

    if ! grep -q 'meta-layer/build' src/gen/preload_nlua.lua;
    then
        sed -E -i "s|nlualib =(.*)$|nlualib =\1\nnlualib = \'${LUA_NVIM_LIB}\'\n|g" src/gen/preload_nlua.lua
    fi
}

python do_patch() {
    import patcher
    import fileutils

    bb.build.exec_func('do_patch_script', d)

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')

    p = patcher.Patcher(s + '/src/nvim/CMakeLists.txt', None)
    p.replace('PUBLIC ${LUAJIT_INCLUDE_DIR})', 'PUBLIC ${ALL_INCLUDES})')
    p.replace('main_lib PUBLIC libuv', 'main_lib PUBLIC PkgConfig::LIBLUV UV::UV VTERM::VTERM')
    p.replace('${LUV_LIBRARY}', 'LUV::LUV UV::UV')
    p.replace(' libvterm', '')
    p.replace(' iconv', '')
    p.replace(' lpeg', ' LPEG::LPEG')
    p.delete_lines('main_lib INTERFACE libintl')
    p.insert_lines_after( \
        'find_package(Unibilium', [ \
        'find_package(Libvterm REQUIRED)', \
        'find_package(UTF8proc REQUIRED)', \
        'find_package(Lpeg REQUIRED)', \
        'find_package(Luv REQUIRED)', \
        'set (ALL_INCLUDES \
${LUAJIT_INCLUDE_DIR} \
${LIBUV_INCLUDE_DIR} \
${LIBLUV_INCLUDE_DIRS} \
${LIBLPEG_INCLUDE_DIRS} \
${MSGPACK_INCLUDE_DIR} \
${TreeSitter_INCLUDE_DIR} \
${LIBVTERM_INCLUDE_DIR} \
${UNIBILIUM_INCLUDE_DIR} \
${UTF8PROC_INCLUDE_DIR} \
)', \
        ] \
    )
    p.write()

    p = patcher.Patcher(s + '/cmake.config/config.h.in', None)
    p.delete_lines('#cmakedefine HAVE__NSGETENVIRON')
    p.delete_lines('#cmakedefine HAVE_SYS_SDT_H')
    p.delete_lines('#cmakedefine HAVE_BE64TOH')
    p.write()

    p = patcher.Patcher(s + '/runtime/CMakeLists.txt', None)
    p.replace('$<TARGET_FILE:nvim_bin>', d.getVar('NATIVE_NVIM_BIN'))
    p.write()

#    p = patcher.Patcher(s + '/src/nvim/event/libuv_proc.c', None)
#    p.replace('.data.fd =', '.data.file =')
#    p.write()

#    p = patcher.Patcher(s + '/src/nvim/lua/treesitter.c', None)
#    p.replace('ts_node_child_containing_descendant', 'ts_node_child_with_descendant')
#    p.write()

    p = patcher.Patcher(s + '/cmake/Deps.cmake', None)
    p.delete_lines('find_program(CACHE_PRG')
    p.write()
}

do_configure:prepend() {
    import os
    lb  = d.getVar("LUA_BASE")
    nv  = d.getVar('NATIVE_NVIM_BIN')
    lld = d.getVar('LIB_LPEG_DIR') + '/lua/?.so'
    lmd = d.getVar('LIB_MPACK_DIR') + '/?.so'
    os.environ['LUA_CPATH'] = lld + ';' + lmd
    # root = d.getVar('ROOT_DIR')

    co = "-DLUA_PRG=" + lb + "/bin/luajit"
    if d.getVar('CURRENT_TARGET') != 'native':
        co += ' -DNVIM_PRG=' + nv
        os.environ['NVIM_PRG'] = nv
    existing_opts = d.getVar('CMAKE_OPTIONS') or ''
    d.setVar('CMAKE_OPTIONS', existing_opts + ' ' + co)
    bb.warn("NATIVE_NVIM_BIN=" + nv)
}

python do_compile:prepend() {
    import os
    from bbdepend import BBDepend

    ld = d.getVar('LIB_LPEG_DIR')
    md = d.getVar('LIB_MPACK_DIR')
    os.environ['LUA_CPATH'] = ld + '/lua/?.so;' + md + '/?.so'
    path = os.environ['PATH']
    os.environ['PATH'] = d.getVar('NATIVE_NVIM_BASE') + ':' + path

    if d.getVar('CURRENT_TARGET') == d.getVar('NATIVE_TARGET'):
        native_lib_path = BBDepend.createAll(d, d.getVar('DEPEND_LIST')).libPath
    else:
        native_lib_path = d.getVar('NATIVE_DEPEND_LIB_PATH')
    if native_lib_path:
        existing = d.getVar('TOOLCHAIN_LD_LIBRARY_PATH') or ''
        d.setVar('TOOLCHAIN_LD_LIBRARY_PATH',
                 native_lib_path + (':' + existing if existing else ''))
}

