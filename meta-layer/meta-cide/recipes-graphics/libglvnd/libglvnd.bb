SUMMARY = "libglvnd"
LICENSE = "CLOSED"
MODULE = "graphics/libglvnd"
SRCPV = "1"

inherit simplemeson encapsulate

MESON_OPTIONS = "-Dx11=disabled"

