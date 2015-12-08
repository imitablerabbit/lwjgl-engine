package com.markhillman.Renderer;

import com.markhillman.Models.Entity;
import com.markhillman.Models.Model;
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
 * Description: This class will render any model and entity
 * Created by Mark on 04/12/2015.
 */
public class Renderer {

    private int programID;

    public Renderer(int programID) {
        this.programID = programID;
    }

    //This will bind and buffer all the data from the model
    public void renderMesh(Model model) {

        //Bind the vao and vbo
        glBindVertexArray(model.getVaoID());
        glBindBuffer(GL_ARRAY_BUFFER, model.getVerticesID());
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        //Draw the shape
        glDrawArrays(GL_TRIANGLES, 0, model.getVerticesSize());
        glDisableVertexAttribArray(0);

        //Unbind the vao and vbo
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    //This will render a model based on its position and the MVP matrix
    public void renderEntity(Entity entity, Matrix4f view, Matrix4f projection) {


        //This will fetch the values from the perspective and put them
        //into a buffer array due to the Matrix4f not putting correctly
        //into a float buffer
        //Send the projection
        float[] array = new float[16];
        int projectionUni = glGetUniformLocation(programID, "projection");
        FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
        projection.get(array);
        projectionBuffer.put(array).flip();
        glUniformMatrix4fv(projectionUni, false, projectionBuffer);

        //Send the view
        int viewUni = glGetUniformLocation(programID, "view");
        FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16);
        view.get(array);
        viewBuffer.put(array).flip();
        glUniformMatrix4fv(viewUni, false, viewBuffer);

        //Send the model
        int modelUni = glGetUniformLocation(programID, "model");
        FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(entity.getPosition());
        modelMatrix.get(array);
        modelBuffer.put(array).flip();
        glUniformMatrix4fv(modelUni, false, modelBuffer);

        //Bind the vao and vbo from the model
        Model model =  entity.getModel();
        renderMesh(model);
    }
}
