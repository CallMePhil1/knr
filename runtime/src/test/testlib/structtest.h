#ifndef STRUCTTEST_H
#define STRUCTTEST_H

#include <stdbool.h>

#ifdef _WIN32
    #ifdef STRUCTTEST_EXPORTS
        #define API __declspec(dllexport)
    #else
        #define API __declspec(dllimport)
    #endif
#else
    #define API __attribute__(visibility("default"))
#endif

union StructUnion {
    int i;
    long long l;
};

typedef struct {
    int i;
    long long l;
} InnerStruct;

typedef struct {
    char c;
    unsigned char uc;

    short s;
    unsigned short us;

    int i;
    unsigned ui;

    long l;
    unsigned long ul;

    long long ll;
    unsigned long long ull;

    float f;
    double d;

    bool b;

    union StructUnion u;

    InnerStruct is;
    InnerStruct sa[4];
    int* pa[4];
} AllPrimitives;

API bool get_bool(AllPrimitives all_prim);

API char get_char(AllPrimitives all_prim);

API unsigned char get_uchar(AllPrimitives all_prim);

API short get_short(AllPrimitives all_prim);

API unsigned short get_ushort(AllPrimitives all_prim);

API int get_int(AllPrimitives all_prim);

API unsigned int get_uint(AllPrimitives all_prim);

API long get_long(AllPrimitives all_prim);

API unsigned long get_ulong(AllPrimitives all_prim);

API long long get_longlong(AllPrimitives all_prim);

API unsigned long long get_ulonglong(AllPrimitives all_prim);

API float get_float(AllPrimitives all_prim);

API double get_double(AllPrimitives all_prim);

API int get_int_from_union(AllPrimitives* all_prim);

API long long get_long_from_union(AllPrimitives* all_prim);

API int get_int_from_inner_struct(AllPrimitives* all_prim);

API long long get_long_from_inner_struct(AllPrimitives* all_prim);

API InnerStruct get_struct_from_array(AllPrimitives* all_prim, int index);

API int get_int_from_pointer_array(AllPrimitives* all_prim, int index);

API void set_bool(AllPrimitives *all_prim, bool value);

API void set_char(AllPrimitives *all_prim, char value);

API void set_uchar(AllPrimitives *all_prim, unsigned char value);

API void set_short(AllPrimitives *all_prim, short value);

API void set_ushort(AllPrimitives *all_prim, unsigned short value);

API void set_int(AllPrimitives *all_prim, int value);

API void set_uint(AllPrimitives *all_prim, unsigned int value);

API void set_long(AllPrimitives *all_prim, long value);

API void set_ulong(AllPrimitives *all_prim, unsigned long value);

API void set_longlong(AllPrimitives *all_prim, long long value);

API void set_ulonglong(AllPrimitives *all_prim, unsigned long long value);

API void set_float(AllPrimitives *all_prim, float value);

API void set_double(AllPrimitives *all_prim, double value);

API void set_int_for_union(AllPrimitives* all_prim, int value);

API void set_long_for_union(AllPrimitives* all_prim, long long value);

API void set_int_for_inner_struct(AllPrimitives* all_prim, int value);

API void set_long_for_inner_struct(AllPrimitives* all_prim, long long value);

API void set_struct_for_array(AllPrimitives* all_prim, int index, InnerStruct value);

API void set_int_for_pointer_array(AllPrimitives* all_prim, int index, int value);

#endif