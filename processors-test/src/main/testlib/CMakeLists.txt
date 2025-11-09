cmake_minimum_required(VERSION 3.15)
project(testlib C)

set(CMAKE_C_STANDARD 11)

# Create a shared library from your source
add_library(primitivetest SHARED primitivetest.c)

# Export definitions so functions are visible on Windows
if(WIN32)
    target_compile_definitions(primitivetest PRIVATE PRIMTEST_EXPORTS)
endif()