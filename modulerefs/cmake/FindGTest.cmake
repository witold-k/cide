if (NOT GTEST_FOUND)
    set(GTEST_INSTALL "${BB_TOP_DIR}/tmp/work/googletest${BB_TARGET_PREFIX}-1.0-r0/install/${BB_TARGET}/${CMAKE_INSTALL_PREFIX}")

    set(GTEST_LIBRARY        "${GTEST_INSTALL}/lib/libgtest.a")
    set(GTEST_MAIN_LIBRARY   "${GTEST_INSTALL}/lib/libgtest_main.a")
    set(GTEST_MOCK_LIBRARY      "${GTEST_INSTALL}/lib/libgmock.a")
    set(GTEST_MOCK_MAIN_LIBRARY "${GTEST_INSTALL}/lib/libgmock_main.a")
    set(GTEST_BOTH_LIBRARIES "${GTEST_MAIN_LIBRARY}" "${GTEST_LIBRARY}")
    set(GTEST_INCLUDE_DIR    "${GTEST_INSTALL}/include")
    set(GTEST_INCLUDE_DIRS   "${GTEST_INSTALL}/include")
    set(GTEST_CFLAGS         "-I${GTEST_INCLUDE_DIR}")
    set(GTEST_FOUND 1)
    set(GTest_FOUND 1)

    add_library(GTest::gtest STATIC IMPORTED)
    set_target_properties(GTest::gtest PROPERTIES
        IMPORTED_LOCATION   "${GTEST_LIBRARY}"
        INCLUDE_DIRECTORIES "${GTEST_INCLUDE_DIR}"
    )

    add_library(GTest::gtest_main STATIC IMPORTED)
    set_target_properties(GTest::gtest_main PROPERTIES
        IMPORTED_LOCATION   "${GTEST_MAIN_LIBRARY}"
        INCLUDE_DIRECTORIES "${GTEST_INCLUDE_DIR}"
    )

    add_library(GTest::gmock STATIC IMPORTED)
    set_target_properties(GTest::gmock PROPERTIES
        IMPORTED_LOCATION   "${GTEST_MOCK_MAIN_LIBRARY}"
        INCLUDE_DIRECTORIES "${GTEST_INCLUDE_DIR}"
    )

    add_library(GTest::gmock_main STATIC IMPORTED)
    set_target_properties(GTest::gmock_main PROPERTIES
        IMPORTED_LOCATION   "${GTEST_MOCK_MAIN_LIBRARY}"
        INCLUDE_DIRECTORIES "${GTEST_INCLUDE_DIR}"
    )

    set (GTEST_CMAKE_MODULEDIR "${CMAKE_INSTALLED_PREFIX}/share/cmake-${CMAKE_MAJOR_VERSION}.${CMAKE_MINOR_VERSION}/Modules")

    message("GTEST_LIBRARY=${GTEST_LIBRARY}")
    message("GTEST_MAIN_LIBRARY=${GTEST_MAIN_LIBRARY}")
    message("GTEST_INCLUDE_DIR=${GTEST_INCLUDE_DIR}")
    message("GTEST_CMAKE_MODULEDIR=${GTEST_CMAKE_MODULEDIR}")

    include_directories("${GTEST_INCLUDE_DIR}")
    include(${GTEST_CMAKE_MODULEDIR}/GoogleTest.cmake)
endif()
