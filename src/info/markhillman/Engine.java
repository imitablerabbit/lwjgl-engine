package info.markhillman;

import info.markhillman.Models.Boid;
import info.markhillman.Models.Entity;
import info.markhillman.Models.Model;
import info.markhillman.Models.ModelLoader;
import info.markhillman.Renderer.InstancedRenderer;
import info.markhillman.Scene.Camera;
import info.markhillman.Utils.EulerAngle;
import info.markhillman.Utils.ShaderLoader;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

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
// TODO: 07/12/2015 Create a scene class to hold the camera
// TODO: 07/12/2015 Create a OBJLoader and use the Model position
public class Engine {

    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private Window window;

    private double xPrev, yPrev;

    private final int width = 600;
    private final int height = 600;

    private Camera camera;

    public Engine() {

        try {
            // Start the engine and the game loop
            init();
            loop();

            //Destroy the window and the callbacks
            window.destroy();
            keyCallback.release();
            cursorPosCallback.release();
        }
        finally {
            //Terminate GLFW and release the GLFWErrorCallback
            glfwTerminate();
            errorCallback.release();
        }
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

        //Create the camera
        camera = new Camera(new Vector3f(0, 0, 0), 0, 180);

        //Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window.getId(), keyCallback = new GLFWKeyCallback() {

            float speed = 0.05f;

            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, GLFW_TRUE);
                else if (key == GLFW_KEY_W){

                    //Move the camera forward
                    camera.setPosition(camera.getPosition().sub(camera.getDirection().mul(speed)));
                    camera.update();
                } else if (key == GLFW_KEY_S) {

                    //Move the camera back
                    camera.setPosition(camera.getPosition().add(camera.getDirection().mul(speed)));
                    camera.update();
                } else if (key == GLFW_KEY_A) {

                    //Move the camera left
                    camera.setPosition(camera.getPosition().add(camera.getDirection().cross(new Vector3f(0, 1, 0)).mul(speed)));
                    camera.update();
                } else if (key == GLFW_KEY_D) {

                    //Move the camera left
                    camera.setPosition(camera.getPosition().sub(camera.getDirection().cross(new Vector3f(0, 1, 0)).mul(speed)));
                    camera.update();
                }
            }
        });

        //Setup the cursor position callback
        glfwSetCursorPosCallback(window.getId(), cursorPosCallback = new GLFWCursorPosCallback() {

            //Create the variables
            EulerAngle angles = new EulerAngle(0, 180);
            float sensitivity = 20;

            @Override
            public void invoke(long window, double x, double y) {

                //Update the camera
                angles.setYaw(angles.getYaw() + (float) ((x - xPrev) / sensitivity));
                angles.setPitch(angles.getPitch() + (float) ((y - yPrev) / sensitivity));
                camera.update(angles);

                xPrev = x;
                yPrev = y;
            }
        });

        //Make the OpenGL context current
        window.makeContext();
        window.show();
    }

    //This is the game loop which will continuously run until the window is closed
    private void loop() {

        //Let LWJGL and openGL work together using GLFW
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Create the shader program
        ShaderLoader shaderLoader = new ShaderLoader("Shaders/vertex.shader", "Shaders/fragment.shader");
        int programID = shaderLoader.getProgramID();
        glUseProgram(programID);

        //Create the renderer and the entity to render
        InstancedRenderer renderer = new InstancedRenderer(programID);
        List<Entity> entities = new ArrayList<>(0);
        Boid boid = new Boid();

        for (int i = 0; i < 1000; i++) {
            Boid b = boid.clone();
            b.setPosition(new Vector3f(
                    (float)(Math.random() * 80) - 40,
                    (float)(Math.random() * 80) - 40,
                    (float)(Math.random() * 80) - 40)
            );
            b.setScale(new Vector3f(0.8f, 0.3f, 0.3f));
            entities.add(b);
        }
        Map<Model, List<Entity>> map = renderer.assembleMap(entities);

        //Run the game and rendering loop.
        while (window.shouldClose() == GLFW_FALSE) {

            //Clear and swap the frame buffers
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Render the map of entities
            entities.forEach(entity -> entity.run());

            //System.out.println(boid.boids);
            renderer.renderEntityMap(map, camera.getView(), camera.getProjection());

            //Poll for events and make use of key callback
            window.swapBuffers();
            glfwPollEvents();
        }
    }
}
