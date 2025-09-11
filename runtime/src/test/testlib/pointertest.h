#ifndef POINTERTEST_H
#define POINTERTEST_H

#include <stdbool.h>

#ifdef _WIN32
  #ifdef POINTERTEST_EXPORTS
    #define API __declspec(dllexport)
  #else
    #define API __declspec(dllimport)
  #endif
#else
  #define API __attribute__((visibility("default")))
#endif

typedef struct {
    long l;
} PointedStruct;

typedef struct {
    int *i;
    PointedStruct *s;
} AllPointers;

API long get_long_from_pointer(long *l);

API void set_long_for_pointer(long *l, long value);

API int get_long_from_struct(PointedStruct *ps);

API void set_long_for_struct(PointedStruct *ps, long value);

API int get_int_via_pointer_from_struct(AllPointers *ap);

API void set_int_via_pointer_from_struct(AllPointers *ap, int value);

#endif