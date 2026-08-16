SUMMARY = "glfw"
LICENSE = "CLOSED"
MODULE = "graphics/glfw"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "wayland wayland-protocols libxkbcommon")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

WAYLAND_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/wayland${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
WAYLAND_PROTO_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/wayland-protocols${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"

CMAKE_SYSTEM_PROGRAM_PATH = "${WAYLAND_NATIVE_INSTALL_DIR}/bin"

NOERROR = "\
    -Wno-array-bounds \
    -Wno-implicit-fallthrough \
    -Wno-maybe-uninitialized \
    -Wno-missing-field-initializers \
    -Wno-sign-compare \
    -Wno-stringop-overflow \
    -Wno-stringop-truncation \
    -Wno-unused-but-set-variable \
    -Wno-unused-parameter \
"

CMAKE_CXX_FLAGS = "${NOERROR}"
CMAKE_C_FLAGS = "${NOERROR}"

CMAKE_OPTIONS = "\
    -DGLFW_BUILD_X11:BOOL=off \
"
