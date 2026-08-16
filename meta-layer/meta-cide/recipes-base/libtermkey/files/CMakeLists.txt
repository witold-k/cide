cmake_minimum_required(VERSION 3.12)
project(libtermkey-cmake VERSION 1.0.0 DESCRIPTION "libtermkey build" LANGUAGES C)

include(GNUInstallDirs)

option(BUILD_SHARED_LIBS "Build as shared library" ON)

find_package(Curses    REQUIRED)
find_package(UNIBILIUM REQUIRED)

set(srcDir .)

set(srcFiles
    driver-csi.c  driver-ti.c  termkey.c
    # t/01base.c    t/03utf8.c   t/05read.c    t/10keyname.c  t/12strpkey.c  t/20canon.c
    # t/31position.c    t/38csi.c  t/40ti-override.c
    # t/02getkey.c  t/04flags.c  t/06buffer.c  t/11strfkey.c  t/13cmpkey.c   t/30mouse.c  t/32modereport.c  t/39dcs.c
    t/taplib.c
)

set(publicHeaderFiles termkey.h)

list(TRANSFORM srcFiles PREPEND ${srcDir}/)
list(TRANSFORM publicHeaderFiles PREPEND ${srcDir}/)

set(includeDir "${CMAKE_CURRENT_BINARY_DIR}/include")
set(VERSION_MAJOR "0")
set(VERSION_MINOR "22")
set(VERSION "$(VERSION_MAJOR).$(VERSION_MINOR")
configure_file(
    ${CMAKE_CURRENT_BINARY_DIR}/include/termkey.h.in ${CMAKE_CURRENT_BINARY_DIR}/include/termkey.h
)
configure_file( # temporary solution
    ${CMAKE_CURRENT_BINARY_DIR}/include/termkey.h.in ${CMAKE_CURRENT_SOURCE_DIR}/termkey.h
)

add_library(termkey SHARED ${srcFiles})
add_library(termkey-header INTERFACE)
target_compile_definitions(termkey PUBLIC "HAVE_UNIBILIUM=1")
target_include_directories(termkey-header
    INTERFACE ${includeDir}
)
target_include_directories(termkey
    PUBLIC ${CURSES_INCLUDE_DIRS} ${CURSES_INCLUDE_DIRS}/ncursesw
           ${UNIBILIUM_INCLUDE_DIRS}
)
target_link_libraries(termkey INTERFACE termkey-header PUBLIC ${CURSES_LIBRARY} ${UNIBILIUM_LIBRARY})

#add_executable(termkey-bin ${srcDir}/termkey.c)
#target_link_libraries(termkey-bin PRIVATE termkey-header termkey)

#

add_library(termkey::lib ALIAS termkey)
add_library(termkey::header ALIAS termkey-header)

#install(TARGETS termkey-bin DESTINATION bin)
#set_target_properties(termkey-bin PROPERTIES
#  OUTPUT_NAME termkey
#)
set_target_properties(termkey PROPERTIES
  FRAMEWORK TRUE
  PUBLIC_HEADER "${publicHeaderFiles}"
)
install(TARGETS
  termkey           DESTINATION lib
  PUBLIC_HEADER DESTINATION include
)
