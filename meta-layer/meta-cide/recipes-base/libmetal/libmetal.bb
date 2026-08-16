SUMMARY = "libmetal"
LICENSE = "CLOSED"
MODULE = "base/libmetal"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "sysfsutils")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


LIBMETAL_MACHINE ?= "zynqmp_a53"
SOC_FAMILY ?= "zynqmp"
ZYNQMP_OPTIONS = "-DWITH_VFIO=on"

CMAKE_OPTIONS = " \
    -DWITH_TESTS=OFF \
    -DWITH_STATIC_LIB=ON \
    -DWITH_SHARED_LIB=OFF \
    -DMACHINE=${LIBMETAL_MACHINE} \
    ${ZYNQMP_OPTIONS} \
    -DSOC_FAMILY=${SOC_FAMILY} \
"

python do_patch() {
    import fileutils
    s = d.getVar('S')

    fileutils.remove_file(s + '/cmake/modules/FindLibSysFS.cmake')
}
