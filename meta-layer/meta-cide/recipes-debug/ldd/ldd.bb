SUMMARY = "ldd"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

inherit encapsulate

python do_populate() {
    import fileutils

    irf = d.getVar('IMAGE_ROOTFS')
    fileutils.copy_file('/usr/bin/ldd', irf + '/bin/wldd')
}
