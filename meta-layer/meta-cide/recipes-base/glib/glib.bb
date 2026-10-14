SUMMARY = "glib"
LICENSE = "CLOSED"
MODULE = "base/glib"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson pcre2 zlib libffi")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"

NOWARN = " \
-Wno-missing-include-dirs \
-Wno-null-dereference \
-Wno-sign-compare \
"

MESON_LDFLAGS = "${DEPEND_LIB_DIRS_SWITCH} ${TARGET_LIB_DIR_FLAGS} ${LIB_FFI_FLAGS}"

LIBRARY_PATH = "${DEPEND_LIB_PATH} -L${BUILDSYSTEMS_DIR}/lib"

MESON_PROGRAM_PATH = "${BUILDSYSTEMS_DIR}/bin"

# is not used, be kept here as a reminder
# is accessed by glib-compile-resources, but environment
# in this binary does not contain the XMLLINT
MESON_ENV = " \
XMLLINT=${BUILDSYSTEMS_DIR}/bin/xmllint \
"

MESON_OPTIONS = " \
-Dxmllint_path=${BUILDSYSTEMS_DIR}/bin/xmllint \
"

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/meson.options', None)
    p.insert_after("The name of the system API to use as a GFileMonitor backend", \
        "option('xmllint_path', type: 'string', value: '', description: 'Path to the xmllint')")
    p.write()

    p = patcher.Patcher(s + '/gio/tests/meson.build', None)
    p.insert_after("test_env.set('GIO_MODULE_DIR', '')", \
        "test_env.set('XMLLINT', get_option('xmllint_path'))")
    p.insert_after("input : 'test.gresource.xml'", \
        "env : test_env,")
    p.write()

}
