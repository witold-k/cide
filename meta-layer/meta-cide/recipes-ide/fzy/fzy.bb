SUMMARY = "fzy"
LICENSE = "CLOSED"
MODULE = "ide/fzy"
SRCPV = "1"

inherit simplemake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses libreadline")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

INCLUDE_LIBREADLINE_DIR_1 = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_LIBREADLINE_DIR = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_LIBNCURSES_DIR_1 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include/ncursesw"
LIB_LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

MAKE_CFLAGS = "-I${INCLUDE_LIBREADLINE_DIR_1} -I${INCLUDE_LIBEVENT_DIR_2} -I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2}"
MAKE_LDFLAGS = "-L${LIB_LIBREADLINE_DIR} -L${LIB_LIBNCURSES_DIR}"

NOWARN = "-Wno-maybe-uninitialized"

python do_patch() {
    import gittools

    s = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')

    g = gittools.Git.for_dir(s)
    g.applyIfNot(s + '/src/options.c', '-x, --exec', fd + '/files/fzy.diff')
}

