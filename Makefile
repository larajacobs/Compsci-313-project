# ONLY ADDED THE ONES NEEDED FOR JAVAFX

# Variables
# Compile Flags
JAVAC_FLAGS = --module-path $(PATH_TO_FX)/lib --add-modules javafx.controls,javafx.fxml
# Run Flags
JAVA_FLAGS = --module-path $(PATH_TO_FX)/lib --add-modules javafx.controls,javafx.fxml
# Sources
SRC_DIR = src
MAIN = GUI

# Run and Compile
run: $(MAIN).class
$(MAIN).class: $(SRC_DIR)/$(MAIN).java
	javac $(JAVAC_FLAGS) $(SRC_DIR)/$(MAIN).java
	java $(JAVA_FLAGS) $(SRC_DIR).$(MAIN)

# Clean .class file
clean:
	rm -f $(SRC_DIR)/$(MAIN).class
	rm -f $(SRC_DIR)/Client.class
	rm -f $(SRC_DIR)/Message.class
