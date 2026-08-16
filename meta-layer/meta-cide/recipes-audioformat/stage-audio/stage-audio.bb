inherit stage

SUMMARY = "wait till recipes-audio is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    libao \
    flac \
    ogg \
    oggz \
    libopus \
    librav1e \
    libspeex \
    libvorbis \
"

# libxspf

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

