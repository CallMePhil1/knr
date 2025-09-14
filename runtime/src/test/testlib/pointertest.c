#include "pointertest.h"
#include <stdlib.h>

long get_long_from_pointer(long *l) {
    return *l;
}

void set_long_for_pointer(long *l, long value) {
    *l = value;
}

int get_long_from_struct(PointedStruct *ps) {
    return ps->l;
}

void set_long_for_struct(PointedStruct *ps, long value) {
    ps->l = value;
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