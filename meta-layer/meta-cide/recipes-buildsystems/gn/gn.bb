SUMMARY = "ggnn"
LICENSE = "CLOSED"
MODULE = "buildsystems/gn"
SRCPV = "1"

inherit simpleninja encapsulate

SKIP_BUILD = 'True'

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "")
}
#do_check_need_build[depends] += "${DEPEND_TARGETS}"

#CMAKE_C_FLAGS = "${NOWARN}"
#CMAKE_CXX_FLAGS = "${NOWARN}"

python do_configure() {
    import fileutils

    s = d.getVar('S')
    b = d.getVar('B')

    fileutils.copy_files(s, b)

# --allow-warning if you want to build with warnings.
    cmd = "python build/gen.py"
    bb.warn(cmd)
    p = subprocess.Popen(cmd, shell=True, cwd=b, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    lines = p.stdout.readlines();
    retval = p.wait()
    p.stdout.close()
    if (retval):
        for line in lines:
            bb.plain(line.decode(encoding = 'utf-8', errors = 'ignore').rstrip())
        bb.error('CONFIGURE FAILED: ' + b)
        bb.warn('cd ' + b + ' && ' + cmd)
        sys.exit(retval)
}

