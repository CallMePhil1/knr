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
  #define API __attribute__(visibility("default"))
#endif

typedef struct {
    long l;
} PointedStruct;

typedef struct {
    int *i;
    int *ni;
    PointedStruct *s;
} AllPointers;

API char get_byte_from_pointer(char *l);

API void set_byte_for_pointer(char *l, char value);

API unsigned char get_ubyte_from_pointer(unsigned char *l);

API void set_ubyte_for_pointer(unsigned char *l, unsigned char value);

API short get_short_from_pointer(short *l);

API void set_short_for_pointer(short *l, short value);

API unsigned short get_ushort_from_pointer(unsigned short *l);

API void set_ushort_for_pointer(unsigned short *l, unsigned short value);

API int get_int_from_pointer(int *l);

API void set_int_for_pointer(int *l, int value);

API unsigned int get_uint_from_pointer(unsigned int *l);

API void set_uint_for_pointer(unsigned int *l, unsigned int value);

API long long get_long_long_from_pointer(long long *l);

API void set_long_long_for_pointer(long long *l, long long value);

API unsigned long long get_ulong_long_from_pointer(unsigned long long *l);

API void set_ulong_long_for_pointer(unsigned long long *l, unsigned long long value);

API int get_long_from_struct(PointedStruct *ps);

API void set_long_for_struct(PointedStruct *ps, long value);

API int get_int_via_pointer_from_struct(AllPointers *ap);

API void set_int_via_pointer_from_struct(AllPointers *ap, int value);

API int get_nullable_int_via_pointer_from_struct(AllPointers *ap);

API void set_nullable_int_via_pointer_from_struct(AllPointers *ap, int value);

#endif