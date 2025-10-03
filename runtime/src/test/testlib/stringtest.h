#ifndef STRINGTEST_H
#define STRINGTEST_H

#include <stdbool.h>

#ifdef _WIN32
    #ifdef STRINGTEST_EXPORTS
        #define API __declspec(dllexport)
    #else
        #define API __declspec(dllimport)
    #endif
#else
    #define API __attribute__(visibility("default"))
#endif

typedef struct {
    char *str_ptr;
    char *cached_str_ptr;
} StringStruct;

API bool are_strs_equal(const char *str1, const char *str2);

API bool struct_str_equal(StringStruct *str_struct, const char *str);

#endif