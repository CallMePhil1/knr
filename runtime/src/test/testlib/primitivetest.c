#include "primitivetest.h"

bool get_bool(AllPrimitives all_prim) {
    return all_prim.b;
}

char get_char(AllPrimitives all_prim) {
    return all_prim.c;
}

unsigned char get_uchar(AllPrimitives all_prim) {
    return all_prim.uc;
}

short get_short(AllPrimitives all_prim) {
    return all_prim.s;
}

unsigned short get_ushort(AllPrimitives all_prim) {
    return all_prim.us;
}

int get_int(AllPrimitives all_prim) {
    return all_prim.i;
}

unsigned int get_uint(AllPrimitives all_prim) {
    return all_prim.ui;
}

long get_long(AllPrimitives all_prim) {
    return all_prim.l;
}

unsigned long get_ulong(AllPrimitives all_prim) {
    return all_prim.ul;
}

long long get_longlong(AllPrimitives all_prim) {
    return all_prim.ll;
}

unsigned long long get_ulonglong(AllPrimitives all_prim) {
    return all_prim.ull;
}

float get_float(AllPrimitives all_prim) {
    return all_prim.f;
}

double get_double(AllPrimitives all_prim) {
    return all_prim.d;
}

int get_int_from_union(AllPrimitives* all_prim) {
    return all_prim->u.i;
}

long long get_long_from_union(AllPrimitives* all_prim) {
    return all_prim->u.l;
}

void set_bool(AllPrimitives *all_prim, bool value) {
    all_prim->b = value;
}

void set_char(AllPrimitives *all_prim, char value) {
    all_prim->c = value;
}

void set_uchar(AllPrimitives *all_prim, unsigned char value) {
    all_prim->uc = value;
}

void set_short(AllPrimitives *all_prim, short value) {
    all_prim->s = value;
}

void set_ushort(AllPrimitives *all_prim, unsigned short value) {
    all_prim->us = value;
}

void set_int(AllPrimitives *all_prim, int value) {
    all_prim->i = value;
}

void set_uint(AllPrimitives *all_prim, unsigned int value) {
    all_prim->ui = value;
}

void set_long(AllPrimitives *all_prim, long value) {
    all_prim->l = value;
}

void set_ulong(AllPrimitives *all_prim, unsigned long value) {
    all_prim->ul = value;
}

void set_longlong(AllPrimitives *all_prim, long long value) {
    all_prim->ll = value;
}

void set_ulonglong(AllPrimitives *all_prim, unsigned long long value) {
    all_prim->ull = value;
}

void set_float(AllPrimitives *all_prim, float value) {
    all_prim->f = value;
}

void set_double(AllPrimitives *all_prim, double value) {
    all_prim->d = value;
}

void set_int_from_union(AllPrimitives* all_prim, int value) {
    all_prim->u.i = value;
}

void set_long_from_union(AllPrimitives* all_prim, long long value) {
    all_prim->u.l = value;
}