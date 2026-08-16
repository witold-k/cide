cmake_minimum_required(VERSION 3.12)
project(unibilium-cmake VERSION 1.0.0 DESCRIPTION "unibilium build for a project subdirectory" LANGUAGES C)

find_package(Terminfo REQUIRED)

include(GNUInstallDirs)

set(publicHeaderFiles unibilium.h)

add_library(unibilium STATIC
    unibilium.c
    uninames.c
    uniutil.c
)
add_library(unibilium-header INTERFACE)
target_include_directories(unibilium-header INTERFACE ${includeDir})
message("[unibilium] TERMINFO_DIRS=${TERMINFO_DIR}")
target_compile_definitions(unibilium
    PRIVATE
    TERMINFO_DIRS="${TERMINFO_DIR}"
)
target_link_libraries(unibilium INTERFACE unibilium-header)

set_target_properties(unibilium PROPERTIES
  FRAMEWORK TRUE
  PUBLIC_HEADER "${publicHeaderFiles}"
)
install(TARGETS
  unibilium     DESTINATION lib
  PUBLIC_HEADER DESTINATION include
)

#

add_executable(gen-static-test tools/gen-static-test.c)
target_link_libraries(gen-static-test PRIVATE unibilium)
target_include_directories(gen-static-test PUBLIC ${CMAKE_SOURCE_DIR})
install(TARGETS gen-static-test DESTINATION bin)

add_executable(unibi-dump tools/unibi-dump.c)
target_link_libraries(unibi-dump PRIVATE unibilium)
target_include_directories(unibi-dump PUBLIC ${CMAKE_SOURCE_DIR})
install(TARGETS unibi-dump DESTINATION bin)

add_executable(unibi-put tools/unibi-put.c)
target_link_libraries(unibi-put PRIVATE unibilium)
target_include_directories(unibi-put PUBLIC ${CMAKE_SOURCE_DIR})
install(TARGETS unibi-put DESTINATION bin)

#

add_library(unibilium::lib ALIAS unibilium)
add_library(unibilium::header ALIAS unibilium-header)

