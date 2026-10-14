SUMMARY = "fribidi"
LICENSE = "CLOSED"
MODULE = "graphicimage/fribidi"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


NOWARN = '\
-Wno-format-overflow \
'

# MESON_C_FLAGS   = ''
# MESON_CXX_FLAGS = ''
# MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

MESON_OPTIONS = " \
-Ddocs=false \
"
