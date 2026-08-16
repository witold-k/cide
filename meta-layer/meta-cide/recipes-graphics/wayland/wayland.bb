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

MESON_LDFLAGS = "${LIB_FFI_FLAGS} ${DEPEND_LIB_DIRS_SWITCH}"

MESON_OPTIONS = " \
    -Dwayland_scanner=${SCANNER} \
    -Ddocumentation=false \
"

python do_patch() {
    import patcher
    s = d.getVar('S')

    p = patcher.Patcher(s + '/src/meson.build', None)
    p.delete_lines("scanner_dep = dependency('wayland-scanner', native: true, version: meson.project_version()")
    p.delete_lines("wayland_scanner_for_build = find_program(scanner_dep.get_variable(pkgconfig: 'wayland_scanner'))")
    p.insert_after("if meson.is_cross_build() or not get_option('scanner')", \
        "       wayland_scanner_for_build = get_option('wayland_scanner')" \
    )
    p.write()

    p = patcher.Patcher(s + '/meson_options.txt', None)
    p.insert_after("value: '')", \
        "option('wayland_scanner', type: 'string', value: '', description: 'Path to the wayland scanner')")
    p.write()
}

