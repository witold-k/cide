cmake_minimum_required(VERSION 3.12)
project(lua-cmake VERSION 1.0.0 DESCRIPTION "Lua build for a project subdirectory" LANGUAGES C)

include(GNUInstallDirs)

option(BUILD_SHARED_LIBS "Build as shared library" ON)

set(srcDir     lib)
set(includeDir include)

set(srcFiles
  dlist.c
  sysfs_attr.c
  sysfs_bus.c
  sysfs_class.c
  sysfs_device.c
  sysfs_driver.c
  sysfs_module.c
  sysfs_utils.c
)

set(publicHeaderFiles  dlist.h libsysfs.h)

list(TRANSFORM srcFiles PREPEND ${srcDir}/)
list(TRANSFORM publicHeaderFiles PREPEND ${includeDir}/)


add_library(sysfs STATIC ${srcFiles})
target_include_directories(sysfs PUBLIC include)


set_target_properties(sysfs PROPERTIES
  FRAMEWORK TRUE
  PUBLIC_HEADER "${publicHeaderFiles}"
)
install(TARGETS
  sysfs         DESTINATION lib
  PUBLIC_HEADER DESTINATION include/sysfs
)
