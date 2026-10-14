SUMMARY = "bitbake"
LICENSE = "CLOSED"
MODULE = "buildsystems/bitbake"
SRCPV = "1"

inherit populate

python do_install() {
    import fileutils

    s = d.getVar('S')
    os.chdir(s)

    inst = d.getVar('INSTALL_DIR') + '/' + d.getVar('TARGET_PREFIX_DIR')
    inst_root = inst + '/opt/buildsystems/bitbake'
    fileutils.copy_files(s, inst_root)
    fileutils.rel_link(inst_root + '/bin/bitbake', inst + '/bin/bitbake')
    fileutils.rel_link(inst_root + '/bin/bitbake', inst + '/wrapper/bin/bitbake')
}
