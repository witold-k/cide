SUMMARY = "bzip2"
LICENSE = "CLOSED"
MODULE = "compress/bzip2"
SRCPV = "1"

inherit simplecmake encapsulate

CMAKE_C_FLAGS = "\
    -DBZ_DEBUG=0 \
    -Wno-conversion \
    -Wno-implicit-fallthrough \
    -Wno-sign-conversion \
    -Wno-unused-parameter \
    -Wno-noreturn \
    -Wno-error \
"

