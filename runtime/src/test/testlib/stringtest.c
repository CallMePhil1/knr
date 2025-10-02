#include "stringtest.h";
#include <stdbool.h>
#include <stdio.h>
#include <string.h>

bool are_strs_equal(const char *str1, const char *str2) {
    return strcmp(str1, str2) == 0;
}

bool struct_str_equal(StringStruct *str_struct, const char *str) {
    return strcmp(str_struct->str_ptr, str) == 0;
}
