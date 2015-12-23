package info.markhillman;

import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Scene;
import info.markhillman.Utils.EulerAngle;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class: Engine
 * Description: This is where the program starts, it will create the
 * openGL context and bind it to a GLFW window. The mvp matrix will
 * be created and sent to the renderer in order to render the shapes
 * in a 3D space.
 * Created by Mark on 01/12/2015.
 */

// TODO: 06/12/2015 Move the cursor and keycallbacks
// TODO: 08/12/2015 Change the keycallback to modify booleans and then move the camera every frame based off those
// TODO: 07/12/2015 Create an input handler
public class Engine {

    private GLFWErrorCallback errorCallback;
    private Window window;
    private Scene scene;
    private final int width = 600;
    private final int height = 600;

    public Engine() {
        try {
            init();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println("Could not initialise the engine");
        }
    }

    public void run() {
        // Start the engine and the game loop
        loop();

        //Destroy the window and the callbacks
        window.destroy();
        scene.getKeyCallback().release();
        scene.getCursorPosCallback().release();
        glfwTerminate();
        errorCallback.release();
    }

    //Initialize the whole engine and open the window
    private void init() {

        //Setup the error callback so that openGL can print errors
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        //Initialise GLFW
        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        //Create the window
        window = new Window("3D Engine", width, height);

        //Setup the cursor
        glfwSetInputMode(window.getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPos(window.getId(), 0, 0);

        //Make the OpenGL context current
        window.makeContext();
        window.show();

        //Create the scene
        scene = new Scene(window.getId());
    }

    //This is the game loop which will continuously run until the window is closed
    private void loop() {

        //Let LWJGL and openGL work together using GLFW
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        //Run the game and rendering loop.
        while (window.shouldClose() == GLFW_FALSE) {

            //Clear and swap the frame buffers
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Render and run the scene
            scene.run();
            scene.render();

            //Poll for events and make use of key callback
            window.swapBuffers();
            glfwPollEvents();
        }
    }

    //Getters and Setters
    public Window getWindow() {
        return window;
    }
    public void setWindow(Window window) {
        this.window = window;
    }
    public Scene getScene() {
        return scene;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
