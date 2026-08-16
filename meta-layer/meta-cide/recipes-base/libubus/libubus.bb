SUMMARY = "libubus"
LICENSE = "CLOSED"
MODULE = "base/ubus"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "libubox jsonc lua")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_INCLUDE_JSON_PATH = "${TOPDIR}/tmp/work/jsonc${CURRENT_TARGET_PREFIX}-0.1-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
CMAKE_FRAMEWORK_JSON_PATH = "${TOPDIR}/tmp/work/jsonc${CURRENT_TARGET_PREFIX}-0.1-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"
CMAKE_INCLUDE_UBOX_PATH = "${TOPDIR}/tmp/work/libubox${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
CMAKE_FRAMEWORK_UBOX_PATH = "${TOPDIR}/tmp/work/libubox${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"
CMAKE_FRAMEWORK_PATH = "\
${CMAKE_FRAMEWORK_JSON_PATH};\
${CMAKE_FRAMEWORK_UBOX_PATH};\
${CMAKE_INCLUDE_JSON_PATH};\
${CMAKE_INCLUDE_UBOX_PATH}\
"
CMAKE_OPTIONS = " \
    -DBUILD_LUA:BOOL=OFF \
"

INSTALL_SYSTEM = "${INSTALL_DIR}/${TARGET_PREFIX_DIR}/lib/systemd/system"

do_patch() {
    if grep -q 'ubox_library' ${S}/CMakeLists.txt;
    then
        sed -i -E 's|FIND_PATH.(\w+)(\W+)(\w+)[^\)]+|find_package\(Ubox|g' ${S}/CMakeLists.txt
        sed -i -E 's|find_library\(json NAMES json-c json\)|find_package\(Jsonc REQUIRED\)|g' ${S}/CMakeLists.txt
        sed -i -E 's|INCLUDE_DIRECTORIES.*ubox_include_dir.*||g' ${S}/CMakeLists.txt
        sed -i -E 's|\{json\}|\{JSONC_LIBRARY\}|g' ${S}/CMakeLists.txt
        sed -i -E 's|\{json\}|\{JSONC_LIBRARY\}|g' ${S}/examples/CMakeLists.txt
        sed -i -E 's|ubox_library|UBOX_LIBRARY|g' ${S}/CMakeLists.txt
        sed -i -E 's|blob_library|UBOX_BLOB_LIBRARY|g' ${S}/CMakeLists.txt
    fi
}

addtask libubus_copy_files
do_libubus_copy_files[nostamp] = "1"
do_libubus_copy_files[dirs] = "${B}"
do_libubus_copy_files() {
    mkdir -p ${INSTALL_SYSTEM}
    cp ${FILE_DIRNAME}/files/ubus.service ${INSTALL_SYSTEM}/ # copy systemd service
}

python do_install:append() {
    bb.build.exec_func('do_libubus_copy_files', d)
}

