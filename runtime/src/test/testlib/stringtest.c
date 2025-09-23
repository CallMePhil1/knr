#include "stringtest.h";
#include <stdbool.h>
#include <stdio.h>
#include <string.h>

bool are_strs_equal(const char *str1, const char *str2) {
    return strcmp(str1, str2) == 0;
}