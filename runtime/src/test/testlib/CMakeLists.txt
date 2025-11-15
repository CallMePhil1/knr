cmake_minimum_required(VERSION 3.15)
project(testlib C)

set(CMAKE_C_STANDARD 11)

# Create a shared library from your source
add_library(arraytest SHARED arraytest.c)
add_library(structtest SHARED structtest.c)
add_library(pointertest SHARED pointertest.c)
add_library(stringtest SHARED stringtest.c)
add_library(uniontest SHARED uniontest.c)

# Export definitions so functions are visible on Windows
if(WIN32)
    target_compile_definitions(arraytest PRIVATE ARRAYTEST_EXPORTS)
    target_compile_definitions(structtest PRIVATE STRUCTTEST_EXPORTS)
    target_compile_definitions(pointertest PRIVATE POINTERTEST_EXPORTS)
    target_compile_definitions(stringtest PRIVATE STRINGTEST_EXPORTS)
    target_compile_definitions(uniontest PRIVATE UNIONTEST_EXPORTS)
endif()