# Author: Abid H. Mujtaba
# Date: 2013-01-06
#
# This Makefile automates the building of the project using gradle. It also has functionality for sending the code to the EV3 and running it on it, using the Wifi connection (and SSH).

# This is the name of the Project as well as the main class (the class that contains the main() function where execution begins.
NAME="Rover"

# We define the PHONY Targets i.e. targets that do NOT correspond to actual files and so are run by being called explicitly along with make or as prerequisites for other targets.
.PHONY: default, build, run, run_jar

# Since this is the first rule in the Makefile this will be run if one issues only "make" at the prompt.
default: build


# We define the output file Rover.jar. "make" will study its timestamp to determine whether it needs to be recompiled.
build: build/libs/Rover.jar

# We declare the prequisites of the .jar file. Any time the prerequisites are newer than the target the recipe is run.
build/libs/Rover.jar:	src/Rover.java build.gradle
# Straight-forward recipe for recompiling the source code to get the .jar file
	gradle build		

# EMPTY target for sending the .class file to the EV3 using ssh. It has "build" as its prerequisite so it will check if the source code needs to be built before sending the file to the EV3.
# The "touch sync" at the end ensures that the empty "sync" file in the folder has its timestamp updated. This means this target will not run again until its dependencies are newer than the targetted empty file.
sync: build
	scp build/classes/main/$(NAME).class ev3:/home/lejos/programs/
	touch sync

# EMPTY target for sending the .jar file to the EV3. This is the file that can be launched directly from the EV3.
sync_jar: build
	scp build/libs/$(NAME).jar ev3:/home/lejos/programs/
	touch sync_jar

# PHONY target to actually run the .class file on the EV3 remotely. It will build and/or sync the .class file to the EV3 before running if needed.
run: sync
# We use ssh to run the compound command on the EV3. The command first changes to the correct directory and then uses "jrun" to execute the main class of the .class file.
	ssh ev3 "cd /home/lejos/programs && jrun $(NAME)"

# PHONY target to actually run the .jar file on the EV3 remotely. It will build and/or sync the .jar file to the EV3 before running if needed.
run_jar: sync_jar
	ssh ev3 "cd /home/lejos/programs && jrun -cp $(NAME).jar $(NAME)"
