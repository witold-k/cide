S1UMMARY = "http-parser"
LICENSE = "CLOSED"
MODULE = "base/http-parser"
SRCPV = "1"

inherit simplemake encapsulate


python do_compile:prepend() {
    if d.getVar('IS_NATIVE') != True:
        d.setVar('MAKE_MAKE_OPTIONS', 'HELPER=true')
}

