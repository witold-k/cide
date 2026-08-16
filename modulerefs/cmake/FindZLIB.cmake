if (NOT ZLIB_FOUND)
    set(ZLIB_INSTALL "${BB_TOP_DIR}/tmp/work/zlib${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(ZLIB_LIBRARY      "${ZLIB_INSTALL}/lib/libz.so")
    set(ZLIB_LIBRARIES    "${ZLIB_INSTALL}/lib/libz.so")
    set(ZLIB_INCLUDE_DIR  "${ZLIB_INSTALL}/include")
    set(ZLIB_INCLUDE_DIRS "${ZLIB_INSTALL}/include")
    set(ZLIB_FOUND        1)

    add_library(ZLIB::ZLIB SHARED IMPORTED)
    set_target_properties(ZLIB::ZLIB PROPERTIES
        IMPORTED_LOCATION   "${ZLIB_LIBRARY}"
        INCLUDE_DIRECTORIES "${ZLIB_INCLUDE_DIR}"
    )

    message("ZLIB_LIBRARY=${ZLIB_LIBRARY}")
    message("ZLIB_INCLUDE_DIR=${ZLIB_INCLUDE_DIR}")

    include_directories("${ZLIB_INCLUDE_DIR}")
endif()
