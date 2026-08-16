inherit populate

SUMMARY = "some scripts"
LICENSE = "CLOSED"
MODULE = "ide/scripts"
SRCPV = "1"

python do_install() {
    import fileutils

    s     = d.getVar('S')
    instd = d.getVar('INSTALL_DIR')

    fileutils.copy_files(s, instd + '/bin-scripts')
}
