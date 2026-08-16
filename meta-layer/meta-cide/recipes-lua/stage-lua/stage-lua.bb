inherit stage

SUMMARY = "wait till recipes-ide is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    libmpack-lua \
    lpeg \
    lua \
    luabitop \
    luajit \
    luv \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

