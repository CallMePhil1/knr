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

API unsigned char get_ubyte(unsigned char *arr, int index);

API void set_ubyte(unsigned char *arr, int index, unsigned char value);

API signed short get_short(signed short *arr, int index);

API void set_short(signed short *arr, int index, signed short value);

API unsigned short get_ushort(unsigned short *arr, int index);

API void set_ushort(unsigned short *arr, int index, unsigned short value);

API signed int get_int(signed int *arr, int index);

API void set_int(signed int *arr, int index, signed int value);

API unsigned int get_uint(unsigned int *arr, int index);

API void set_uint(unsigned int *arr, int index, unsigned int value);

API signed long long get_long(signed long long *arr, int index);

API void set_long(signed long long *arr, int index, signed long long value);

#endif