SUMMARY = "taskwarrior"
LICENSE = "CLOSED"
MODULE = "ide/taskwarrior"
SRCPV = "1"

SKIP_BUILD = "True"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libncurses libreadline")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

