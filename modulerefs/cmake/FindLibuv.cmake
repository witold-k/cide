if (NOT LIBUV_FOUND)
    set(LIBUV_INSTALL "${BB_TOP_DIR}/tmp/work/libuv${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LIBUV_LIBRARY      "${LIBUV_INSTALL}/lib/libuv.so")
    set(LIBUV_LIBRARIES    "${LIBUV_INSTALL}/lib/libuv.so")
    set(LIBUV_INCLUDE_DIR  "${LIBUV_INSTALL}/include")
    set(LIBUV_INCLUDE_DIRS "${LIBUV_INSTALL}/include")
    set(LIBUV_CFLAGS "-I${LIBUV_INCLUDE_DIR}")
    set(LIBUVPATH "-I${LIBUV_INSTALL}/bin")
    set(LIBUV_FOUND 1)

    if (NOT EXISTS ${LIBUV_INCLUDE_DIR}/uv.h)
        error("uv.h not available")
    endif()

    add_library(uv SHARED IMPORTED)
    set_target_properties(uv PROPERTIES
      IMPORTED_LOCATION   "${LIBUV_LIBRARY}"
      INCLUDE_DIRECTORIES "${LIBUV_INCLUDE_DIR}"
      )

    add_library(UV::UV SHARED IMPORTED)
    set_target_properties(UV::UV PROPERTIES
      IMPORTED_LOCATION   "${LIBUV_LIBRARY}"
      INCLUDE_DIRECTORIES "${LIBUV_INCLUDE_DIR}"
      )

    message("LIBUV_LIBRARY=${LIBUV_LIBRARY}")
    message("LIBUV_INCLUDE_DIR=${LIBUV_INCLUDE_DIR}")

    mark_as_advanced(LIBUV_INCLUDE_DIR LIBUV_LIBRARY LIBUV_INCLUDE_DIRS LIBUV_LIBRARIES)
    list(APPEND LIBUV_NAMES uv_a uv libuv)

    include_directories("${LIBUV_INCLUDE_DIR}")
endif()
