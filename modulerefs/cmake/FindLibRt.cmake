if (NOT LIBRT_FOUND)
    set(LIBRT_INSTALL "")
    set(LIBRT_LIBRARIES  "rt")
    set(LIBRT_LIBRARY    "rt")
    set(LIBRT_FOUND 1)

    message("LIBRT_LIBRARY=${LIBRT_LIBRARY}")
endif()
