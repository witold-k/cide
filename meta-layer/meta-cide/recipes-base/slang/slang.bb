SUMMARY = "slang"
LICENSE = "CLOSED"
MODULE = "base/slang"
SRCPV = "1"

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libpng zlib")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CONFIGURE_COPY_SRC = "1"
CONFIGURE_CONFIGURE_DIR = "${B}"

INCLUDE_ZLIB_DIR = "${TOPDIR}/tmp/work/zlib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_ZLIB_DIR = "${TOPDIR}/tmp/work/zlib${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

INCLUDE_PNG_DIR = "${TOPDIR}/tmp/work/libpng${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
LIB_PNG_DIR = "${TOPDIR}/tmp/work/libpng${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"

CONFIGURE_COMMON   = "-I${INCLUDE_ZLIB_DIR} -I${INCLUDE_PNG_DIR}"
CONFIGURE_CFLAGS   = "${CONFIGURE_COMMON}"
CONFIGURE_CXXFLAGS = "${CONFIGURE_COMMON}"
CONFIGURE_LDFLAGS  = "-Wl,-rpath=${LIB_PNG_DIR}:${LIB_ZLIB_DIR} -Wl,-rpath-link=${LIB_PNG_DIR}:${LIB_ZLIB_DIR} -L${LIB_PNG_DIR} -L${LIB_ZLIB_DIR}"

