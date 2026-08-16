if (NOT CURSES_FOUND)
    set(CURSES_INSTALL "${BB_TOP_DIR}/tmp/work/libncurses${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(CURSES_LIBRARY      "${CURSES_INSTALL}/lib/libncursesw.a")
    set(CURSES_INCLUDE_DIR  "${CURSES_INSTALL}/include")
    set(CURSES_INCLUDE_DIRS "${CURSES_INSTALL}/include")
    set(TERMINFO_DIR        "${CURSES_INSTALL}/share/terminfo")
    set(CURSES_FOUND 1)

    message("TERMINFO_DIR=${TERMINFO_DIR}")

    add_library(ncursesw STATIC IMPORTED)
    set_target_properties(ncursesw PROPERTIES
      IMPORTED_LOCATION   "${CURSES_LIBRARY}"
      INCLUDE_DIRECTORIES "${CURSES_INCLUDE_DIR}"
    )

    add_library(NCURSES::NCURSES STATIC IMPORTED)
    set_target_properties(NCURSES::NCURSES PROPERTIES
      IMPORTED_LOCATION   "${CURSES_LIBRARY}"
      INCLUDE_DIRECTORIES "${CURSES_INCLUDE_DIR}"
    )

    message("CURSES_LIBRARY=${CURSES_LIBRARY}")
    message("CURSES_INCLUDE_DIR=${CURSES_INCLUDE_DIR}")
    message("TERMINFO_DIR=${TERMINFO_DIR}")

    include_directories("${CURSES_INCLUDE_DIR}")
endif()
