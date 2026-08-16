if (NOT BROTLI_FOUND)
    set(BROTLI_INSTALL "${BB_TOP_DIR}/tmp/work/brotli${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(BROTLI_LIBRARY      "${BROTLI_INSTALL}/lib/libbrotlidec.so" "${BROTLI_INSTALL}/lib/libbrotlienc.so" "${BROTLI_INSTALL}/lib/libbrotlicommon.so")
    set(BROTLI_LIBRARIES    "${BROTLI_INSTALL}/lib/libbrotlidec.so" "${BROTLI_INSTALL}/lib/libbrotlienc.so" "${BROTLI_INSTALL}/lib/libbrotlicommon.so")
    set(BROTLI_INCLUDE_DIR  "${BROTLI_INSTALL}/include")
    set(BROTLI_INCLUDE_DIRS "${BROTLI_INSTALL}/include")
    set(BROTLI_FOUND        1)

    add_library(BROTLI::BROTLI SHARED IMPORTED)
    set_target_properties(BROTLI::BROTLI PROPERTIES
        IMPORTED_LOCATION   "${BROTLI_LIBRARY}"
        INCLUDE_DIRECTORIES "${BROTLI_INCLUDE_DIR}"
    )

    message("BROTLI_LIBRARY=${BROTLI_LIBRARY}")
    message("BROTLI_INCLUDE_DIR=${BROTLI_INCLUDE_DIR}")

    include_directories("${BROTLI_INCLUDE_DIR}")
endif()
