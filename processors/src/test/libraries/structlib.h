#ifndef STRUCTLIB_H
#define STRUCTLIB_H

#include <stdbool.h>

#ifdef _WIN32
    #ifdef STRUCTLIB_EXPORTS
        #define API __declspec(dllexport)
    #else
        #define API __declspec(dllimport)
    #endif
#else
    #define API __attribute__(visibility("default"))
#endif

typedef struct {
    int i;
} TestStruct;

API void disposeStruct(TestStruct *testStruct);

API void passByPointer(TestStruct *testStruct, int value);

API void passByRef(TestStruct *testStruct, int value);

API TestStruct *returnByPointer(int value);

API TestStruct *returnByRef(int value);

API TestStruct returnByValue(int value);

API TestStruct *getStructInArray(int index);

API void setStructInArray(int index, TestStruct testStruct);

API void setValueInArray(int index, int value);

#endif