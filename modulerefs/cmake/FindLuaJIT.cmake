if (NOT LUAJIT_FOUND)
    set(LUAJIT_INSTALL "${BB_TOP_DIR}/tmp/work/luajit${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LUAJIT_LIBRARY      "${LUAJIT_INSTALL}/lib/libluajit-5.1.so")
    set(LUAJIT_LIBRARIES    "${LUAJIT_INSTALL}/lib/libluajit-5.1.so")
    set(LUAJIT_INCLUDE_DIR  "${LUAJIT_INSTALL}/include/luajit-2.1")
    set(LUAJIT_INCLUDE_DIRS "${LUAJIT_INSTALL}/include/luajit-2.1")
    set(LUAJIT_BIN_DIR      "${LUAJIT_INSTALL}/bin")
    set(LUAJIT_CFLAGS "-I${LUAJIT_INCLUDE_DIR}/luajit-2.1")
    set(LUAPATH "-I${LUAJIT_INSTALL}/bin")
    set(LUAJIT_FOUND 1)
    add_library(luajit SHARED IMPORTED)
    set_target_properties(luajit PROPERTIES
      IMPORTED_LOCATION   "${LUAJIT_LIBRARY}"
      INCLUDE_DIRECTORIES "${LUAJIT_INCLUDE_DIR}"
      )
    add_library(luajit-bin SHARED IMPORTED)
    set_target_properties(luajit-bin PROPERTIES
      IMPORTED_LOCATION   "${LUAJIT_INSTALL}/bin/luajit"
      PROPERTIES OUTPUT_NAME ticker "luajit"
      )

    message("LUAJIT_LIBRARY=${LUAJIT_LIBRARY}")
    message("LUAJIT_INCLUDE_DIR=${LUAJIT_INCLUDE_DIR}")

    include_directories("${LUAJIT_INCLUDE_DIR}")
endif()
