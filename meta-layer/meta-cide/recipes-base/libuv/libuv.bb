SUMMARY = "libuv"
LICENSE = "CLOSED"
MODULE = "base/libuv"
SRCPV = "1"

inherit simplecmake encapsulate

CMAKE_C_FLAGS = "\
    -Wno-dangling-pointer \
    -Wno-discarded-qualifiers \
    -Wno-format-truncation \
    -Wno-maybe-uninitialized \
    -Wno-sign-conversion \
    -Wno-stringop-overread \
    -Wno-unused-result \
    -Wno-return-type \
"
