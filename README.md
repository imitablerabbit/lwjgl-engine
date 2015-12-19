<h1>LWJGL Engine</h1>
<p>
    LWJGL is an openGL variant for Java programming, this engine is designed to use LWJGL in order to open and display 3D models as well as other various tasks.
</p>

<h2>Compiling and running the engine:</h3>
<p>
    First make sure that the latest release of Java is installed. Next, on a windows machine I have included a script to compile and run the engine (combile.bat & run.bat). This just runs the specific command needed to run the pre-built class files in the command line. On a non-windows machine you can use the below commands.
</p>
<h3>Compiling:</h3>
<p>
    In order to compile the java source files, you can use the javac command from the root of the project files:
</p>
```
    javac -d "out/production/Engine" -cp "libs/lwjgl.jar;out/production/Engine/" src/info/markhillman/Models/\*.java src/info/markhillman/Renderer/\*.java src/info/markhillman/Scene/\*.java src/info/markhillman/Utils/\*.java src/info/markhillman/\*.java
```
<p>
    This will compile the files into the out/production/Engine file path.
</p>
<h3>Running:</h3>
<p>
    In order to run the java class files, you can use the java command from the root of the project files:
</p>
```
    java -Djava.library.path=libs/ -cp "out/production/Engine/;libs/lwjgl.jar" info.markhillman.Main
```
<p>
    When this command is run a window will open up and display an exhibition of what the engine can currently do.
</p>


<h2>To Do:</h2>
<ul>
    <li>Add a Scene class to contain the camera</li>
    <li>Look into creating the matrices within the entities instead of the renderer</li>
    <ul>
        <li>Create the model matrices</li>
    </ul>
    <li>Add support for VBO indexing</li>
    <li>Organise some of the code</li>
</ul>

<h2>Change Log:</h2>
<ul>
    <li>Create the OBJ Loader</li>
    <li>Create the rotational matrices for the entities file</li>
    <li>Fix the boid class</li>
    <li>Add a list of all the boids created so they can affect each other</li>
    <li>Create instance rendering method for multiple entities</li>
    <li>Added support for scaling and rotation</li>
    <li>Created the Boid class</li>
</ul>
