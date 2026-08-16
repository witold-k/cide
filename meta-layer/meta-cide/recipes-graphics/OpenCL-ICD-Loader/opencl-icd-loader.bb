SUMMARY = "opencl icd-loader"
LICENSE = "CLOSED"
MODULE = "graphics/OpenCL-ICD-Loader"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "opencl-headers")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = "\
    -Wno-calloc-transposed-args \
"

