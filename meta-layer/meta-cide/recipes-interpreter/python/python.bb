SUMMARY = "python"
LICENSE = "CLOSED"
MODULE = "interpreter/cpython"
SRCPV = "1"

SKIP_BUILD = 'True'

inherit simpleconfigure encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "util-linux openssl")
    BD.native_depends(d, "python")
}
do_check_need_build[depends] += "${DEPEND_TARGETS} ${NATIVE_DEPEND_TARGETS}"

CONFIGURE_DEEPCOPY_SRC  = "true"
CONFIGURE_CONFIGURE_DIR = "${B}"

CONFIGURE_COMMON   = "${DEPEND_INCLUDE_DIRS_SWITCH}"
CONFIGURE_CFLAGS   = "${CONFIGURE_COMMON}"
CONFIGURE_CXXFLAGS = "${CONFIGURE_COMMON}"
CONFIGURE_LDFLAGS  = "${DEPEND_LIB_DIRS_SWITCH}"

PYTHON_NATIVE_INSTALL_DIR = "${TOPDIR}/tmp/work/python${NATIVE_TARGET_PREFIX}-1.0-r0/install/${NATIVE_TARGET}/${NATIVE_PREFIX_DIR}"
PYTHON_BUILD_DIR          = "${TOPDIR}/tmp/work/python${NATIVE_TARGET_PREFIX}-1.0-r0/build/${NATIVE_TARGET}"
BUILD_PYTHON              = "${PYTHON_BUILD_DIR}/src"
INSTALLED_PYTHON          = "${PYTHON_NATIVE_INSTALL_DIR}/bin"

PYTHONHOME = "${PYTHON_NATIVE_INSTALL_DIR}"
PYTHONPATH_BASE = "${PYTHONHOME}/lib/python3.15"
PYTHONPATH = "\
${PYTHONPATH_BASE}:${PYTHONPATH_BASE}/site-packages:${PYTHONPATH_BASE}/lib-dynload\
"

python do_patch() {
    import patcher

    s = d.getVar('S')
    pyh = d.getVar('PYTHONHOME')
    pyp = d.getVar('PYTHONPATH')

    p = patcher.Patcher(s + '/configure', '=')
    p.replace('PYTHONPATH=$(srcdir)/Lib', 'PYTHONPATH=' + pyp)
    p.set('build_python_ver', '$PACKAGE_VERSION')
    p.write()

    p = patcher.Patcher(s + '/Makefile.pre.in', None)
    p.insert_lines_after('BUILDEXE=	@BUILDEXEEXT@', [ \
        'NATIVE_PYTHONPATH=' + pyp, \
        'NATIVE_PYTHONHOME=' + pyh, \
    ])
    p.replace('\t$(FREEZE_MODULE_BOOTSTRAP)', '\tPYTHONPATH=$(NATIVE_PYTHONPATH) PYTHONHOME=$(NATIVE_PYTHONHOME) $(FREEZE_MODULE_BOOTSTRAP)')
    p.replace('\t$(FREEZE_MODULE)', '\tPYTHONPATH=$(NATIVE_PYTHONPATH) PYTHONHOME=$(NATIVE_PYTHONHOME) $(FREEZE_MODULE)')
    p.replace('\t$(PYTHON_FOR_BUILD)', '\tPYTHONPATH=$(NATIVE_PYTHONPATH) PYTHONHOME=$(NATIVE_PYTHONHOME) $(PYTHON_FOR_BUILD)')
    p.replace('$(RUNSHARED) $(PYTHON_FOR_BUILD)', '$(RUNSHARED) PYTHONPATH=$(NATIVE_PYTHONPATH) PYTHONHOME=$(NATIVE_PYTHONHOME) $(PYTHON_FOR_BUILD)')
    p.write()
}

python do_configure:prepend() {
    import patcher

    ca  = d.getVar('CURRENT_ARCH')
    pyh = d.getVar('PYTHONHOME')
    pyp = d.getVar('PYTHONPATH')

    is_native = d.getVar('IS_NATIVE')
    if not is_native and ca != None:
        os.environ['PYTHONHOME'] = pyh
        os.environ['PYTHONPATH'] = pyp

        os.environ['CONFIG_SITE'] = d.getVar('FILE_DIRNAME') + '/files/config.site-aarch64'
        py_bin = d.getVar('INSTALLED_PYTHON') + '/python3.15'
        d.setVar('CONFIGURE_OPTIONS', '--with-build-python="' + py_bin + '" --build=' + ca)
}

python do_compile:prepend() {
    ca = d.getVar('CURRENT_ARCH')
    pyh = d.getVar('PYTHONHOME')
    pyp = d.getVar('PYTHONPATH')

    is_native = d.getVar('IS_NATIVE')
    if not is_native and ca != None:
        os.environ['PYTHONHOME'] = pyh
        os.environ['PYTHONPATH'] = pyp

        py_path = 'PYTHONPATH=' + pyp
        py_home = 'PYTHONHOME=' + pyh
        d.setVar('CONFIGURE_MAKE_OPTIONS', py_path + ' ' + py_home)
}

python do_install:prepend() {
    ca = d.getVar('CURRENT_ARCH')
    pyh = d.getVar('PYTHONHOME')
    pyp = d.getVar('PYTHONPATH')

    is_native = d.getVar('IS_NATIVE')
    if not is_native and ca != None:
        os.environ['PYTHONHOME'] = pyh
        os.environ['PYTHONPATH'] = pyp

        py_path = 'PYTHONPATH=' + pyp
        py_home = 'PYTHONHOME=' + pyh
        d.setVar('CONFIGURE_MAKE_OPTIONS', py_path + ' ' + py_home)
}

