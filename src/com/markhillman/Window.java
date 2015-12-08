package com.markhillman;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Class: Window
 * Description: This will create the window using GLFW. It
 * will also handle any of the general window functions for
 * GLFW in a more OOP style
 * Created by Mark on 01/12/2015.
 */
public class Window {

    private long id;
    private String title;
    private int width;
    private int height;

    public Window() {
        this("New Window", 600, 600);
    }
    public Window(String title, int width, int height) {

        this.title = title;
        this.width = width;
        this.height = height;

        //Create the GLFW window and assign the id
        this.id = glfwCreateWindow(width, height, title, NULL, NULL);
        if (id == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        //Configure the window hints
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    }

    //Make this window the current context
    public void makeContext() {
        glfwMakeContextCurrent(id);
    }

    //Display the window on the screen
    public void show() {
        glfwShowWindow(id);
    }

    //Destroy this window
    public void destroy() {
        glfwDestroyWindow(id);
    }

    //Returns whether this window should close
    public int shouldClose() {
        return glfwWindowShouldClose(id);
    }

    //Swap the current buffers to stop tearing
    public void swapBuffers() {
        glfwSwapBuffers(id);
    }

    //Return the current windows id
    public long getId() {
        return this.id;
    }
}
