if (NOT EVENT_FOUND)
    set(EVENT_INSTALL "${BB_TOP_DIR}/tmp/work/libevent${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(EVENT_LIBRARY          "${EVENT_INSTALL}/lib/libevent.so")
    set(EVENT_CORE_LIBRARY     "${EVENT_INSTALL}/lib/libevent_core.so")
    set(EVENT_PTHREADS_LIBRARY "${EVENT_INSTALL}/lib/libevent_pthreads.so")
    set(EVENT_INCLUDE_DIR  "${EVENT_INSTALL}/include")
    set(EVENT_INCLUDE_DIRS "${EVENT_INSTALL}/include")
    set(EVENT_FOUND 1)

    add_library(event STATIC IMPORTED)
    add_library(event_core STATIC IMPORTED)
    add_library(event_pthreads STATIC IMPORTED)
    set_target_properties(event PROPERTIES
      IMPORTED_LOCATION   "${EVENT_LIBRARY}" "${EVENT_CORE_LIBRARY}" "${EVENT_PTHREADS_LIBRARY}"
      INCLUDE_DIRECTORIES "${EVENT_INCLUDE_DIR}"
    )

    message("EVENT_LIBRARY=${EVENT_LIBRARY}")
    message("EVENT_INCLUDE_DIR=${EVENT_INCLUDE_DIR}")

    include_directories("${EVENT_INCLUDE_DIR}")
endif()
