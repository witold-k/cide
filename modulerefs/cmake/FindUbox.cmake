if (NOT UBOX_FOUND)
  set(UBOX_INSTALL "${BB_TOP_DIR}/tmp/work/libubox${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

  set(UBOX_LIBRARY      "${UBOX_INSTALL}/lib/libubox.so")
  set(UBOX_BLOB_LIBRARY "${UBOX_INSTALL}/lib/libblobmsg_json.a")
  set(UBOX_JSON_LIBRARY "${UBOX_INSTALL}/lib/libjson_script.so")

  set(UBOX_INCLUDE_DIR  "${UBOX_INSTALL}/include")
  set(UBOX_INCLUDE_DIRS "${UBOX_INSTALL}/include")
  set(UBOX_CFLAGS "-I${UBOX_INCLUDE_DIR}")
  set(UBOXPATH "-I${UBOX_INSTALL}/bin")
  set(UBOX_FOUND 1)
  add_library(ubox SHARED IMPORTED)
  set_target_properties(ubox PROPERTIES
    IMPORTED_LOCATION   "${UBOX_LIBRARY}"
    INCLUDE_DIRECTORIES "${UBOX_INCLUDE_DIR}"
    )

  message("UBOX_LIBRARY=${UBOX_LIBRARY}")
  message("UBOX_INCLUDE_DIR=${UBOX_INCLUDE_DIR}")

  include_directories("${UBOX_INCLUDE_DIR}")
endif()
