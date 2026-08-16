SUMMARY = "libreadline"
LICENSE = "CLOSED"
MODULE = "base/readline"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
INCLUDE_LIBNCURSES_DIR_1 = "${LIBNCURSES_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${LIBNCURSES_DIR}/include/ncursesw"

CONFIGURE_CFLAGS = "-I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2}"
CONFIGURE_CXXFLAGS = "${CONFIGURE_CFLAGS}"
CONFIGURE_LDFLAGS = "-L${LIB_LIBNCURSES_DIR} "
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

CONFIGURE_OPTIONS = "\
    --with-curses=${LIB_LIBNCURSES_DIR} \
"
