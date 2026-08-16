SUMMARY = "glove"
LICENSE = "CLOSED"
MODULE = "nlp/glove"
SRCPV = "1"

inherit simplemake encapsulate

MAKE_CFLAGS = "\
    -funroll-loops \
    -lm \
"

python do_patch() {
    import patcher
    s = d.getVar('S')
    p = patcher.Patcher(s + '/Makefile', None)
    p.delete_lines('CC =')
    p.delete_lines('CFLAGS =')
    p.write()
}

python do_install() {
    bb.warn("need to be inplemented")
}

