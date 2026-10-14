S1UMMARY = "p11-kit"
LICENSE = "CLOSED"
MODULE = "base/p11-kit"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson libtasn1 libffi")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

FFI_DIR = "${TOPDIR}/tmp/work/libffi${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}"
LIB_FFI_FLAGS = "-L${FFI_DIR}/lib64 -Wl,-rpath=${FFI_DIR}/lib64 -Wl,-rpath-link=${FFI_DIR}/lib64"

MESON_LDFLAGS = "${LIB_FFI_FLAGS}"

