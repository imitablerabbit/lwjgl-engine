package info.markhillman;

import info.markhillman.Exceptions.SingletonException;
import info.markhillman.Scene.Scene;
import info.markhillman.Utils.GameTimer;
import org.lwjgl.glfw.GLFWErrorCallback;

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

    // Static Singleton methods
    private static Engine engine;
    public static Engine getInstance() {
        if (engine == null) {
            try {
                new Engine();
            }
            catch (SingletonException e) {
                System.out.println(e);
            }
        }
        return engine;
    }

    // Engine instance methods and constructors
    private GLFWErrorCallback errorCallback;
    private Window window;
    private Scene scene;
    public static GameTimer timer;

    public Engine() throws SingletonException {
        this(500, 500, "3D Engine");
    }
    public Engine(int width, int height) throws SingletonException {
        this(width, height, "3D Engine");
    }
    public Engine(int width, int height, String title) throws SingletonException {

        // Singleton creation
        if (engine == null) {
            try {
                // Set the singleton instance
                engine = this;

                //Setup the error callback so that openGL can print errors
                glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

                //Initialise GLFW
                if (glfwInit() != GLFW_TRUE)
                    throw new IllegalStateException("Unable to initialize GLFW");

                //Create the window
                window = new Window(title, width, height);
                window.makeContext();
                window.show();

                //Setup the cursor
                glfwSetInputMode(window.getId(), GLFW_STICKY_KEYS, GL_TRUE);
                glfwSetInputMode(window.getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                glfwSetCursorPos(window.getId(), 0, 0);

                //Create the scene and timer
                scene = new Scene(window.getId());
                timer = new GameTimer();
            }
            catch (IllegalStateException e) {
                System.out.println("Could not initialise the engine");
                System.out.println(e.toString());
            }
        }
        else {
            throw new SingletonException("Duplicate instance attempted");
        }
    }

    //This will start the game Engine
    public void run() {

        //Start the game loop to run the scene
        loop();

        //Destroy the window and the callbacks
        window.destroy();
        scene.getKeyCallback().release();
        scene.getCursorPosCallback().release();
        glfwTerminate();
        errorCallback.release();
    }

    //This is the game loop which will continuously run until the window is closed
    private void loop() {

        //Set the clear color and enable openGL operations
        glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Run the game and rendering loop.
        while (window.shouldClose() == GLFW_FALSE) {

            timer.start();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Render and run the scene
            scene.run();
            scene.render();

            //Poll for events and swap buffers
            window.swapBuffers();
            glfwPollEvents();
            timer.update();
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
