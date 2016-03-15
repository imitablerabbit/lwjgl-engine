package info.markhillman.Models;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Class: Model 
 * Description: This is the basic model class, it will contain a
 * position to render a specific model to using the MVP matrix. 
 * Created by Mark
 * on 06/12/2015.
 */
public class Entity implements Cloneable {

    protected Vector3f position;
    protected Vector3f scale;
    protected Matrix4f rotation;
    protected Model model;
    protected Action action;

    public Entity() {
        this(new Model(), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }
    public Entity(Vector3f position) {
        this(new Model(), position, new Vector3f(1, 1, 1));
    }
    public Entity(Vector3f position, Vector3f scale) {
        this(new Model(), position, scale);
    }
    public Entity(Vector3f position, Vector3f scale, Matrix4f rotation) {
        this(new Model(), position, scale, rotation);
    }
    public Entity(Model model) {
        this(model, new Vector3f());
    }
    public Entity(Model model, Vector3f position) {
        this(model, position, new Vector3f(1, 1, 1));
    }
    public Entity(Model model, Vector3f position, Vector3f scale) {
        this(model, position, scale, new Matrix4f());
    }
    public Entity(Model model, Vector3f position, Vector3f scale, Matrix4f rotation) {
        this.model = model;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }
    public Entity(Entity e) {
        this.position = new Vector3f(e.getPosition());
        this.scale = new Vector3f(e.getScale());
        this.rotation = new Matrix4f(e.getRotation());
        this.model = e.getModel();
    }

    //Clone the entity making sure that the model is the same object
    @Override
    public Entity clone() throws CloneNotSupportedException { 
        
        Entity e = new Entity(this);
        return e;
    }

    //Run the entity's action interface
    public void run() {

        if (action == null) {
            //Do nothing for default
        } else {
            //Run the action
            action.run();
        }
    }

    //Create a texturedEntity from this entity
    public TexturedEntity bindTexture(String texturePath) {

        TexturedEntity te = new TexturedEntity(this, texturePath);
        return te;
    }

    //Print the entity as a string
    public String toString() {
        return "Position: " + position.toString();
    }

    //Getters and Setters
    public void setAction(Action action) {
        this.action = action;
    }
    public Matrix4f getScaleMatrix() {
        return new Matrix4f().scale(getScale());
    }
    public Matrix4f getTranslationMatrix() {
        return new Matrix4f().translate(getPosition());
    }
    public Matrix4f getRotation() {
        return rotation;
    }
    public void setRotation(Matrix4f rotation) {
        this.rotation = rotation;
    }
    public Vector3f getScale() {
        return scale;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
}
