SUMMARY = "create encapsulated root fs"
MODULE = "(none)"
SRCPV = "1"
WRAPPER_LIB_DIR  = "${IMAGE_ROOTFS}/wrapper/lib"
WRAPPER_INCLUDE_DIR  = "${IMAGE_ROOTFS}/wrapper/include"

LEFT  = "{"
RIGHT = "}"

DEPEND_LIST = " \
    stage-audio \
    stage-base \
    stage-compress \
    stage-documents \
    stage-graphicimage \
    stage-graphics \
    stage-ide \
    stage-lua \
    stage-soundsubsystem \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.getVar("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

do_populate() {
    LD_LINUX=$(find -L /lib64 -maxdepth 1 -xtype l -name "ld-linux*.so*")
    if [ -z "$LD_LINUX" ];
    then
        LD_LINUX=$(find -L /lib -maxdepth 1 -xtype l -name "ld-linux*.so*")
    fi
    mkdir -p ${WRAPPER_LIB_DIR}
    cp -L $LD_LINUX ${WRAPPER_LIB_DIR}/${LD_LINUX_SO}
    sysdir=$(dirname $(find /usr/lib /lib -iname "libc.so.*"))

    cp $sysdir/libc.so.* ${WRAPPER_LIB_DIR}/
    cp $sysdir/libm.so.* ${WRAPPER_LIB_DIR}/
    cp $sysdir/libpthread.so.* ${WRAPPER_LIB_DIR}/
    cp $sysdir/libthread_db.so.* ${WRAPPER_LIB_DIR}/
    cp $sysdir/libstdc++.so.*  ${WRAPPER_LIB_DIR}/
    cp $sysdir/libresolv.so.*  ${WRAPPER_LIB_DIR}/
    cp $sysdir/libgcc_s.so.*   ${WRAPPER_LIB_DIR}/

    mkdir -p ${WRAPPER_INCLUDE_DIR}
    cp -r ${IMAGE_ROOTFS}/include/* ${WRAPPER_INCLUDE_DIR}/
}
