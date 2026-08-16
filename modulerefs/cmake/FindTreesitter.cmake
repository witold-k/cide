if (NOT TreeSitter_FOUND)
    set(TreeSitter_INSTALL "${BB_TOP_DIR}/tmp/work/tree-sitter${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(TreeSitter_LIBRARY      "${TreeSitter_INSTALL}/lib/libtree-sitter.so")
    set(TreeSitter_LIBRARIES    "${TreeSitter_INSTALL}/lib/libtree-sitter.so")
    set(TreeSitter_INCLUDE_DIR  "${TreeSitter_INSTALL}/include")
    set(TreeSitter_INCLUDE_DIRS "${TreeSitter_INSTALL}/include")
    set(TreeSitter_CFLAGS "-I${TreeSitter_INCLUDE_DIR}")
    set(LUAPATH "-I${TreeSitter_INSTALL}/bin")
    set(TreeSitter_FOUND 1)
    add_library(treesitter SHARED IMPORTED)
    set_target_properties(treesitter PROPERTIES
      IMPORTED_LOCATION   "${TreeSitter_LIBRARY}"
      INCLUDE_DIRECTORIES "${TreeSitter_INCLUDE_DIR}"
      )

    message("TreeSitter_LIBRARY=${TreeSitter_LIBRARY}")
    message("TreeSitter_INCLUDE_DIR=${TreeSitter_INCLUDE_DIR}")

    include_directories("${TreeSitter_INCLUDE_DIR}")
endif()
