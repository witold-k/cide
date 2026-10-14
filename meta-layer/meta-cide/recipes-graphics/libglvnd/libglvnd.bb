SUMMARY = "libglvnd"
LICENSE = "CLOSED"
MODULE = "graphics/libglvnd"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


MESON_OPTIONS = "-Dx11=disabled"

