SUMMARY = "openssl"
LICENSE = "CLOSED"
MODULE = "crypt/openssl"
SRCPV = "1"

inherit simpleconfigure encapsulate

CONFIGURE_COMMAND = "Configure"
CONFIGURE_IGNORE_HOST = "True"

python do_configure:prepend() {
    apply_meson_properties(d.getVar('TOOLCHAIN'), d)

    tp = d.getVar('TOOLCHAIN_PREFIX') or ''
    target = d.getVar('CURRENT_TARGET')

    cc = None

    if tp == 'aarch64-unknown-linux-gnu-': cc = 'Configure linux-aarch64'
    elif tp == 'arm-unknown-linux-gnueabihf-': cc = 'Configure linux-armv4'
    elif tp == 'i686-w64-mingw32-': cc = 'Configure mingw'
    elif tp == 'x86_64-w64-mingw32-': cc = 'Configure mingw64'
    elif tp == 'x86_64-unknown-linux-gnu': cc = 'Configure linux-x86_64'
    else:
        if d.getVar('IS_NATIVE') == True:
            cc = 'Configure'
        else:
            raise Exception('no configuration found: ' + target + '/'+ tp)

    bb.warn('Openssl: ' + cc)
    d.setVar('CONFIGURE_COMMAND', cc)
}
