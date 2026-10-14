SUMMARY = "libopus"
LICENSE = "CLOSED"
MODULE = "audioformat/opus"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"


# FIXME enable options, process autogen to download
# use new wger downloader and create link

# python do_patch() {
#     import bbtools
#     bb.warn('autogen')
#     bbtools.do_autogen(bb, d.getVar('S'))
# }
