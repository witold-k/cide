SUMMARY = "libncurses"
LICENSE = "CLOSED"
MODULE = "base/libncurses"
SRCPV = "1"

inherit simpleconfigure encapsulate

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_CFLAGS = "-fPIC -DNCURSES_ENABLE_STDBOOL_H=1"
CONFIGURE_OPTIONS = "--enable-widec --with-shared --disable-stripping --enable-termcap"

python do_patch() {
    bb.build.exec_func('do_autotools_autoconf', d)
}

addtask make_link
do_make_link[nostamp] = "1"
do_make_link[dirs] = "${S}"
do_make_link() {
    cd ${INSTALL_DIR}/${TARGET_PREFIX_DIR}/lib
    if [ -f libncursesw.a ];
    then
        ln -sf libncursesw.a libncurses.a
    fi
}

python do_install:append() {
    bb.build.exec_func('do_make_link', d)
}
