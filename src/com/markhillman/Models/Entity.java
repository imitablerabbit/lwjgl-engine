package com.markhillman.Models;

import org.joml.Vector3f;

/**
 * Class: Model
 * Description: This is the basic model class, it will
 * contain a position to render a specific model to using
 * the MVP matrix.
 * Created by Mark on 06/12/2015.
 */
public class Entity {

    private Vector3f position;
    private Model model;

    public Entity() {
        this(new Model(), new Vector3f(0, 0, 0));
    }
    public Entity(Vector3f position) {
        this.model = new Model();
        this.position = position;
    }
    public Entity(Model model, Vector3f position) {
        this.model = model;
        this.position = position;
    }

    //Getters and Setters
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
