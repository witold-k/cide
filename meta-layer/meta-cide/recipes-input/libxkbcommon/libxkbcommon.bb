SUMMARY = "libxkbcommon"
LICENSE = "CLOSED"
MODULE = "input/libxkbcommon"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "wayland wayland-protocols icu libncurses libreadline")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

WAYLAND_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/wayland${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
WAYLAND_PROTO_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/wayland-protocols${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"

MESON_OPTIONS = " \
    -Denable-x11=false \
    -Denable-parser-auto-generation=false \
"
MESON_LDFLAGS = "${LIB_FFI_FLAGS} ${DEPEND_LIB_DIRS_SWITCH}"

NOWARN = "\
    -Wno-cpp \
    -Wno-function-cast \
    -Wno-bad-function-cast \
"

python do_patch:prepend() {
    import patcher

    s = d.getVar('S')
    wid = d.getVar("WAYLAND_NATIVE_INSTALL_DIR")

    p = patcher.Patcher(s + '/meson.build', None)
    p.delete_lines("wayland_scanner = find_program")
    p.insert_after("wayland_scanner_dep = dependency", "wayland_scanner = find_program('" +  wid + "/bin/wayland-scanner')")
    p.write()
}

python do_configure:prepend() {
    import os
    path = os.environ['PATH']
    wpath = d.getVar('WAYLAND_NATIVE_INSTALL_DIR') + '/bin'
    wppath = d.getVar('WAYLAND_PROTO_NATIVE_INSTALL_DIR') + '/bin'
    os.environ['PATH'] = path + ':' + wpath + ':' + wppath
    os.environ['BISON'] = d.getVar('BUILDSYSTEMS_DIR') + '/wrapper/bin/bison'
}

python do_compile:prepend() {
    import os
    os.environ['BISON'] = d.getVar('BUILDSYSTEMS_DIR') + '/wrapper/bin/bison'
}
