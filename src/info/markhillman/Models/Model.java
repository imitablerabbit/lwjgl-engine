package info.markhillman.Models;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Class: Model
 * Description: This is the basic mesh for a model, it contains the
 * vao and the vertices vbo.
 * Created by Mark on 04/12/2015.
 */
public class Model {

    private int vaoID;
    private int verticesID;
    private float[] vertices;

    public Model() {
        this(new float[] {
                -1.0f, -1.0f, -0.0f,
                1.0f, -1.0f, -0.0f,
                0.0f,  1.0f, -0.0f
        });
    }
    public Model(float[] vertices) {

        //Set the variables
        this.vertices = vertices;

        //Create and bind a VAO
        this.vaoID = glGenVertexArrays();
        glBindVertexArray(this.vaoID);

        //Create and bind the vbo
        verticesID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesID);

        //Buffer the data to the vbo
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        //Unbind vao and vbo
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    //Getters and Setters
    public int getVaoID() {
        return this.vaoID;
    }
    public int getVerticesID() {
        return this.verticesID;
    }
    public float[] getVertices() {
        return vertices;
    }
    public int getVerticesSize() {
        return this.vertices.length;
    }
}
