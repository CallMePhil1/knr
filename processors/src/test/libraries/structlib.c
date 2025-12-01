#include "structlib.h"
#include <stdlib.h>

TestStruct testStructs[10];

void disposeStruct(TestStruct *testStruct) {
    testStruct->i = -1;
}

void passByPointer(TestStruct *testStruct, int value) {
    testStruct->i = value;
}

void passByRef(TestStruct *testStruct, int value) {
    testStruct->i = value;
}

TestStruct *returnByRef(int value) {
    TestStruct *testStruct = malloc(sizeof(TestStruct));
    testStruct->i = value;
    return testStruct;
}

TestStruct returnByValue(int value) {
    TestStruct testStruct;
    testStruct.i = value;
    return testStruct;
}

TestStruct *returnByPointer(int value) {
    TestStruct *testStruct = malloc(sizeof(TestStruct));
    testStruct->i = value;
    return testStruct;
}

TestStruct *getStructInArray(int index) {
    return &testStructs[index];
}

void setStructInArray(int index, TestStruct testStruct) {
    testStructs[index] = testStruct;
}

void setValueInArray(int index, int value) {
    testStructs[index].i = value;
}
