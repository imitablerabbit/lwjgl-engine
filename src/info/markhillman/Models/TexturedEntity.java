package info.markhillman.Models;

import org.joml.Vector3f;

/**
 * Class: TexturedEntity
 * Description: This class will create a texturedEntity
 * from an existing entity and a texturePath or with a
 * textured model. A textured entity is basically the same
 * as a normal entity but it is guaranteed to have a texture
 * attached for the render to apply.
 * Created by Mark on 22/12/2015.
 */
public class TexturedEntity extends Entity {

    protected TexturedModel model;

    public TexturedEntity() {
        super(new Entity());
    }
    public TexturedEntity(Entity entity, TexturedModel model) {
        super(entity);
        this.model = model;
    }
    public TexturedEntity(Entity entity, String texturePath) {
        super(entity);
        this.model = new TexturedModel(entity.getModel(), texturePath);
    }
    public TexturedEntity(TexturedEntity e) {
        this.position = e.getPosition();
        this.scale = e.getScale();
        this.angle = e.getRotationAngles();
        this.velocity = e.getVelocity();
        this.acceleration = e.getAcceleration();
        this.model = e.getModel();
    }

    //Clone the texturedEntity
    public void clone(TexturedEntity e) {
        this.position = e.getPosition();
        this.scale = e.getScale();
        this.angle = e.getRotationAngles();
        this.velocity = e.getVelocity();
        this.acceleration = e.getAcceleration();
        this.model = e.getModel();
    }

    //Getters and setters
    public TexturedModel getModel() {
        return model;
    }
}

