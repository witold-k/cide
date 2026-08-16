inherit simplecmake encapsulate

SUMMARY = "xpdf"
LICENSE = "CLOSED"
MODULE = "ide/xpdf-4.00"
SRCPV = "1"

python () {
    from bbdepend import BBDepend as BD
    BD.native_depends(d, 'xpdf')
    #BD.depends(d, d.getVar('DEPEND_LIST'))
}
#do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

python do_patch() {
    import patcher

    s = d.getVar('S')

    p = patcher.Patcher(s + '/goo/gmem.h', None)
    p.replace('GMEM_EXCEP throw(GMemException)', 'GMEM_EXCEP noexcept')
    p.write()
}

CMAKE_OPTIONS = " \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
"

NOWARN = " \
    -Wno-array-bounds \
    -Wno-implicit-fallthrough \
    -Wno-maybe-uninitialized \
    -Wno-missing-field-initializers \
    -Wno-shift-negative-value \
    -Wno-stringop-overflow \
    -Wno-unused-parameter \
    -Wno-write-strings \
"

NOWARN_CXX = " \
    -Wno-terminate \
"

CMAKE_CXX_FLAGS = "${NOWARN_CXX}"

