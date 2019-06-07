# project name
TARGETS = projet_space_invaders

SOURCE = ./src
BIN = ./bin
MAIN_CLASS = App


#########
# Rules #
#########

# ALL
all : clean build run

# CLEAN
clean :
	@echo
	@echo Cleaning : object files
	@echo --------
	@echo
	rm -Rf ${BIN}/*

# BUILD
build :
	@echo
	@echo Building project \(source files\)
	@echo --------
	@echo
	javac -d ${BIN} -cp ${SOURCE}/. ${SOURCE}/${MAIN_CLASS}.java

# RUN
run :
	@echo
	@echo Run main file
	@echo --------
	@echo
	java -cp ${BIN} ${MAIN_CLASS}

