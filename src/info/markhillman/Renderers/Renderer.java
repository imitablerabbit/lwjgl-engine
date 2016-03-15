package info.markhillman.Renderers;

import info.markhillman.Models.Entity;
import info.markhillman.Models.Material;
import info.markhillman.Models.Model;
import info.markhillman.Scene.Scene;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import org.joml.Vector3f;

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
    
    // This will send a 3 float matrix to the shaders as a unform variable
    protected void sendVector(Vector3f vector, String uniformName) {
        
        //Create the uniformLocation
        int uniformLocation = glGetUniformLocation(programID, uniformName);

        //Create the buffer and buffer the data to it
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        vector.get(buffer);
        glUniform3fv(uniformLocation, buffer);
    }

    //This will create and load a float into a uniform variable
    protected void sendFloat(float f, String uniformName) {
        int uniformLocation = glGetUniformLocation(programID, uniformName);
        glUniform1f(uniformLocation, f);
    }

    //This will create and send a boolean into a uniform variable
    protected void sendBoolean(boolean b, String uniformName) {
        int uniformLocation = glGetUniformLocation(programID, uniformName);
        glUniform1f(uniformLocation, (b == true) ? 1 : 0);
    }

    //This will create and return a model matrix for the MVP matrix
    protected Matrix4f createModelMatrix(Entity entity) {

        //Assemble the model matrix order = SRT
        Matrix4f matrix = new Matrix4f();
        matrix.mul(entity.getTranslationMatrix());
        matrix.mul(entity.getRotation());
        matrix.mul(entity.getScaleMatrix());
                
        return matrix;
    }

    //Send uniform data
    protected void sendUniforms(Entity entity, Scene scene) {

        //Create the model matrix
        Matrix4f model = createModelMatrix(entity);
        
        // Get the matrices
        Matrix4f projection = scene.getCamera().getProjection();
        Matrix4f view = scene.getCamera().getView();

        //Send the matrices
        sendMatrices(projection, "projection");
        sendMatrices(view, "view");
        sendMatrices(model, "model");
        
        // Send the light information
        sendVector(scene.getLight().getPosition(), "lightPosition");        
        sendVector(scene.getLight().getColor(), "lightColor");

        //Send the models material
        Material material = entity.getModel().getMaterial();
        sendFloat(material.getDampening(), "dampening");
        sendFloat(material.getReflectivity(), "reflectivity");

        //Send booleans for shaders
        sendBoolean(false, "isTextured");
    }

    //This will render a model based on its position and the MVP matrix
    public void renderEntity(Entity entity, Scene scene) {

        //Send the uniform variables to the shader
        sendUniforms(entity, scene);

        //Bind the vao and vbo from the model
        renderModel(entity.getModel());
    }
}
