SUMMARY = "e2fsprogs"
LICENSE = "CLOSED"
MODULE = "base/e2fsprogs"
SRCPV = "1"

inherit simpleconfigure encapsulate

DO_MAKE_COMPILE_WRITABLE = "True"
#DO_MAKE_COMPILE_WRITABLE_DEBUG = "True"

python do_patch() {
    import patcher
    import shutil

    s = d.getVar('S')

    p = patcher.Patcher(s + '/Makefile.in', None)
    p.replace('$(E2SCRUB_DIR)', '')
    p.write()

    if os.path.exists(s + '/scrub'):
        shutil.rmtree(s + '/scrub')
}
