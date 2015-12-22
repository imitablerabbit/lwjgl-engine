package info.markhillman.Renderer;

import info.markhillman.Models.Entity;
import info.markhillman.Models.Material;
import info.markhillman.Models.Model;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Class: Renderer
 * Description: This class will render any model and entity as
 * well as send all of the information to the shader such
 * as the vertices or the uniform variables.
 * Created by Mark on 04/12/2015.
 */
public class Renderer {

    protected int programID;

    public Renderer(int programID) {
        this.programID = programID;
    }

    //This will bind the models vao and vbo
    protected void bindModel(Model model) {

        //Bind the vao and vbo
        glBindVertexArray(model.getVaoID());

        //Send the vertices
        if (model.getVertices().length > 0) {
            glBindBuffer(GL_ARRAY_BUFFER, model.getVerticesID());
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(0);
        }

        //Send the normals
        if (model.getNormals().length > 0) {
            glBindBuffer(GL_ARRAY_BUFFER, model.getNormalsID());
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(1);
        }

        //Send the uvs
        if (model.getUvs().length > 0) {
            glBindBuffer(GL_ARRAY_BUFFER, model.getUvsID());
            glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(2);
        }
    }

    //This will clear any vao and vbo bindings
    protected void unbindModel() {

        //Unbind the vao and vbo
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    //This will bind and buffer all the data from the model
    public void renderModel(Model model) {

        //Bind and render the model
        bindModel(model);
        glDrawArrays(GL_TRIANGLES, 0, model.getVerticesSize());
        unbindModel();
    }

    //This will send a Matrix4f to the shader as a uniform variable
    protected void sendMatrices(Matrix4f matrix, String uniformName) {

        //Create the uniformLocation
        int uniformLocation = glGetUniformLocation(programID, uniformName);

        //Create the buffer and buffer the data to it
        float[] array = new float[16];
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(array);
        buffer.put(array).flip();
        glUniformMatrix4fv(uniformLocation, false, buffer);
    }

    //This will create and load a float into a uniform variable
    protected void sendFloat(float f, String uniformName) {

        //Create the uniformLocation
        int uniformLocation = glGetUniformLocation(programID, uniformName);

        //Send the uniform data
        glUniform1f(uniformLocation, f);
    }

    //This will create and return a model matrix for the MVP matrix
    protected Matrix4f createModelMatrix(Entity entity) {

        //Send the model
        Matrix4f matrix = new Matrix4f();
        Matrix4f scale = entity.getScaleMatrix();
        Matrix4f translation = entity.getTranslationMatrix();
        Matrix4f rotation = entity.getRotationMatrix();

        //Assemble the model matrix
        matrix.mul(translation).mul(rotation).mul(scale);
        return matrix;
    }

    //Send uniform data
    protected void sendUniforms(Entity entity, Matrix4f view, Matrix4f projection) {

        //Create the model matrix
        Matrix4f model = createModelMatrix(entity);

        //Send the matrices
        sendMatrices(projection, "projection");
        sendMatrices(view, "view");
        sendMatrices(model, "model");

        //Send the models material
        Material material = entity.getModel().getMaterial();
        sendFloat(material.getDampening(), "dampening");
        sendFloat(material.getReflectivity(), "reflectivity");
    }

    //This will render a model based on its position and the MVP matrix
    public void renderEntity(Entity entity, Matrix4f view, Matrix4f projection) {

        //Send the uniform variables to the shader
        sendUniforms(entity, view, projection);

        //Bind the vao and vbo from the model
        renderModel(entity.getModel());
    }
}
