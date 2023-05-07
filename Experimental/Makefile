CC = gcc
FLAGS = -g -Wall
EXE = main
LIB = -lsensors





build: main.o sensors.o
	$(CC) $(FLAGS) $^ -o $(EXE) $(LIB)


run: build
	./main


%.o:%.c
	$(CC) $(FLAGS) -c $< $(LIB)

clean:
	rm -rf *.o *.gch *.bin $(EXE) 


