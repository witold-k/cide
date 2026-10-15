cmake_minimum_required(VERSION 3.20)
project(tig-cmake VERSION 1.0.0 DESCRIPTION "tig build for a project subdirectory" LANGUAGES C)

find_package(Curses   REQUIRED)
find_package(Readline REQUIRED)

include(GNUInstallDirs)

set(srcDir src)

set(srcFiles
    apps.c
    argv.c
    blame.c
    blob.c
    diff.c
    display.c
    draw.c
    graph-v1.c
    graph-v2.c
    graph.c
    grep.c
    help.c
    io.c
    keys.c
    line.c
    log.c
    main.c
    map.c
    options.c
    pager.c
    parse.c
    prompt.c
    refdb.c
    reflog.c
    refs.c
    repo.c
    request.c
    search.c
    stage.c
    stash.c
    status.c
    string.c
    tig.c
    tree.c
    types.c
    ui.c
    util.c
    view.c
    watch.c
)
list(TRANSFORM srcFiles PREPEND ${srcDir}/)

set(compatFiles
    hashtab.c
    mkstemps.c
    setenv.c
    strndup.c
    utf8proc.c
    #    utf8proc_data.c
    wordexp.c
)
list(TRANSFORM compatFiles PREPEND compat/)

set(genFiles
    builtin-config.c
)
list(TRANSFORM genFiles PREPEND ${CMAKE_CURRENT_BINARY_DIR}/)

add_custom_command(
  OUTPUT ${CMAKE_CURRENT_BINARY_DIR}/builtin-config.c
  COMMAND ${CMAKE_CURRENT_SOURCE_DIR}/tools/make-builtin-config.sh ${CMAKE_CURRENT_SOURCE_DIR}/tigrc > ${CMAKE_CURRENT_BINARY_DIR}/builtin-config.c
)

add_executable(tig-bin ${srcFiles} ${compatFiles} ${genFiles})
add_custom_target(xcv_strtab ALL DEPENDS
  ${CMAKE_CURRENT_BINARY_DIR}/builtin-config.c
)
target_link_libraries(tig-bin PRIVATE NCURSES::NCURSES)
target_include_directories(tig-bin PRIVATE
    include .
    ${CURSES_INCLUDE_DIR}/ncursesw
)
target_compile_definitions(tig-bin PRIVATE
    HAVE_NCURSES_H
    SYSCONFDIR="${TIG_SYSCONFDIR}"
)
install(TARGETS tig-bin DESTINATION bin)
set_target_properties(tig-bin PROPERTIES OUTPUT_NAME tig)

