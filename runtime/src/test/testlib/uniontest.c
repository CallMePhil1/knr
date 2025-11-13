#include "uniontest.h";

char get_byte(union PrimitivesUnion* primUnion) {
    return primUnion->c;
}

short get_short(union PrimitivesUnion* primUnion) {
    return primUnion->c;
}

int get_int(union PrimitivesUnion* primUnion) {
    return primUnion->c;
}

int get_int_from_pointer(union PrimitivesUnion* primUnion) {
    return *(primUnion->p1);
}

long long get_long(union PrimitivesUnion* primUnion) {
    return primUnion->c;
}

long long get_long_from_pointer(union PrimitivesUnion* primUnion) {
    return *(primUnion->p);
}

void set_byte(union PrimitivesUnion* primUnion, char value) {
    primUnion->c = value;
}

void set_short(union PrimitivesUnion* primUnion, short value) {
    primUnion->c = value;
}

void set_int(union PrimitivesUnion* primUnion, int value) {
    primUnion->c = value;
}

void set_int_of_pointer(union PrimitivesUnion* primUnion, int value) {
    *(primUnion->p1) = value;
}

void set_long(union PrimitivesUnion* primUnion, long long value) {
    primUnion->c = value;
}

void set_long_of_pointer(union PrimitivesUnion* primUnion, long long value) {
    *(primUnion->p) = value;
}