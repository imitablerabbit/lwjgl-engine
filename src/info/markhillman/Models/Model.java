package info.markhillman.Models;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glUniform1ui;

/**
 * Class: Model
 * Description: This is the basic mesh for a model, it contains the
 * vao and the vertices vbo.
 * Created by Mark on 04/12/2015.
 */
public class Model {

    private int vaoID;
    private int verticesID;
    private int normalsID;
    private float[] vertices;
    private float[] normals;

    public Model() {
        this(new float[] {
                -1.0f, -1.0f, -0.0f,
                1.0f, -1.0f, -0.0f,
                0.0f,  1.0f, -0.0f
        });
    }
    public Model(List<Float> vertices) {
        float[] verticesTemp = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            verticesTemp[i] = vertices.get(i);
        }
        this.vertices = verticesTemp;
        this.normals = new float[vertices.size()];
    }
    public Model(float[] vertices) {
        this(vertices, new float[vertices.length]);
    }
    public Model(float[] vertices, float[] normals) {
        this.vertices = vertices;
        this.normals = normals;
        bindAttributes();
    }
    public Model(List<Float> vertices, List<Float> normals) {
        float[] verticesTemp = new float[vertices.size()];
        float[] normalsTemp = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            verticesTemp[i] = vertices.get(i);
            normalsTemp[i] = normals.get(i);
        }
        this.vertices = verticesTemp;
        this.normals = normalsTemp;
        bindAttributes();
    }

    //Generate the vao and vbo
    private void bindAttributes() {

        //Create and bind a VAO
        this.vaoID = glGenVertexArrays();
        glBindVertexArray(this.vaoID);

        //Create and bind the vbo
        verticesID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesID);

        //Buffer the data to the vbo
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        //Create the normals vbo
        normalsID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalsID);

        //Buffer the data to the vbo
        FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
        normalsBuffer.put(normals).flip();
        glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);

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
    public float[] getNormals() {
        return normals;
    }
    public void setNormals(float[] normals) {
        this.normals = normals;
    }
    public int getNormalsID() {
        return normalsID;
    }
}
