package info.markhillman.Loaders;

import info.markhillman.Models.*;
import info.markhillman.Utils.EulerAngle;
import org.joml.Vector3f;

/**
 * Class: EntityLoader
 * Description: This class will create and load different
 * types of Entities. One function will also return a
 * TexturedEntity
 * Created by Mark on 26/12/2015.
 */
public class EntityLoader {

    private ModelLoader modelLoader;

    public EntityLoader() {
        modelLoader = new ModelLoader();
    }

    //This function will load a models obj path and return a newly created Entity
    public Entity loadEntity(String modelPath) {
        return loadEntity(modelPath, new Vector3f(0,0,0), new Vector3f(1));
    }
    public Entity loadEntity(String modelPath, Vector3f position) {
        return loadEntity(modelPath, position, new Vector3f(1));
    }
    public Entity loadEntity(String modelPath, Vector3f position, Vector3f scale) {

        Model model = modelLoader.loadOBJModel(modelPath);
        Entity e = new Entity(model, position, scale);
        return e;
    }

    //This function will load an Entity and then bind a texture to it
    public TexturedEntity loadTexturedEntity(String modelPath, String texturePath) {
        return loadTexturedEntity(modelPath, texturePath, new Vector3f(0,0,0), new Vector3f(1));
    }
    public TexturedEntity loadTexturedEntity(String modelPath, String texturePath, Vector3f position) {
        return loadTexturedEntity(modelPath, texturePath, position, new Vector3f(1));
    }
    public TexturedEntity loadTexturedEntity(String modelPath, String texturePath, Vector3f position, Vector3f scale) {

        Entity e = loadEntity(modelPath, position, scale);
        TexturedEntity te = new TexturedEntity(e, texturePath);
        return te;
    }
}
