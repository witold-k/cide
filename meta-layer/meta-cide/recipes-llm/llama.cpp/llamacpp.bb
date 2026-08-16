SUMMARY = "llama_cpp"
LICENSE = "CLOSED"
MODULE = "llm/llama_cpp"
SRCPV = "1"

inherit simplecmake encapsulate

SKIP_CROSS = 'True'

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, " \
        openssl \
    ")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

export CUDA_HOME = "${CUDA_HOME}"
export PATH .= ":${CUDA_HOME}/bin"
export LD_LIBRARY_PATH .= ":${CUDA_HOME}/lib64"
export CUDACXX = "${CUDA_HOME}/bin/nvcc"
export CMAKE_CUDA_COMPILER = "${CUDA_HOME}/bin/nvcc"

CMAKE_CUDA_FLAGS = ' \
    -D__NO_MATH_INLINE \
    -D__NO_MATH_INLINES \
    -D_FORCE_INLINES \
    -ccbin=${COMPILER_PREFIX}sysroot_g++ \
'

CMAKE_OPTIONS = ' \
    -DGGML_CCACHE=OFF \
    -DGGML_CUDA=ON \
    -DLLAMA_BUILD_WEBUI=OFF \
    -DLLAMA_BUILD_UI=OFF \
    -DHOST_CXX_COMPILER="${COMPILER_PREFIX}sysroot_g++" \
    -DCUDAToolkit_ROOT=${CUDA_HOME} \
    -DCMAKE_CUDA_FLAGS="${CMAKE_CUDA_FLAGS}" \
'

NOWARN = "\
    -fpermissive \
    -Wno-unused-parameter \
"

CMAKE_CXX_FLAGS = "\
"

CMAKE_EXE_LINKER_FLAGS = "${DEPEND_LIB_DIRS_SWITCH} -L${CUDA_HOME}/lib64 \
    -lcudart \
    -lcublas \
    -lcublasLt \
"

python do_patch() {
    import patcher

    bb.warn('CUDA_HOME=' + d.getVar('CUDA_HOME'))
    bb.warn('COMPILER_PREFIX=' + d.getVar('COMPILER_PREFIX'))

    s = d.getVar('S')
    p = patcher.Patcher(s + '/vendor/cpp-httplib/httplib.cpp', None)
    p.replace('static_cast<cert_t>(cert)', '(cert_t)(cert)')
    p.replace('static_cast<cert_t>(x509)', '(cert_t)(x509)')
    p.write()

    p = patcher.Patcher(s + '/ggml/src/ggml-cuda/CMakeLists.txt', None)
    p.replace('ggml_get_flags(${CUDA_CCID} ${CUDA_CCVER})', 'ggml_get_flags(${CUDA_CCID} GGML_CUDA_FLAGS GGML_CUDA_DEFINES)')
    p.write()
}

python do_install:append() {
    import os
    import glob
    import fileutils

    inst = d.getVar('INSTALL_DIR')
    tp    = d.getVar('TARGET_PREFIX_DIR')
    dest = inst + '/' + tp + '/bin'

    build_bin = os.path.join(d.getVar('B'), 'bin')
    for f in glob.glob(os.path.join(build_bin, 'llama-*')):
        if os.path.isfile(f):
            df = os.path.join(dest, os.path.basename(f))
            fileutils.copy_file(f, df)
            os.chmod(df, 0o755)
}

