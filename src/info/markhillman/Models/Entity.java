package info.markhillman.Models;

import org.joml.Vector3f;

import java.util.Vector;

/**
 * Class: Model
 * Description: This is the basic model class, it will
 * contain a position to render a specific model to using
 * the MVP matrix.
 * Created by Mark on 06/12/2015.
 */
public class Entity {

    private Vector3f position;
    private Vector3f scale;
    private Vector3f rotation;
    private Model model;

    public Entity() {
        this(new Model(), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }
    public Entity(Vector3f position) {
        this(new Model(), position, new Vector3f(1, 1, 1));
    }
    public Entity(Model model, Vector3f position) {
        this(model, position, new Vector3f(1, 1, 1));
    }
    public Entity(Vector3f position, Vector3f scale) {
        this(new Model(), position, scale);
    }
    public Entity(Vector3f position, Vector3f scale, Vector3f rotation) {
        this(new Model(), position, scale, rotation);
    }
    public Entity(Model model, Vector3f position, Vector3f scale) {
        this(model, position, scale, new Vector3f(0, 0, 0));
    }
    public Entity(Model model, Vector3f position, Vector3f scale, Vector3f rotation) {
        this.model = model;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    //Move the entity
    public void move(Vector3f direction) {
        position.add(direction);
    }
    public String toString() {
        return "Position: " + position.toString();
    }

    //Getters and Setters
    public Vector3f getRotation() {
        return rotation;
    }
    public void setRotation(Vector3f rotation) {
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
