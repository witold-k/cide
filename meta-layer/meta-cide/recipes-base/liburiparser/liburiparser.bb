SUMMARY = "liburiparser"
LICENSE = "CLOSED"
MODULE = "base/uriparser"
SRCPV = "1"

inherit simplecmake encapsulate

CMAKE_OPTIONS = "\
    -DURIPARSER_BUILD_DOCS:BOOL=OFF\
"
