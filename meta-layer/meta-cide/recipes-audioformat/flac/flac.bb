SUMMARY = "libflac"
LICENSE = "CLOSED"
MODULE = "audioformat/flac"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "ogg")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_EXE_LINKER_FLAGS = "-lm ${DEPEND_LIB_DIRS_SWITCH}"

CMAKE_OPTIONS = " \
    -DWITH_OGG=ON \
"

python do_patch() {
    import fileutils

    md = d.getVar('ROOT_DIR') + '/modulerefs/cmake'

    s = d.getVar('S')

    fileutils.copy_file_to_dir(md + '/FindOgg.cmake', s + '/cmake/')
}

