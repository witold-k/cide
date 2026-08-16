S1UMMARY = "gmp"
LICENSE = "CLOSED"
MODULE = "math/gmp"
SRCPV = "1"

inherit simpleconfigure encapsulate

CONFIGURE_COPY_SRC      = "true"
CONFIGURE_BUILD_DIR     = "${B}"
CONFIGURE_CONFIGURE_DIR = "${B}"

# new URL?
# git clone https://gmplib.org/repo/gmp.git
# git clone https://github.com/gmp-mirror/gmp.git

CONFIGURE_OPTIONS = '--enable-maintainer-mode'

python do_patch() {
    bb.build.exec_func('do_autotools_full_autoreconf', d)
    # bb.build.exec_func('do_autotools_bootstrap', d)
}
