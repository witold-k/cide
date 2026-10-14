SUMMARY = "meson"
LICENSE = "CLOSED"
MODULE = "buildsystems/meson"
SRCPV = "1"

inherit populate

python do_install() {
    import fileutils

    s = d.getVar('S')
    os.chdir(s)

    inst = d.getVar('INSTALL_DIR') + '/' + d.getVar('TARGET_PREFIX_DIR')
    inst_root = inst + '/opt/buildsystems/meson'
    fileutils.copy_files(s, inst_root)
    fileutils.rel_link(inst_root + '/meson.py', inst + '/bin/meson')
    fileutils.rel_link(inst_root + '/meson.py', inst + '/wrapper/bin/meson')
}
