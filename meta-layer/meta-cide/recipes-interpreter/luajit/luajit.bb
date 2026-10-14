SUMMARY = "luajit"
LICENSE = "CLOSED"
MODULE = "interpreter/luajit"
SRCPV = "1"

inherit simplemake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, "luajit")
    BD.depends(d, "lua")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

INCLUDE_LUA_DIR = "${TOPDIR}/tmp/work/lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_LUA_DIR = "${TOPDIR}/tmp/work/lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

MAKE_CFLAGS = "-fno-lto -O2 -fno-ipa-pta -I${INCLUDE_LUA_DIR}"
MAKE_LDFLAGS = "-L${LIB_LUA_DIR} -lm "
MAKE_MAKE_OPTIONS = "CC=sysroot_gcc CROSS=${TOOLCHAIN_DIR}/${TOOLCHAIN_PREFIX}"

python do_install:prepend() {
    bb.warn('install start')
}

python do_install:append() {
    import fileutils
    bb.warn('install link')
    inst = d.getVar('INSTALL_DIR')
    tpd  = d.getVar('TARGET_PREFIX_DIR')
    instd = inst + '/' + tpd + '/bin/'
    lj = fileutils.find_file_prefix(instd, "luajit-")
    if lj is not None:
        bb.warn(lj)
        fileutils.rel_link(lj, instd + 'luajit')
    bb.warn('install done')
}

