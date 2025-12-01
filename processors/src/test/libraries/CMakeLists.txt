cmake_minimum_required(VERSION 3.15)
project(testlib C)

set(CMAKE_C_STANDARD 11)

# Create a shared library from your source
add_library(primitivelib SHARED primitivelib.c)
add_library(structlib SHARED structlib.c)

# Export definitions so functions are visible on Windows
if(WIN32)
    target_compile_definitions(primitivelib PRIVATE PRIMLIB_EXPORTS)
    target_compile_definitions(structlib PRIVATE STRUCTLIB_EXPORTS)
endif()