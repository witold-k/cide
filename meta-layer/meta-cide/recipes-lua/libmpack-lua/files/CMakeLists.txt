cmake_minimum_required(VERSION 3.12)
project(libmpack-lua)

include(GNUInstallDirs)

find_package(LuaJit REQUIRED)

set(SRC lmpack.c)
add_library(mpack SHARED ${SRC})
target_include_directories(mpack PRIVATE ${LUA_INCLUDE_DIRS})
target_link_libraries(mpack ${LUA_LIBRARIES})
set_target_properties(mpack PROPERTIES
  PREFIX ""
)
install(TARGETS mpack DESTINATION lib)
