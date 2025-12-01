#ifndef PRIMLIB_H
#define PRIMLIB_H

#include <stdbool.h>

#ifdef _WIN32
    #ifdef PRIMLIB_EXPORTS
        #define API __declspec(dllexport)
    #else
        #define API __declspec(dllimport)
    #endif
#else
    #define API __attribute__(visibility("default"))
#endif

typedef struct {
    char c;
    unsigned char uc;

    short s;
    unsigned short us;

    int i;
    unsigned ui;

    long long l;
    unsigned long long ul;

    float f;
    double d;

    bool b;
} PrimitiveStruct;

API bool get_bool(PrimitiveStruct primStruct);

API char get_byte(PrimitiveStruct primStruct);

API unsigned char get_ubyte(PrimitiveStruct primStruct);

API short get_short(PrimitiveStruct primStruct);

API unsigned short get_ushort(PrimitiveStruct primStruct);

API int get_int(PrimitiveStruct primStruct);

API unsigned int get_uint(PrimitiveStruct primStruct);

API long long get_long(PrimitiveStruct primStruct);

API unsigned long long get_ulong(PrimitiveStruct primStruct);

API float get_float(PrimitiveStruct primStruct);

API double get_double(PrimitiveStruct primStruct);

API void set_bool(PrimitiveStruct *primStruct, bool value);

API void set_byte(PrimitiveStruct *primStruct, char value);

API void set_ubyte(PrimitiveStruct *primStruct, unsigned char value);

API void set_short(PrimitiveStruct *primStruct, short value);

API void set_ushort(PrimitiveStruct *primStruct, unsigned short value);

API void set_int(PrimitiveStruct *primStruct, int value);

API void set_uint(PrimitiveStruct *primStruct, unsigned int value);

API void set_long(PrimitiveStruct *primStruct, long long value);

API void set_ulong(PrimitiveStruct *primStruct, unsigned long long value);

API void set_float(PrimitiveStruct *primStruct, float value);

API void set_double(PrimitiveStruct *primStruct, double value);

#endif