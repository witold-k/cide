if (NOT GNUTLS_FOUND)
    set(GNUTLS_INSTALL "${BB_TOP_DIR}/tmp/work/gnutls${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(GNUTLS_LIBRARY      "${GNUTLS_INSTALL}/lib/libgnutls.so")
    set(GNUTLS_LIBRARIES    "${GNUTLS_INSTALL}/lib/libgnutls.so")
    set(GNUTLS_INCLUDE_DIR  "${GNUTLS_INSTALL}/include")
    set(GNUTLS_INCLUDE_DIRS "${GNUTLS_INSTALL}/include")
    set(GNUTLS_CFLAGS "-I${GNUTLS_INCLUDE_DIR}")
    set(LUAPATH "-I${GNUTLS_INSTALL}/bin")
    set(GNUTLS_FOUND 1)

    add_library(gnutls SHARED IMPORTED)
    set_target_properties(gnutls PROPERTIES
      IMPORTED_LOCATION   "${GNUTLS_LIBRARY}"
      INCLUDE_DIRECTORIES "${GNUTLS_INCLUDE_DIR}"
      )

    message("GNUTLS_LIBRARY=${GNUTLS_LIBRARY}")
    message("GNUTLS_INCLUDE_DIR=${GNUTLS_INCLUDE_DIR}")

    include_directories("${GNUTLS_INCLUDE_DIR}")
endif()
