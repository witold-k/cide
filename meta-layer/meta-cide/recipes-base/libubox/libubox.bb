SUMMARY = "libubox"
LICENSE = "CLOSED"
MODULE = "base/libubox"
SRCPV = "1"

inherit simplecmake encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "jsonc lua")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

CMAKE_INCLUDE_JSON_PATH = "${TOPDIR}/tmp/work/jsonc${CURRENT_TARGET_PREFIX}-0.1-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
CMAKE_FRAMEWORK_JSON_PATH = "${TOPDIR}/tmp/work/jsonc${CURRENT_TARGET_PREFIX}-0.1-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"
CMAKE_INCLUDE_LUA_PATH = "${TOPDIR}/tmp/work/lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/include"
CMAKE_FRAMEWORK_LUA_PATH = "${TOPDIR}/tmp/work/lua${CURRENT_TARGET_PREFIX}-1.0-r0/install/${CURRENT_TARGET}/${TARGET_PREFIX_DIR}/lib"
CMAKE_FRAMEWORK_PATH = "\
${CMAKE_FRAMEWORK_JSON_PATH};\
${CMAKE_FRAMEWORK_LUA_PATH};\
${CMAKE_INCLUDE_JSON_PATH};\
${CMAKE_INCLUDE_LUA_PATH}\
"
CMAKE_OPTIONS = " \
    -DBUILD_LUA:BOOL=OFF \
"

do_patch() {
    if grep -q '<json.h>' ${S}/jshn.c;
    then
        sed -i 's|<json.h>|<json-c/json.h>|' ${S}/jshn.c
        sed -i 's|<json.h>|<json-c/json.h>|' ${S}/blobmsg_json.c
        sed -i 's|<json.h>|<json-c/json.h>|' ${S}/examples/json_script-example.c
        sed -i -E 's|PKG_SEARCH_MODULE.(\w+)[^\)]+|find_package\(\1|g' ${S}/CMakeLists.txt
        sed -i -E 's|pkg_search_module.(\w+)[^\)]+|find_package\(\1|g' ${S}/lua/CMakeLists.txt
        sed -i -E 's|find_library\(json NAMES json-c\)|find_package\(Jsonc REQUIRED\)|g' ${S}/CMakeLists.txt
        sed -i -E 's|\{json\}|\{JSONC_LIBRARY\}|g' ${S}/CMakeLists.txt
        sed -i -E 's|find_library\(json NAMES json-c\)|find_package\(Jsonc REQUIRED\)|g' ${S}/examples/CMakeLists.txt
        sed -i -E 's|\{json\}|\{JSONC_LIBRARY\}|g' ${S}/examples/CMakeLists.txt
    fi
}

