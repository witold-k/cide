if (NOT PNG_FOUND)
    set(PNG_INSTALL_BASE "${BB_TOP_DIR}/tmp/work/libpng${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}")
    set(PNG_INSTALL      "${PNG_INSTALL_BASE}/${CMAKE_INSTALL_PREFIX}")

    set(PNG_LIBRARY      "${PNG_INSTALL}/lib/libpng.so")
    set(PNG_LIBRARIES    "${PNG_INSTALL}/lib/libpng.so")
    set(PNG_INCLUDE_DIR  "${PNG_INSTALL}/include")
    set(PNG_INCLUDE_DIRS "${PNG_INSTALL}/include")
    set(PNG_FOUND        1)

    add_library(PNG::PNG SHARED IMPORTED)
    set_target_properties(PNG::PNG PROPERTIES
        IMPORTED_LOCATION   "${PNG_LIBRARY}"
        INCLUDE_DIRECTORIES "${PNG_INCLUDE_DIR}"
    )

    message("PNG_LIBRARY=${PNG_LIBRARY}")
    message("PNG_INCLUDE_DIR=${PNG_INCLUDE_DIR}")

    include_directories("${PNG_INCLUDE_DIR}")
endif()
