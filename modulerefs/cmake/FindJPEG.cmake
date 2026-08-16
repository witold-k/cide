if (NOT JPEG_FOUND)
    set(JPEG_INSTALL_BASE "${BB_TOP_DIR}/tmp/work/libjpeg${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}")
    set(JPEG_INSTALL      "${JPEG_INSTALL_BASE}/${CMAKE_INSTALL_PREFIX}")

    set(JPEG_LIBRARY      "${JPEG_INSTALL}/lib/libjpeg.so")
    set(JPEG_LIBRARIES    "${JPEG_INSTALL}/lib/libjpeg.so")
    set(JPEG_INCLUDE_DIR  "${JPEG_INSTALL}/include")
    set(JPEG_INCLUDE_DIRS "${JPEG_INSTALL}/include")
    set(JPEG_FOUND        1)

    add_library(JPEG::JPEG SHARED IMPORTED)
    set_target_properties(JPEG::JPEG PROPERTIES
        IMPORTED_LOCATION   "${JPEG_LIBRARY}"
        INCLUDE_DIRECTORIES "${JPEG_INCLUDE_DIR}"
    )

    message("JPEG_LIBRARY=${JPEG_LIBRARY}")
    message("JPEG_INCLUDE_DIR=${JPEG_INCLUDE_DIR}")

    include_directories("${JPEG_INCLUDE_DIR}")
endif()
