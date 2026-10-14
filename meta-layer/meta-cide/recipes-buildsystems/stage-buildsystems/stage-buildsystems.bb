inherit stage

SUMMARY = "wait till recipes-buildsystems is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    bazel \
    bitbake \
    cmake \
    conan \
    gn \
    meson \
    ninja \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.get("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

