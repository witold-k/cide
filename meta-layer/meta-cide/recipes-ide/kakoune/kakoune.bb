SUMMARY = "kakoune"
LICENSE = "CLOSED"
MODULE = "ide/kakoune"
SRCPV = "1"

inherit simplemake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses libreadline")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

INCLUDE_LIBREADLINE_DIR_1 = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_LIBREADLINE_DIR = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-2.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_LIBNCURSES_DIR_1 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include/ncursesw"
LIB_LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

NOERRORFLAGS = "\
    -Wno-array-bounds \
    -Wno-dangling-reference \
    -Wno-delete-non-virtual-dtor \
    -Wno-init-list-lifetime \
    -Wno-maybe-uninitialized \
    -Wno-stringop-overflow \
    -Wno-unused-but-set-variable \
    -Wno-unused-result \
"

COMMON_MAKE_CFLAGS = "${NOERRORFLAGS} -I${INCLUDE_LIBREADLINE_DIR_1} -I${INCLUDE_LIBEVENT_DIR_2} -I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2}"
MAKE_CFLAGS   = "${COMMON_MAKE_CFLAGS}"
MAKE_CXXFLAGS = "${COMMON_MAKE_CFLAGS}"
MAKE_LDFLAGS = "-L${LIB_LIBREADLINE_DIR} -L${LIB_LIBNCURSES_DIR}"

