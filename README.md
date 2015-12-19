#LWJGL Engine#

LWJGL is an openGL variant for Java programming, this engine is designed to use LWJGL in order to open and display 3D models as well as other various tasks.

##Compiling and running the engine:##

First make sure that the latest release of Java is installed. Next, on a windows machine I have included a script to compile and run the engine (combile.bat & run.bat). This just runs the specific command needed to run the pre-built class files in the command line. On a non-windows machine you can use the below commands.

###Compiling:###

In order to compile the java source files, you can use the javac command from the root of the project files:

```
javac -d "out/production/Engine" -cp "libs/lwjgl.jar;out/production/Engine/" src/info/markhillman/Models/\*.java src/info/markhillman/Renderer/\*.java src/info/markhillman/Scene/\*.java src/info/markhillman/Utils/\*.java src/info/markhillman/\*.java
```

This will compile the files into the out/production/Engine file path.

###Running:###

In order to run the java class files, you can use the java command from the root of the project files:

```
java -Djava.library.path=libs/ -cp "out/production/Engine/;libs/lwjgl.jar" info.markhillman.Main
```

When this command is run a window will open up and display an exhibition of what the engine can currently do.

##Screenshots##

![Boid simulation screenshot](/res/screenshots/screenshot1.png?raw=true "Boid Simulation")

##To Do:##
- Add a Scene class to contain the camera</li>
- Look into creating the matrices within the entities instead of the renderer
  - Create the model matrices
- Add support for VBO indexing
- Organise some of the code

##Change Log:##
- Create the OBJ Loader
- Create the rotational matrices for the entities file
- Fix the boid class
- Add a list of all the boids created so they can affect each other
- Create instance rendering method for multiple entities
- Added support for scaling and rotation
- Created the Boid class
