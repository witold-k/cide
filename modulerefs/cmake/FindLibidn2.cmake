find_package(Libunistring)

if (NOT LIBIDN2_FOUND)
    set(LIBIDN2_INSTALL "${BB_TOP_DIR}/tmp/work/libidn2${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LIBIDN2_LIBRARY      "${LIBIDN2_INSTALL}/lib/libidn2.so" "${LIBUNISTRING_INSTALL}/lib/libunistring.so")
    set(LIBIDN2_LIBRARIES    "${LIBIDN2_INSTALL}/lib/libidn2.so" "${LIBUNISTRING_INSTALL}/lib/libunistring.so")
    set(LIBIDN2_INCLUDE_DIR  "${LIBIDN2_INSTALL}/include")
    set(LIBIDN2_INCLUDE_DIRS "${LIBIDN2_INSTALL}/include")
    set(LIBIDN2_CFLAGS "-I${LIBIDN2_INCLUDE_DIR}")
    set(LIBIDN2PATH "-I${LIBIDN2_INSTALL}/bin")
    set(LIBIDN2_FOUND 1)
    add_library(idn2 SHARED IMPORTED)
    set_target_properties(idn2 PROPERTIES
      IMPORTED_LOCATION   "${LIBIDN2_LIBRARY}"
      INCLUDE_DIRECTORIES "${LIBIDN2_INCLUDE_DIR}"
      )

    message("LIBIDN2_LIBRARY=${LIBIDN2_LIBRARY}")
    message("LIBIDN2_INCLUDE_DIR=${LIBIDN2_INCLUDE_DIR}")

    include_directories("${LIBIDN2_INCLUDE_DIR}")
endif()
