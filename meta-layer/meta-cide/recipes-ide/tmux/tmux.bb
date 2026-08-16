SUMMARY = "tmux"
LICENSE = "CLOSED"
MODULE = "ide/tmux"
SRCPV = "1"

inherit simpleconfigure encapsulate
#inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libevent libncurses libsixel")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"
CONFIGURE_OPTIONS       = "--enable-sixel --disable-systemd"

INCLUDE_LIBNCURSES_DIR_1 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include/ncursesw"
LIB_LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"


NOWARN = " \
    -Wno-implicit-function-declaration \
    -Wno-incompatible-pointer-types \
    -Wno-maybe-uninitialized \
    -Wno-pointer-sign \
    -Wno-shadow \
    -Wno-unused-variable \
    -Wno-use-after-free \
    -Wno-missing-prototypes \
    -Wno-missing-declarations \
"

#    -DHAVE_TREE_H=1
#    -DHAVE_QUEUE_H=1
#    -DHAVE_BITSTRING_H=1
#    -DHAVE_LIBUTIL_H=1
#    -DHAVE_UTIL_H=1
#    -DHAVE_FORKPTY=1
COMMON_C_FLAGS = " \
    -I${S} \
    -DENABLE_SIXEL=1 \
    -DHAVE_EVENT2_EVENT_H=1 \
    -DHAVE_PTY_H=1 \
    -DHAVE_FLOCK=1 \
    -DHAVE_FORKPTY=1 \
    -DHAVE_FDFORKPTY=1 \
    -DOK=0 \
    -I${INCLUDE_LIBNCURSES_DIR_2} \
"

CMAKE_C_FLAGS = "${COMMON_C_FLAGS} -DTMUX_VERSION="\\\"3.2a\\\"" "

CONFIGURE_CFLAGS = "${COMMON_C_FLAGS}"
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

python do_patch() {
    import fileutils
    import patcher

    s  = d.getVar('S')
    fd = d.getVar('FILE_DIRNAME')

    fileutils.copy_file(fd + '/files/CMakeLists.txt', s + '/CMakeLists.txt')

    p = patcher.Patcher(s + "/configure.ac", None)
    p.replace('$found_forkpty', 'yes')
    p.write()

    bb.build.exec_func('do_autotools_autogen', d)
}
