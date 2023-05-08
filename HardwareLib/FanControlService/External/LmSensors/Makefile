CC = gcc
DEBUG_FLAGS = -g -Wall
RELEASE_FLAGS = -O2 -Wall
LIB = -lsensors
SRC = sensors_wrapper.c
OBJ_DEBUG = $(SRC:.c=_debug.o)
OBJ_RELEASE = $(SRC:.c=_release.o)
NAME = lsensorswrapper.so


# Debug Configuration
debug: $(OBJ_DEBUG)
	$(CC) $(DEBUG_FLAGS) -shared $^ -o $(NAME) $(LIB)

%_debug.o: %.c
	$(CC) $(DEBUG_FLAGS) -c -fPIC $< -o $@

# Release Configuration
release: $(OBJ_RELEASE)
	$(CC) $(RELEASE_FLAGS) -shared $^ -o $(NAME) $(LIB)

%_release.o: %.c
	$(CC) $(RELEASE_FLAGS) -c -fPIC $< -o $@

# Clean target
clean:
	rm -rf *.o *.gch *.so

.PHONY: debug release clean