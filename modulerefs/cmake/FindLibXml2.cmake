
# if (NOT LIBXML2_FOUND)
#     set(LIBXML2_INSTALL "${BB_TOP_DIR}/tmp/work/libxml2${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")
#
#     message("USE private FindXml2.cmake ${LIBXML2_INSTALL}")
#
#     # set(LIBXML2_LIBRARY      "${LIBXML2_INSTALL}/lib/libxml2.so")
#     # set(LIBXML2_INCLUDE_DIR  "${LIBXML2_INSTALL}/include")
#     # set(LIBXML2_INCLUDE_DIRS "${LIBXML2_INSTALL}/include")
#     # set(LIBXML2_VERSION_STRING "0.0")
#
#     include(${LIBXML2_INSTALL}/lib/cmake/libxml2/libxml2-config.cmake)
#     set (LIBXML2_FOUND 1)
#     # add_library(LIBXML2::LIBXML2 SHARED IMPORTED)
#     # set_target_properties(LIBXML2::LIBXML2 PROPERTIES
#     #   IMPORTED_LOCATION   "${LIBXML2_LIBRARY}"
#     #   INCLUDE_DIRECTORIES "${LIBXML2_INCLUDE_DIR}"
#     #   )
#
#     if ("${LIBXML2_LIBRARY}" STREQUAL "")
#       set(LIBXML2_LIBRARY "${LIBXML2_INSTALL}/lib/libxml2.so")
#     endif()
#     message("LIBXML2_LIBRARY=${LIBXML2_LIBRARY}")
#     if ("${LIBXML2_LIBRARIES}" STREQUAL "")
#       set(LIBXML2_LIBRARIES "${LIBXML2_LIBRARY}")
#     endif()
#     message("LIBXML2_LIBRARIES=${LIBXML2_LIBRARIES}")
#
#     message("LIBXML2_INCLUDE_DIR=${LIBXML2_INCLUDE_DIR}")
#
#     if (NOT ${LIBXML2_INCLUDE_DIR} STREQUAL "")
#       include_directories("${LIBXML2_INCLUDE_DIR}")
#     endif()
# endif()

if (NOT LLIBXML2_FOUND)
    set(LLIBXML2_INSTALL "${BB_TOP_DIR}/tmp/work/libxml2${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LLIBXML2_LIBRARY      "${LLIBXML2_INSTALL}/lib/libxml2.so" "${LIBUNISTRING_INSTALL}/lib/libz.so")
    set(LLIBXML2_LIBRARIES    "${LLIBXML2_INSTALL}/lib/libxml2.so" "${LIBUNISTRING_INSTALL}/lib/libz.so")
    set(LLIBXML2_INCLUDE_DIR  "${LLIBXML2_INSTALL}/include")
    set(LLIBXML2_INCLUDE_DIRS "${LLIBXML2_INSTALL}/include")
    set(LLIBXML2_CFLAGS "-I${LLIBXML2_INCLUDE_DIR}")
    set(LLIBXML2PATH "-I${LLIBXML2_INSTALL}/bin")
    set(LLIBXML2_FOUND 1)
    add_library(idn2 SHARED IMPORTED)
    set_target_properties(idn2 PROPERTIES
      IMPORTED_LOCATION   "${LLIBXML2_LIBRARY}"
      INCLUDE_DIRECTORIES "${LLIBXML2_INCLUDE_DIR}"
      )

    message("LLIBXML2_LIBRARY=${LLIBXML2_LIBRARY}")
    message("LLIBXML2_INCLUDE_DIR=${LLIBXML2_INCLUDE_DIR}")

    include_directories("${LLIBXML2_INCLUDE_DIR}")
endif()
