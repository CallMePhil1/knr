cmake_minimum_required(VERSION 3.15)
project(testlib C)

set(CMAKE_C_STANDARD 11)

# Create a shared library from your source
add_library(arraytest SHARED arraytest.c)
add_library(primitivetest SHARED primitivetest.c)
add_library(pointertest SHARED pointertest.c)
add_library(stringtest SHARED stringtest.c)

# Export definitions so functions are visible on Windows
if(WIN32)
    target_compile_definitions(arraytest PRIVATE ARRAYTEST_EXPORTS)
    target_compile_definitions(primitivetest PRIVATE PRIMTEST_EXPORTS)
    target_compile_definitions(pointertest PRIVATE POINTERTEST_EXPORTS)
    target_compile_definitions(stringtest PRIVATE STRINGTEST_EXPORTS)
endif()