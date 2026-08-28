SUMMARY = "wayland"
LICENSE = "CLOSED"
MODULE = "graphics/wayland"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, "wayland")
    BD.depends(d, "libffi libexpat libxml2")
}
do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

WAYLAND_NATIVE_BUILD_DIR = "${TOPDIR}/tmp/work/wayland${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
SCANNER                  = "${WAYLAND_NATIVE_BUILD_DIR}/src/wayland-scanner"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"
MESON_LDFLAGS = "${LIB_FFI_FLAGS}"

MESON_OPTIONS = " \
    -Ddocumentation=false \
"

