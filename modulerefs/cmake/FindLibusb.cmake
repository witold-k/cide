if (NOT LIBUSB_FOUND)
    set(LIBUSB_INSTALL "${BB_TOP_DIR}/tmp/work/libusb${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(LIBUSB_LIBRARY      "${LIBUSB_INSTALL}/lib/libusb.so")
    set(LIBUSB_LIBRARIES    "${LIBUSB_INSTALL}/lib/libusb.so")
    set(LIBUSB_INCLUDE_DIR  "${LIBUSB_INSTALL}/include")
    set(LIBUSB_INCLUDE_DIRS "${LIBUSB_INSTALL}/include")
    set(LIBUSB_CFLAGS "-I${LIBUSB_INCLUDE_DIR}")
    set(LIBUSBPATH "-I${LIBUSB_INSTALL}/bin")
    set(LIBUSB_FOUND 1)
    add_library(USB::USB SHARED IMPORTED)
    set_target_properties(USB::USB PROPERTIES
      IMPORTED_LOCATION   "${LIBUSB_LIBRARY}"
      INCLUDE_DIRECTORIES "${LIBUSB_INCLUDE_DIR}"
      )

    message("LIBUSB_LIBRARY=${LIBUSB_LIBRARY}")
    message("LIBUSB_INCLUDE_DIR=${LIBUSB_INCLUDE_DIR}")

    include_directories("${LIBUSB_INCLUDE_DIR}")
endif()
