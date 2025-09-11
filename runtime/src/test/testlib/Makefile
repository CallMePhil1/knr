CC = gcc
CFLAGS = -Wall -Wextra -std=c11
TARGET = primativetest

SRCS = primativetest.c
HEADERS = primativetest.h

ifeq ($(OS),Windows_NT)
    TARGET_EXT = .dll
    LDFLAGS = -shared
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Darwin)
        TARGET_EXT = .dylib
        LDFLAGS = -shared
    else
        TARGET_EXT = .so
        LDFLAGS = -shared
    endif
endif

all: $(TARGET)$(TARGET_EXT)

$(TARGET)$(TARGET_EXT): $(SRCS) $(HEADERS)
	$(CC) $(CFLAGS) $(LDFLAGS) -o $@ $(SRCS)

clean:
	rm -f primativetest