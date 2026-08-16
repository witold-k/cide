SUMMARY = "word3vec"
LICENSE = "CLOSED"
MODULE = "nlp/word2vec"
SRCPV = "1"

inherit simplemake encapsulate

S = "${SOURCE_DIR}/src"

MAKE_CFLAGS = "\
    -funroll-loops \
    -Wno-unused-variable \
    -lm \
"

python do_patch() {
    import patcher
    s = d.getVar('S')
    p = patcher.Patcher(s + '/makefile', None)
    p.delete_lines('CC =')
    p.delete_lines('CFLAGS =')
    p.delete_lines('SCRIPTS_DIR')
    p.replace('BIN_DIR=../bin', 'BIN_DIR=.')
    p.write()
}

python do_install() {
    bb.warn("need to be inplemented")
}

