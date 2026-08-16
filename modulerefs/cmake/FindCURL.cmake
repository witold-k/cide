if (NOT CURL_FOUND)
    set(CURL_INSTALL_BASE "${BB_TOP_DIR}/tmp/work/curl${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}")
    set(CURL_INSTALL      "${CURL_INSTALL_BASE}/${CMAKE_INSTALL_PREFIX}")

    set(CURL_LIBRARY      "${CURL_INSTALL}/lib/libcurl.so")
    set(CURL_LIBRARIES    "${CURL_INSTALL}/lib/libcurl.so")
    set(CURL_INCLUDE_DIR  "${CURL_INSTALL}/include")
    set(CURL_INCLUDE_DIRS "${CURL_INSTALL}/include")
    set(CURL_FOUND        1)

    add_library(CURL::libcurl SHARED IMPORTED)
    set_target_properties(CURL::libcurl PROPERTIES
        IMPORTED_LOCATION   "${CURL_LIBRARY}"
        INCLUDE_DIRECTORIES "${CURL_INCLUDE_DIR}"
    )

    message("CURL_LIBRARY=${CURL_LIBRARY}")
    message("CURL_INCLUDE_DIR=${CURL_INCLUDE_DIR}")

    include_directories("${CURL_INCLUDE_DIR}")
endif()
