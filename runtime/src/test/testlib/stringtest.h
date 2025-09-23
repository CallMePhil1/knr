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
  #define API __attribute__((visibility("default")))
#endif

API bool are_strs_equal(const char *str1, const char *str2);

#endif