SUMMARY = "libdb"
LICENSE = "CLOSED"
MODULE = "base/libdb"
SRCPV = "1"

inherit simpleconfigure encapsulate

# --with-mutex: dist/aclocal/mutex.m4
CONFIGURE_OPTIONS = " \
    --enable-pthread_api \
    --with-mutex=POSIX/pthreads \
"
CONFIGURE_COPY_SRC      = "true"
CONFIGURE_BUILD_DIR     = "${B}/src"
CONFIGURE_CONFIGURE_DIR = "${B}/dist"

CONFIGURE_CFLAGS = " \
    -Wno-old-style-definition \
    -Wno-incompatible-pointer-types \
"

python do_patch() {
    import patcher

    bb.build.exec_func('do_autotools_autogen', d)

    s = d.getVar('S')

    p = patcher.Patcher(s + '/dist/Makefile.in', None)
    p.replace(' install_docs', '')
    p.write()
}

python do_install:append() {
    instd = d.getVar('INSTALL_DIR')
    os.system('chmod -R +w ' + instd)
}
