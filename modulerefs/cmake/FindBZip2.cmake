if (NOT BZIP2_FOUND)
    set(BZIP2_INSTALL "${BB_TOP_DIR}/tmp/work/bzip2${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(BZIP2_LIBRARY      "${BZIP2_INSTALL}/lib/libbz2.so")
    set(BZIP2_LIBRARIES    "${BZIP2_INSTALL}/lib/libbz2.so")
    set(BZIP2_INCLUDE_DIR  "${BZIP2_INSTALL}/include")
    set(BZIP2_INCLUDE_DIRS "${BZIP2_INSTALL}/include")
    set(BZIP2_FOUND        1)

    add_library(BZip2::BZip2 SHARED IMPORTED)
    set_target_properties(BZip2::BZip2 PROPERTIES
        IMPORTED_LOCATION   "${BZIP2_LIBRARY}"
        INCLUDE_DIRECTORIES "${BZIP2_INCLUDE_DIR}"
    )

    add_library(BZIP2::BZIP2 SHARED IMPORTED)
    set_target_properties(BZIP2::BZIP2 PROPERTIES
        IMPORTED_LOCATION   "${BZIP2_LIBRARY}"
        INCLUDE_DIRECTORIES "${BZIP2_INCLUDE_DIR}"
    )

    message("BZIP2_LIBRARY=${BZIP2_LIBRARY}")
    message("BZIP2_INCLUDE_DIR=${BZIP2_INCLUDE_DIR}")

    include_directories("${BZIP2_INCLUDE_DIR}")
endif()
