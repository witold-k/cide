if (NOT LUA_FOUND)
    set(LUA_INSTALL "${BB_TOP_DIR}/tmp/work/lua${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LUA_LIBRARY      "${LUA_INSTALL}/lib/liblua.a")
    set(LUA_LIBRARIES    "${LUA_INSTALL}/lib/liblua.a")
    set(LUA_INCLUDE_DIR  "${LUA_INSTALL}/include")
    set(LUA_INCLUDE_DIRS "${LUA_INSTALL}/include")
    set(LUA_CFLAGS "-I${LUA_INCLUDE_DIR}")
    set(LUAPATH "-I${LUA_INSTALL}/bin")
    set(LUA_FOUND 1)
    add_library(lua SHARED IMPORTED)
    set_target_properties(lua PROPERTIES
      IMPORTED_LOCATION   "${LUA_LIBRARY}"
      INCLUDE_DIRECTORIES "${LUA_INCLUDE_DIR}"
      )

    message("LUA_LIBRARY=${LUA_LIBRARY}")
    message("LUA_INCLUDE_DIR=${LUA_INCLUDE_DIR}")

    include_directories("${LUA_INCLUDE_DIR}")
endif()
