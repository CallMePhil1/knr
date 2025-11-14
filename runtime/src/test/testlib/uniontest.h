#ifndef UNIONTEST_H
#define UNIONTEST_H

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
    int i;
    long long l;
} UnionStruct;

union PrimitivesUnion {
    char c;
    short s;
    int i;
    long long l;
    long long *p;
    int *p1;
    UnionStruct us;
};

API char get_byte(union PrimitivesUnion* primUnion);

API short get_short(union PrimitivesUnion* primUnion);

API int get_int(union PrimitivesUnion* primUnion);

API int get_int_from_pointer(union PrimitivesUnion* primUnion);

API int get_int_from_struct(union PrimitivesUnion* primUnion);

API long long get_long(union PrimitivesUnion* primUnion);

API long long get_long_from_pointer(union PrimitivesUnion* primUnion);

API long long get_long_from_struct(union PrimitivesUnion* primUnion);

API void set_byte(union PrimitivesUnion* primUnion, char value);

API void set_short(union PrimitivesUnion* primUnion, short value);

API void set_int(union PrimitivesUnion* primUnion, int value);

API void set_int_of_pointer(union PrimitivesUnion* primUnion, int value);

API void set_long(union PrimitivesUnion* primUnion, long long value);

API void set_long_of_pointer(union PrimitivesUnion* primUnion, long long value);

#endif