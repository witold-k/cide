SUMMARY = "fribidi"
LICENSE = "CLOSED"
MODULE = "graphicimage/fribidi"
SRCPV = "1"

inherit simplemeson encapsulate

NOWARN = '\
-Wno-format-overflow \
'

# MESON_C_FLAGS   = ''
# MESON_CXX_FLAGS = ''
# MESON_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

MESON_OPTIONS = " \
-Ddocs=false \
"
