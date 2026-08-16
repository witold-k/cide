find_package(unibilium REQUIRED)
find_package(libvterm  REQUIRED) # not neccessary here, but neovim requires it and has broken dependencies

if (NOT LIBTERMKEY_FOUND)
    set(LIBTERMKEY_INSTALL "${BB_TOP_DIR}/tmp/work/libtermkey${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LIBTERMKEY_LIBRARY      "${LIBTERMKEY_INSTALL}/lib/libtermkey.so")
    set(LIBTERMKEY_LIBRARIES    "${LIBTERMKEY_INSTALL}/lib/libtermkey.so")
    set(LIBTERMKEY_INCLUDE_DIR  "${LIBTERMKEY_INSTALL}/include")
    set(LIBTERMKEY_INCLUDE_DIRS "${LIBTERMKEY_INSTALL}/include;${UNIBILIUM_INCLUDE_DIRS};${LIBVTERM_INCLUDE_DIRS}")
    set(LIBTERMKEY_FOUND 1)

    add_library(termkey SHARED IMPORTED)
    set_target_properties(termkey PROPERTIES
      IMPORTED_LOCATION   "${LIBTERMKEY_LIBRARY}"
      INCLUDE_DIRECTORIES "${LIBTERMKEY_INCLUDE_DIRS}"
      )

    message("LIBTERMKEY_LIBRARY=${LIBTERMKEY_LIBRARY}")
    message("LIBTERMKEY_INCLUDE_DIR=${LIBTERMKEY_INCLUDE_DIR}")

    include_directories("${LIBTERMKEY_INCLUDE_DIR}")
endif()
