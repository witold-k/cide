SUMMARY = "emacs"
LICENSE = "CLOSED"
MODULE = "ide/emacs"
SRCPV = "1"

SKIP_BUILD = "True"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "glib gnutls libncurses libreadline slang zlib tree-sitter")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


INCLUDE_ZLIB_DIR = "${TOPDIR}/tmp/work/zlib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_ZLIB_DIR = "${TOPDIR}/tmp/work/zlib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_LIBREADLINE_DIR = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_LIBREADLINE_DIR = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_GLIB_DIR = "${TOPDIR}/tmp/work/glib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_GLIB_DIR = "${TOPDIR}/tmp/work/glib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_SLANG_DIR = "${TOPDIR}/tmp/work/slang${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_SLANG_DIR = "${TOPDIR}/tmp/work/slang${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_TREESITTER_DIR = "${TOPDIR}/tmp/work/tree-sitter${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_TREESITTER_DIR = "${TOPDIR}/tmp/work/tree-sitter${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_GNUTLS_DIR = "${TOPDIR}/tmp/work/gnutls${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_GNUTLS_DIR = "${TOPDIR}/tmp/work/gnutls${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_LIBNCURSES_DIR_1 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include/ncursesw"
LIB_LIBNCURSES_DIR = " ${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

NOWARN = " \
    -Wno-null-dereference \
    -Wno-maybe-uninitialized \
"
CONFIGURE_CFLAGS = "${NOWARN} -I${INCLUDE_LIBREADLINE_DIR} -I${INCLUDE_SLANG_DIR} -I${INCLUDE_GLIB_DIR} -I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2} -I${INCLUDE_ZLIB_DIR} -I${INCLUDE_TREESITTER_DIR}"
CONFIGURE_LDFLAGS = "-L${LIB_SLANG_DIR} -L${LIB_LIBREADLINE_DIR} -L${LIB_GLIB_DIR} -L${LIB_LIBNCURSES_DIR} -L${LIB_ZLIB_DIR} -L${LIB_TREESITTER_DIR}"

DEPENDENCIES = "\
TREE_SITTER_LIBS='-L${LIB_TREESITTER_DIR} -ltree-sitter' \
TREE_SITTER_CFLAGS='-I${INCLUDE_TREESITTER_DIR}' \
LIBGNUTLS_LIBS='-L${LIB_GNUTLS_DIR} -lgnutls' \
LIBGNUTLS_CFLAGS='-I${INCLUDE_GNUTLS_DIR}' \
"

CONFIGURE_OPTIONS = "\
--with-gnutls=yes --with-tree-sitter --with-xml2 --with-zlib \
--without-x --without-x-toolkit --without-sound --without-xpm \
--without-jpeg --without-tiff --without-gif --without-png --without-rsvg \
--without-imagemagick --without-xft --without-libotf --without-m17n-flt \
--without-xaw3d --without-toolkit-scroll-bars --without-gpm --without-dbus \
--without-gconf --without-gsettings \
--with-x-toolkit=no \
${DEPENDENCIES}\
"

CONFIGURE_PKG_CONFIG_PATH = "\
${LIB_SLANG_DIR}/pkgconfig:\
${LIB_GLIB_DIR}/pkgconfig:\
${LIB_TREESITTER_DIR}/pkgconfig\
${LIB_GNUTLS_DIR}/pkgconfig\
"

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}


CONFIGURE_LD_LIBRARY_PATH = "${LIB_ZLIB_DIR}:${LIB_TREESITTER_DIR}"

addtask emacs_rename
do_emacs_rename[nostamp] = "1"
do_emacs_rename[dirs] = "${S}"
do_emacs_rename() {
    cd ${INSTALL_DIR}/${TARGET_PREFIX_DIR}/bin
    EMACS=$(find . -name "emacs-*")
    if [ ! -z "$EMACS" ];
    then
        echo "RENAME $EMACS => emacs"
        rm emacs
        mv $EMACS emacs
    fi
}

python do_install:append() {
    bb.build.exec_func('do_emacs_rename', d)
}
