SUMMARY = "wayland"
LICENSE = "CLOSED"
MODULE = "graphics/wayland-protocols"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson wayland libffi libxml2")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

WAYLAND_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/wayland${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
WAYLAND_PROTO_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/wayland-protocols${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"

MESON_LDFLAGS = "${LIB_FFI_FLAGS} ${DEPEND_LIB_DIRS_SWITCH}"

python do_configure:prepend() {
    path = os.environ['PATH']
    wpath = d.getVar('WAYLAND_NATIVE_INSTALL_DIR') + '/bin'
    wppath = d.getVar('WAYLAND_PROTO_NATIVE_INSTALL_DIR') + '/bin'
    #bb.warn("wpath: " + wpath)
    #bb.warn("wppath: " + wppath)
    os.environ['PATH'] = path + ':' + wpath + ':' + wppath
}
