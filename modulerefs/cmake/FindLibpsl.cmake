if (NOT LIBPSL_FOUND)
    set(LIBPSL_INSTALL "${BB_TOP_DIR}/tmp/work/libpsl${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LIBPSL_LIBRARY      "${LIBPSL_INSTALL}/lib/libpsl.so")
    set(LIBPSL_LIBRARIES    "${LIBPSL_INSTALL}/lib/libpsl.so")
    set(LIBPSL_INCLUDE_DIR  "${LIBPSL_INSTALL}/include")
    set(LIBPSL_INCLUDE_DIRS "${LIBPSL_INSTALL}/include")
    set(LIBPSL_CFLAGS "-I${LIBPSL_INCLUDE_DIR}")
    set(LIBPSLPATH "-I${LIBPSL_INSTALL}/bin")
    set(LIBPSL_FOUND 1)
    add_library(lua SHARED IMPORTED)
    set_target_properties(lua PROPERTIES
      IMPORTED_LOCATION   "${LIBPSL_LIBRARY}"
      INCLUDE_DIRECTORIES "${LIBPSL_INCLUDE_DIR}"
      )

    message("LIBPSL_LIBRARY=${LIBPSL_LIBRARY}")
    message("LIBPSL_INCLUDE_DIR=${LIBPSL_INCLUDE_DIR}")

    include_directories("${LIBPSL_INCLUDE_DIR}")
endif()
