cmake_minimum_required(VERSION 3.20)
project(libusb-cmake VERSION 1.0.0 DESCRIPTION "libusb build for a project subdirectory" LANGUAGES C)

include(GNUInstallDirs)

option(BUILD_SHARED_LIBS "Build as shared library" ON)

set(srcDir libusb)
set(includeDir libusb)

# --- NEU: version_describe.h automatisch generieren ---
set(GENERATED_HEADER "${CMAKE_CURRENT_SOURCE_DIR}/${srcDir}/version_describe.h")
set(GEN_SCRIPT "${CMAKE_CURRENT_SOURCE_DIR}/build-aux/gen-describe.sh")

add_custom_command(
    OUTPUT "${GENERATED_HEADER}"
    COMMAND ${CMAKE_COMMAND} -E echo "Generating version_describe.h..."
    # Führt das originale Shell-Skript von libusb aus
    COMMAND sh "${GEN_SCRIPT}" "${CMAKE_CURRENT_SOURCE_DIR}" "${srcDir}/version_describe.h"
    WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}"
    COMMENT "Generating libusb version header"
)
# -----------------------------------------------------

set(srcFiles
    core.c
    descriptor.c
    hotplug.c
    io.c
    strerror.c
    sync.c
    os/events_posix.c
    os/linux_netlink.c
    os/linux_usbfs.c
    os/threads_posix.c
)

set(publicHeaderFiles libusb.h libusbi.h)

list(TRANSFORM srcFiles PREPEND ${srcDir}/)
list(TRANSFORM publicHeaderFiles PREPEND ${srcDir}/)

# --- NEU: Fügen Sie den generierten Header zu den Quellcodedateien hinzu ---
# Dadurch weiß CMake, dass es den Header vor dem Kompilieren bauen muss
add_library(usb SHARED ${srcFiles} "${GENERATED_HEADER}")

target_compile_options(usb
    PRIVATE
    $<$<OR:$<C_COMPILER_ID:AppleClang>,$<C_COMPILER_ID:Clang>,$<C_COMPILER_ID:GNU>>:
    -Wextra -Wshadow -Wsign-compare -Wundef -Wwrite-strings -Wredundant-decls
    -Wdisabled-optimization -Waggregate-return -Wdouble-promotion
    -Wmissing-prototypes -Wnested-externs -Wstrict-prototypes -Wold-style-definition>
    )
target_include_directories(usb PUBLIC ${includeDir})
add_library(libusb::lib ALIAS usb)

set_target_properties(usb PROPERTIES
  FRAMEWORK TRUE
  PUBLIC_HEADER "${publicHeaderFiles}"
)
install(TARGETS
  usb           DESTINATION lib
  PUBLIC_HEADER DESTINATION include
)

