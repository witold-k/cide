SUMMARY = "sc-im"
LICENSE = "CLOSED"
MODULE = "ide/sc-im"
SRCPV = "1"

inherit simplemake encapsulate

MAKE_SRC_DIR = "${B}/src"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses lua")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

INCLUDE_LIBNCURSES_DIR_1 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include/ncursesw"
LIB_LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_LUA_DIR = "${TOPDIR}/tmp/work/lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_LUA_DIR     = "${TOPDIR}/tmp/work/lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

MAKE_CFLAGS = "\
    -I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2} \
    -I${INCLUDE_LUA_DIR} -I${INCLUDE_LIBNCURSES_DIR_2} \
    -Wno-implicit-function-declaration \
    -Wno-maybe-uninitialized \
    -Wno-stringop-truncation \
    -Wno-unused-result \
    -Wno-unused-variable \
"

MAKE_LDFLAGS = "-L${LIB_LIBNCURSES_DIR} -L${LIB_LUA_DIR}"

python do_patch() {
    import fileutils
    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')

    fileutils.copy_file(fd + '/files/Makefile', s + '/src/Makefile')
}
