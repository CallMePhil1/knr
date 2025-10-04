#include "arraytest.h"

signed char get_byte(signed char *arr, int index) {
    return arr[index];
}

void set_byte(signed char *arr, int index, signed char value) {
    arr[index] = value;
}