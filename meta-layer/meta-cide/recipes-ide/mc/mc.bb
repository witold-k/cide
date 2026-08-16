SUMMARY = "mc"
LICENSE = "CLOSED"
MODULE = "ide/mc"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "glib libffi libncurses libreadline pcre2 slang e2fsprogs zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

INCLUDE_LIBREADLINE_DIR = "${TOPDIR}/tmp/work/libreadline${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"

INCLUDE_SLANG_DIR = "${TOPDIR}/tmp/work/slang${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"

INCLUDE_GLIB_DIR = "${TOPDIR}/tmp/work/glib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"

INCLUDE_E2FS_DIR = "${TOPDIR}/tmp/work/e2fsprogs${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"

LIBNCURSES_DIR = "${TOPDIR}/tmp/work/libncurses${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
INCLUDE_LIBNCURSES_DIR_1 = "${LIBNCURSES_DIR}/include"
INCLUDE_LIBNCURSES_DIR_2 = "${LIBNCURSES_DIR}/include/ncursesw"

LIBPCRE2_DIR = "${TOPDIR}/tmp/work/pcre2${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
INCLUDE_LIBPCRE2_DIR = "${LIBPCRE2_DIR}/include"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"

CONFIGURE_CFLAGS = "-I${INCLUDE_SLANG_DIR} -I${INCLUDE_E2FS_DIR} -I${INCLUDE_LIBREADLINE_DIR} -I${INCLUDE_LIBEVENT_DIR_2} -I${INCLUDE_LIBNCURSES_DIR_1} -I${INCLUDE_LIBNCURSES_DIR_2} -I${INCLUDE_GLIB_DIR} -I${INCLUDE_GLIB_DIR2} -I${INCLUDE_GLIB_DIR}/glib-2.0 -I${LIB_GLIB_DIR}/glib-2.0/include -I${INCLUDE_LIBPCRE2_DIR}"
CONFIGURE_LDFLAGS = "${DEPEND_LIB_DIRS_SWITCH} ${LIB_FFI_FLAGS}"

NOWARN = " \
 -Wno-implicit-function-declaration \
 -Wno-nested-externs \
 -Wno-format \
 -Wno-missing-prototypes \
"

addtask mc_patch_script
do_mc_patch_script() {
#    if ! cmp -s ${FILE_DIRNAME}/files/mc-ext2fs-attr.m4 ${S}/m4.include/mc-ext2fs-attr.m4;
#    then
#        cp ${FILE_DIRNAME}/files/mc-ext2fs-attr.m4 ${S}/m4.include/
#    fi
    if grep -s 'ENABLE_EXT2FS_ATTR' src/filemanager/Makefile.am;
    then
        sed -i '/if/d' src/filemanager/Makefile.am;
        sed -i '/endif/d' src/filemanager/Makefile.am;
    fi
    if grep -s 'ifdef ENABLE_EXT2FS_ATTR' src/filemanager/info.c;
    then
        sed -i 's/ifdef ENABLE_EXT2FS_ATTR/if 1/g' src/filemanager/info.c;
    fi
    if grep -s 'ifdef ENABLE_EXT2FS_ATTR' src/keymap.h;
    then
        sed -i 's/ifdef ENABLE_EXT2FS_ATTR/if 1/g' src/keymap.h;
    fi
    if grep -s 'ifdef ENABLE_EXT2FS_ATTR' src/keymap.c;
    then
        sed -i 's/ifdef ENABLE_EXT2FS_ATTR/if 1/g' src/keymap.c;
    fi
}

python do_patch() {
    bb.build.exec_func('do_mc_patch_script', d)
    bb.build.exec_func('do_autotools_autogen', d)
}

addtask mc_export
do_mc_export[nostamp] = "1"
do_mc_export[dirs] = "${S}"
do_mc_export() {
    export GLIB_LIBS="${DEPEND_NATIVE_LIB_DIRS_SWITCH} -lgio-2.0 -lgmodule-2.0 -lgobject-2.0 -lglib-2.0 -lpcre2-8 -le2p -lffi"
    export GLIB_CFLAGS="-I${INCLUDE_GLIB_DIR} -I${INCLUDE_GLIB_DIR}/glib-2.0 -I${LIB_GLIB_DIR}/lib/glib-2.0/include -I${LIB_GLIB_DIR}/glib-2.0/include"
}

python do_configure:prepend() {
    bb.build.exec_func('do_mc_export', d)
}

python do_compile:prepend() {
    os.environ['GLIB_LIBS'] = "${DEPEND_NATIVE_LIB_DIRS_SWITCH} -lgio-2.0 -lgmodule-2.0 -lgobject-2.0 -lglib-2.0"
    gd = d.getVar('INCLUDE_GLIB_DIR')
    os.environ['GLIB_CFLAGS'] = '-I' + gd + ' -I' + gd + '/glib-2.0 -I' + gd + '/lib/glib-2.0/include -I' + gd + '/glib-2.0/include'
    bb.warn(gd)
}

