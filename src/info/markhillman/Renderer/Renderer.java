package info.markhillman.Renderer;

import info.markhillman.Models.Entity;
import info.markhillman.Models.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Class: Renderer
 * Description: This class will render any model and entity
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
        glBindBuffer(GL_ARRAY_BUFFER, model.getVerticesID());
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, model.getNormalsID());
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);
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
    protected void sendMatrices(Matrix4f matrix, int uniformLocation) {

        //Create the buffer and buffer the data to it
        float[] array = new float[16];
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(array);
        buffer.put(array).flip();
        glUniformMatrix4fv(uniformLocation, false, buffer);
    }

    //This will create and return a model matrix for the MVP matrix
    protected Matrix4f createModelMatrix(Entity entity) {

        //Send the model
        Matrix4f matrix = new Matrix4f();
        Matrix4f scale = matrix.scale(entity.getScale(), new Matrix4f());
        Matrix4f translation = matrix.translate(entity.getPosition(), new Matrix4f());

        //Rotation
        Matrix4f rotation = entity.getRotationalMatrix();

        //Assemble the model matrix
        matrix.mul(translation).mul(rotation).mul(scale);
        return matrix;
    }

    //This will render a model based on its position and the MVP matrix
    public void renderEntity(Entity entity, Matrix4f view, Matrix4f projection) {

        //Create the model matrix
        Matrix4f model = createModelMatrix(entity);

        //Send the matrices
        int projectionUni = glGetUniformLocation(programID, "projection");
        int viewUni = glGetUniformLocation(programID, "view");
        int modelUni = glGetUniformLocation(programID, "model");
        sendMatrices(projection, projectionUni);
        sendMatrices(view, viewUni);
        sendMatrices(model, modelUni);

        //Bind the vao and vbo from the model
        renderModel(entity.getModel());
    }
}
