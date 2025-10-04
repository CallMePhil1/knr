#ifndef ARRAYTEST_H
#define ARRAYTEST_H

#ifdef _WIN32
    #ifdef ARRAYTEST_EXPORTS
        #define API __declspec(dllexport)
    #else
        #define API __declspec(dllimport)
    #endif
#else
    #define API __attribute__(visibility("default"))
#endif

API signed char get_byte(signed char *arr, int index);

API void set_byte(signed char *arr, int index, signed char value);

#endif