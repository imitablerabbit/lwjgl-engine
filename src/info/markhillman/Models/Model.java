package info.markhillman.Models;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

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
    private int normalsID;
    private int uvsID;
    private float[] vertices;
    private float[] normals;
    private float[] uvs;
    private Material material;

    public Model() {
        this(new float[] {
                -1.0f, -1.0f, -0.0f,
                1.0f, -1.0f, -0.0f,
                0.0f,  1.0f, -0.0f
        });
    }

    //Array constructors
    public Model(float[] vertices) {
        this.vertices = vertices;
        this.material = new Material();
    }
    public Model(float[] vertices, float[] normals) {
        this(vertices, normals, new Material());
    }
    public Model(float[] vertices, float[] normals, float[] uvs) {
        this(vertices, normals, uvs, new Material());
    }
    public Model(float[] vertices, float[] normals, Material material) {
        this.vertices = vertices;
        this.normals = normals;
        this.material = material;
        bindAttributes();
    }
    public Model(float[] vertices, float[] normals, float[] uvs, Material material) {
        this.vertices = vertices;
        this.normals = normals;
        this.material = material;
        this.uvs = uvs;
        bindAttributes();
    }

    //List constructors
    public Model(List<Float> vertices) {
        float[] verticesTemp = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            verticesTemp[i] = vertices.get(i);
        }
        this.vertices = verticesTemp;
        this.material = new Material();
    }
    public Model(List<Float> vertices, List<Float> normals, List<Float> uvs) {
        this(vertices, normals, uvs, new Material());
    }
    public Model(List<Float> vertices, List<Float> normals, List<Float> uvs, Material material) {
        float[] verticesTemp = new float[vertices.size()];
        float[] normalsTemp = new float[normals.size()];
        float[] uvsTemp = new float[uvs.size()];
        for (int i = 0; i < vertices.size(); i++) {
            verticesTemp[i] = vertices.get(i);
        }
        for (int i = 0; i < normals.size(); i++) {
            normalsTemp[i] = normals.get(i);
        }
        for (int i = 0; i < uvs.size(); i++) {
            uvsTemp[i] = uvs.get(i);
        }
        this.vertices = verticesTemp;
        this.normals = normalsTemp;
        this.uvs = uvsTemp;
        this.material = material;
        bindAttributes();
    }

    //Generate the vao and vbo
    private void bindAttributes() {

        //Create and bind a VAO
        this.vaoID = glGenVertexArrays();
        glBindVertexArray(this.vaoID);

        //Create and bind the vertices
        if (vertices.length > 0) {
            verticesID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, verticesID);

            //Buffer the data to the vbo
            FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
            verticesBuffer.put(vertices).flip();
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        }
        else {
            System.out.println("No vertices were found for " + this);
            return;
        }

        //Create and buffer the normals if there is some in the array
        if (normals.length > 0) {
            normalsID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, normalsID);

            //Buffer the data to the vbo
            FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
            normalsBuffer.put(normals).flip();
            glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
        }
        else {
            System.out.println("No normals were found for " + this);
        }

        //Create and buffer the UVS if there is some in the array
        if (uvs.length > 0) {
            uvsID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, uvsID);

            //Buffer the data to the vbo
            FloatBuffer uvBuffer = BufferUtils.createFloatBuffer(uvs.length);
            uvBuffer.put(uvs).flip();
            glBufferData(GL_ARRAY_BUFFER, uvBuffer, GL_STATIC_DRAW);
        }
        else {
            System.out.println("No UVs were found for " + this);
        }

        //Unbind vao and vbo
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    //Getters and Setters
    public int getVaoID() {
        return vaoID;
    }
    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }
    public int getVerticesID() {
        return verticesID;
    }
    public void setVerticesID(int verticesID) {
        this.verticesID = verticesID;
    }
    public int getNormalsID() {
        return normalsID;
    }
    public void setNormalsID(int normalsID) {
        this.normalsID = normalsID;
    }
    public int getUvsID() {
        return uvsID;
    }
    public void setUvsID(int uvsID) {
        this.uvsID = uvsID;
    }
    public float[] getVertices() {
        return vertices;
    }
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }
    public float[] getNormals() {
        return normals;
    }
    public void setNormals(float[] normals) {
        this.normals = normals;
    }
    public float[] getUvs() {
        return uvs;
    }
    public void setUvs(float[] uvs) {
        this.uvs = uvs;
    }
    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }
    public int getVerticesSize() {
        return vertices.length;
    }
}
