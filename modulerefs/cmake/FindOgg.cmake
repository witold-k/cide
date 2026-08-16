find_package(PkgConfig)
pkg_check_modules(OGG REQUIRED ogg)

set(OGG_INCLUDE_DIR ${OGG_INCLUDE_DIRS})
set(OGG_LIBRARY ${OGG_LIBRARIES})

message("### OGG_INCLUDE_DIR=${OGG_INCLUDE_DIR}")
message("### OGG_LIBRARY=${OGG_LIBRARY}")

# =============================================================================
# FIXED: Change UNKNOWN IMPORTED to INTERFACE IMPORTED to fix Ninja rule crash
# =============================================================================
if(NOT TARGET Ogg::ogg)
    add_library(Ogg::ogg INTERFACE IMPORTED)
    set_target_properties(Ogg::ogg PROPERTIES
        INTERFACE_INCLUDE_DIRECTORIES "${OGG_INCLUDE_DIR}"
        INTERFACE_LINK_LIBRARIES "${OGG_LIBRARY}"
    )
endif()

