#include "primitivelib.h"

bool get_bool(PrimitiveStruct primStruct) {
    return primStruct.b;
}

char get_byte(PrimitiveStruct primStruct) {
    return primStruct.c;
}

unsigned char get_ubyte(PrimitiveStruct primStruct) {
    return primStruct.uc;
}

short get_short(PrimitiveStruct primStruct) {
    return primStruct.s;
}

unsigned short get_ushort(PrimitiveStruct primStruct) {
    return primStruct.us;
}

int get_int(PrimitiveStruct primStruct) {
    return primStruct.i;
}

unsigned int get_uint(PrimitiveStruct primStruct) {
    return primStruct.ui;
}

long long get_long(PrimitiveStruct primStruct) {
    return primStruct.l;
}

unsigned long long get_ulong(PrimitiveStruct primStruct) {
    return primStruct.ul;
}

float get_float(PrimitiveStruct primStruct) {
    return primStruct.f;
}

double get_double(PrimitiveStruct primStruct) {
    return primStruct.d;
}

void set_bool(PrimitiveStruct *primStruct, bool value) {
    primStruct->b = value;
}

void set_byte(PrimitiveStruct *primStruct, char value) {
    primStruct->c = value;
}

void set_ubyte(PrimitiveStruct *primStruct, unsigned char value) {
    primStruct->uc = value;
}

void set_short(PrimitiveStruct *primStruct, short value) {
    primStruct->s = value;
}

void set_ushort(PrimitiveStruct *primStruct, unsigned short value) {
    primStruct->us = value;
}

void set_int(PrimitiveStruct *primStruct, int value) {
    primStruct->i = value;
}

void set_uint(PrimitiveStruct *primStruct, unsigned int value) {
    primStruct->ui = value;
}

void set_long(PrimitiveStruct *primStruct, long long value) {
    primStruct->l = value;
}

void set_ulong(PrimitiveStruct *primStruct, unsigned long long value) {
    primStruct->ul = value;
}

void set_float(PrimitiveStruct *primStruct, float value) {
    primStruct->f = value;
}

void set_double(PrimitiveStruct *primStruct, double value) {
    primStruct->d = value;
}