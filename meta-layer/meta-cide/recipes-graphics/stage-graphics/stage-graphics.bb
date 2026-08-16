inherit stage

SUMMARY = "wait till recipes-graphics is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    cairo \
    glfw \
    harfbuzz \
    libglvnd \
    librsvg \
    libvips \
    opencl-headers \
    opencl-icd-loader \
    pango \
    pixman \
    wayland \
    wayland-protocols \
    xwrap \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

