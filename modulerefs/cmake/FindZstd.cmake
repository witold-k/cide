if (NOT LIBZSTD_FOUND)
    set(LIBZSTD_INSTALL "${BB_TOP_DIR}/tmp/work/zstd${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LIBZSTD_LIBRARY      "${LIBZSTD_INSTALL}/lib/libzstd.so")
    set(LIBZSTD_LIBRARIES    "${LIBZSTD_INSTALL}/lib/libzstd.so")
    set(LIBZSTD_INCLUDE_DIR  "${LIBZSTD_INSTALL}/include")
    set(LIBZSTD_INCLUDE_DIRS "${LIBZSTD_INSTALL}/include")
    set(LIBZSTD_FOUND        1)

    add_library(ZSTD::ZSTD SHARED IMPORTED)
    set_target_properties(ZSTD::ZSTD PROPERTIES
        IMPORTED_LOCATION   "${LIBZSTD_LIBRARY}"
        INCLUDE_DIRECTORIES "${LIBZSTD_INCLUDE_DIR}"
    )

    message("LIBZSTD_LIBRARY=${LIBZSTD_LIBRARY}")
    message("LIBZSTD_INCLUDE_DIR=${LIBZSTD_INCLUDE_DIR}")

    include_directories("${LIBZSTD_INCLUDE_DIR}")
endif()
