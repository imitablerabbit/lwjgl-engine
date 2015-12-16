package info.markhillman.Renderer;

import info.markhillman.Models.Entity;
import info.markhillman.Models.Model;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

/**
 * Class: InstancedRenderer
 * Description: This will class will render a map of
 * different entities to the gl context in a somewhat
 * efficient manner. It will not resend the model information
 * each time, only the entity information will be sent.
 * This is so that the entity can use the same vertices
 * but be rendered in a different place.
 * Created by Mark on 14/12/2015.
 */
public class InstancedRenderer extends Renderer{

    public InstancedRenderer(int programID) {
        super(programID);
    }

    //This will assemble the map of entities
    public Map<Model, List<Entity>> assembleMap(List<Entity> entities) {

        Map<Model, List<Entity>> map = new HashMap<>();
        for (Entity entity: entities) {

            //Add the entity to the list if that model already exists
            if (map.containsKey(entity.getModel())) {
                map.get(entity.getModel()).add(entity);
            }
            else {

                //Create a new list for the map
                ArrayList<Entity> list = new ArrayList<>(0);
                list.add(entity);
                map.put(entity.getModel(), list);
            }
        }
        //System.out.println(map);
        return map;
    }
    public Map<Model, List<Entity>> assembleMap(Entity[] entities) {

        //Create a list from the array
        List<Entity> list = new ArrayList<>(entities.length);
        for (Entity entity : entities) {
            list.add(entity);
        }
        return assembleMap(list);
    }

    //This will render the entities onto the screen
    public void renderEntityMap(Map<Model, List<Entity>> map, Matrix4f view, Matrix4f projection) {

        //Get each of the different models
        for (Map.Entry<Model, List<Entity>> entry : map.entrySet()) {
            bindModel(entry.getKey());
            for (Entity entity : entry.getValue()) {

                //Create the model matrix
                Matrix4f model = createModelMatrix(entity);

                //Send the matrices as uniforms
                int projectionUni = glGetUniformLocation(programID, "projection");
                int viewUni = glGetUniformLocation(programID, "view");
                int modelUni = glGetUniformLocation(programID, "model");
                sendMatrices(projection, projectionUni);
                sendMatrices(view, viewUni);
                sendMatrices(model, modelUni);

                //Make a call to the render function
                glDrawArrays(GL_TRIANGLES, 0, entry.getKey().getVerticesSize());
            }
            unbindModel();
        }
    }
}
