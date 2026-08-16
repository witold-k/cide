SUMMARY = "abook"
LICENSE = "CLOSED"
MODULE = "ide/abook"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses libreadline")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

LIBREADLINE_DIR = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
INCLUDE_LIBREADLINE_DIR_1 = "${LIBREADLINE_DIR}/include"
INCLUDE_LIBREADLINE_DIR_2 = "${LIBREADLINE_DIR}/include/readline"
LIB_LIBREADLINE_DIR = "${LIBREADLINE_DIR}/lib"

LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
INCLUDE_LIBNCURSES_DIR_1 = "${LIBNCURSES_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${LIBNCURSES_DIR}/include/ncursesw"
LIB_LIBNCURSES_DIR = "${LIBNCURSES_DIR}/lib"

NOWARN = "\
-Wno-discarded-qualifiers \
-Wno-format-truncation \
-Wno-stringop-overflow \
-Wno-unused-but-set-variable \
"

CONFIGURE_CFLAGS = " \
    -I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2} -I${INCLUDE_LIBREADLINE_DIR_1} -I${INCLUDE_LIBREADLINE_DIR_2} \
    -DHAVE_READLINE_READLINE_H -DHAVE_READLINE_HISTORY_H -DHAVE_DECL_WCWIDTH \
    ${NOWARN} \
"
CONFIGURE_CPPFLAGS = "${CONFIGURE_CFLAGS}"
CONFIGURE_LDFLAGS = "-L${LIB_LIBREADLINE_DIR} -L${LIB_LIBNCURSES_DIR} "
CONFIGURE_LIBS = "-lreadline -lncursesw"

CONFIGURE_OPTIONS = "\
    --with-readline=${LIBREADLINE_DIR} \
    --with-curses=${LIBNCURSES_DIR} \
"

python do_patch() {
    bb.build.exec_func('do_autotools_full_autoreconf', d)
}

python do_configure:append() {
    import patcher

    b = d.getVar('B')

    p = patcher.Patcher(b + '/config.h', None)
    p.replace('HAVE_DECL_WCWIDTH', 'SKIP_DECL_WCWIDTH')
    p.write()
}

