<h1>LWJGL Engine</h1>

<h2>Setting up the project:</h2>
<p>
    This project is being made using the intellij IDE. Therefore I will only give instructions on how to build the engine for intellij. I will also supply instructions on how to run the program from the command line.
</p>

<h3>Downloading the relevant files:</h3>
<p>
    This engine uses both LWJGL and JOML. LWJGL is a java library that enables cross platform access to openGL API. LWJGL also comes with GLFW, which we use to create and bind an openGL context to a window.
    <a href="https://www.lwjgl.org/">LWJGL can be found here</a>
</p>
<p>
    JOML is also used for this engine. JOML is describes as a Java math library for OpenGL rendering calculations. In this engine it is used mainly for its Vector and Matrix classes
    <a href="https://github.com/JOML-CI/JOML">JOML can be found here</a>
</p>

<h3>Adding LWJGL:</h3>
<p>
    The libraries need to be added to the project in order for them to work. For this section I will only include how to do this in intellij. If you are using another IDE you will have to research this for yourself.
</p>
<ol>
    <li>
        First we are going to add LWJGL to the project. So open up the project in intellij (Open intellij -> File -> Open... -> locate the folder called engine).
    </li>
    <li>
        Next open the Project Structure menu (File -> Project Structure... OR press Ctrl + Alt + Shift + s).
    </li>
    <li>
        Now navigate to the modules tab on the left hand side of the Project Structure window.
    </li>
    <li>
        Now we need to add the jar file from inside the LWJGL download files. Click on the dependencies tab in the modules window.
    </li>
    <li>
        To add the jar files click on the small green plus sign in the top right corner of the dependencies tab. Then click on JARs or directories.
    </li>
    <li>
        Now locate the LWJGL jar file.
    </li>
    <li>
        Next we need to add the dependencies for LWJGL, these are found in the "native" folder. Copy these to another folder called libs (If you are on windows, there is already a folder called libs with the native files inside).
    </li>
    <li>
        Now we need to add a VM option before we can run anything. This is done by opening the edit configurations window (Run -> Edit Configurations...).  Find the VM Options box and enter the following. Where "libs" is the folder name containing the native files.
        <p><strong>-Djava.library.path=libs/</strong></p>
    </li>
</ol>

<h3>Running from CMD:</h3>
<p>In order to run the engine just click on the run.bat file. This just runs the specific command needed to run the pre-built class files in the command line. To find out how this works, open up the .bat file and search for any part of the java run string that you want to research</p>

<h2>To Do:</h2>
<ul>
    <li>Add a Scene class to contain the camera</li>
    <li>Add some functionality to the Boid class</li>
    <li>Look into creating the matrices within the classes instead of the renderer</li>
    <li>Create instance rendering method for multiple entities</li>
    <li>Organise some of the code</li>
</ul>

<h2>Change Log:</h2>
<ul>
    <li>Added support for scaling and rotation</li>
    <li>Created the Boid class</li>
</ul>
