if (NOT JSONC_FOUND)
    set(JSONC_INSTALL "${BB_TOP_DIR}/tmp/work/jsonc${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(JSONC_LIBRARY      "${JSONC_INSTALL}/lib/libjson-c.so")
    set(JSONC_INCLUDE_DIR  "${JSONC_INSTALL}/include")
    set(JSONC_INCLUDE_DIRS "${JSONC_INSTALL}/include")
    set(JSONC_FOUND 1)

    add_library(json-c SHARED IMPORTED)
    set_target_properties(json-c PROPERTIES
      IMPORTED_LOCATION   "${JSONC_LIBRARY}"
      INCLUDE_DIRECTORIES "${JSONC_INCLUDE_DIR}"
      )

    message("JSONC_LIBRARY=${JSONC_LIBRARY}")
    message("JSONC_INCLUDE_DIR=${JSONC_INCLUDE_DIR}")

    include_directories("${JSONC_INCLUDE_DIR}")
endif()
