SUMMARY = "clipper2"
LICENSE = "CLOSED"
MODULE = "base/clipper2"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "googletest")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


S = "${SOURCE_DIR}/CPP"

CMAKE_C_FLAGS = "\
    -Wno-unused-result \
    -Wno-sequence-point \
    -Wno-sign-compare \
    -Wno-unused-parameter \
"

CMAKE_CXX_FLAGS = "\
    -Wno-unused-result \
    -Wno-sequence-point \
    -Wno-sign-compare \
    -Wno-unused-parameter \
"

python do_patch() {
    import patcher
    s = d.getVar('S')

    p = patcher.Patcher(s + '/Clipper2Lib/include/clipper2/clipper.export.h', None)
    p.replace('return *reinterpret_cast<T1*>(&value);', 'void * tmp = (void*)&value; return *reinterpret_cast<T1*>(tmp);')
    p.write()
}

python do_configure:prepend() {
    copt_init = "-DUSE_EXTERNAL_GTEST=ON"
    if  d.getVar('IS_NATIVE') != True:
        copt_init += ' -DCLIPPER2_TESTS=OFF'
    d.setVar('CMAKE_OPTIONS', copt_init)
}

