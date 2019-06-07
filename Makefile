# project name
TARGETS = projet_space_invaders

SOURCE = ./src
BIN = ./bin
MAIN_CLASS = Convertisseur
MODULES = javafx.base,javafx.fxml,javafx.media,javafx.controls,javafx.graphics,javafx.swing,javafx.web
DEPENDES = ./lib/javafx.base.jar:./lib/javafx.fxml.jar:./lib/javafx.media.jar:./lib/javafx.controls.jar:./lib/javafx.graphics.jar:./lib/javafx.swing.jar:./lib/javafx.web.jar


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
	javac -d ${BIN} -cp ${DEPENDES}:${SOURCE}/. ${SOURCE}/${MAIN_CLASS}.java

# RUN
run :
	@echo
	@echo Run main file
	@echo --------
	@echo
	java --module-path ./lib --add-modules ${MODULES} -cp ${BIN} ${MAIN_CLASS}
#java -cp ${DEPENDES}:${BIN} ${MAIN_CLASS}

