SUMMARY = "libcpptest"
LICENSE = "CLOSED"
MODULE = "base/cpptest"
SRCPV = "1"

inherit simpleconfigure encapsulate

python do_patch() {
    bb.build.exec_func('do_autotools_autogen', d)
}
