
set(ICONV_INCLUDE_DIR "/usr/include/iconv.h")
set(ICONV_LIBRARY "")
find_package_handle_standard_args(Iconv DEFAULT_MSG
  ICONV_INCLUDE_DIR)
mark_as_advanced(ICONV_INCLUDE_DIR ICONV_LIBRARY)

#target_include_directories(iconv SYSTEM BEFORE INTERFACE ${ICONV_INCLUDE_DIR})

