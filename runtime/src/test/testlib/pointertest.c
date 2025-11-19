#include "pointertest.h"
#include <stdlib.h>

char get_byte_from_pointer(char *l) {
    return *l;
}

void set_byte_for_pointer(char *l, char value) {
    *l = value;
}

unsigned char get_ubyte_from_pointer(unsigned char *l) {
    return *l;
}

void set_ubyte_for_pointer(unsigned char *l, unsigned char value) {
    *l = value;
}

short get_short_from_pointer(short *l) {
    return *l;
}

void set_short_for_pointer(short *l, short value) {
    *l = value;
}

unsigned short get_ushort_from_pointer(unsigned short *l) {
    return *l;
}

void set_ushort_for_pointer(unsigned short *l, unsigned short value) {
    *l = value;
}

int get_int_from_pointer(int *l) {
    return *l;
}

void set_int_for_pointer(int *l, int value) {
    *l = value;
}

unsigned int get_uint_from_pointer(unsigned int *l) {
    return *l;
}

void set_uint_for_pointer(unsigned int *l, unsigned int value) {
    *l = value;
}

long long get_long_long_from_pointer(long long *l) {
    return *l;
}

void set_long_long_for_pointer(long long *l, long long value) {
    *l = value;
}

unsigned long long get_ulong_long_from_pointer(unsigned long long *l) {
    return *l;
}

void set_ulong_long_for_pointer(unsigned long long *l, unsigned long long value) {
    *l = value;
}

int get_long_from_struct(PointedStruct *ps) {
    return ps->l;
}

void set_long_for_struct(PointedStruct *ps, long value) {
    ps->l = value;
}

char get_byte_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    return *(ap->b);
}

void set_byte_via_pointer_from_struct(AllPointers *ap, char value) {
    if (ap == NULL)
        return;
    *(ap->b) = value;
}

char get_nullable_byte_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    if (ap->nb == NULL)
        return -2;
    
    return *(ap->nb);
}

void set_nullable_byte_via_pointer_from_struct(AllPointers *ap, char value) {
    *(ap->nb) = value;
}

short get_short_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    return *(ap->s);
}

void set_short_via_pointer_from_struct(AllPointers *ap, short value) {
    if (ap == NULL)
        return;
    *(ap->s) = value;
}

short get_nullable_short_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    if (ap->ns == NULL)
        return -2;
    
    return *(ap->ns);
}

void set_nullable_short_via_pointer_from_struct(AllPointers *ap, short value) {
    *(ap->ns) = value;
}

int get_int_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    return *(ap->i);
}

void set_int_via_pointer_from_struct(AllPointers *ap, int value) {
    if (ap == NULL)
        return;
    *(ap->i) = value;
}

int get_nullable_int_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    if (ap->ni == NULL)
        return -2;
    
    return *(ap->ni);
}

void set_nullable_int_via_pointer_from_struct(AllPointers *ap, int value) {
    *(ap->ni) = value;
}

long long get_long_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    return *(ap->l);
}

void set_long_via_pointer_from_struct(AllPointers *ap, long long value) {
    if (ap == NULL)
        return;
    *(ap->l) = value;
}

long long get_nullable_long_via_pointer_from_struct(AllPointers *ap) {
    if (ap == NULL)
        return -1;
    if (ap->nl == NULL)
        return -2;
    
    return *(ap->nl);
}

void set_nullable_long_via_pointer_from_struct(AllPointers *ap, long long value) {
    *(ap->nl) = value;
}