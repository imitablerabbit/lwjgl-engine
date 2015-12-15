package info.markhillman.Models;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: Boid
 * Description: This will move the entity according to other
 * boids in the vicinity of this one.
 * Created by Mark on 13/12/2015.
 */
public class Boid extends Entity {

    //Create an array of all the boids created
    public static List<Boid> boids = new ArrayList<>(0);
    private Vector3f velocity;
    private Vector3f acceleration;
    private float maxSpeed = 0.05f;

    public Boid(Vector3f position) {
        super(position, new Vector3f(0.2f, 0.6f, 1f), new Vector3f(0));
        velocity = new Vector3f((float)(Math.random() - 0.5),
                (float)(Math.random() - 0.5),
                (float)(Math.random() - 0.5));
        acceleration = new Vector3f(0);
        boids.add(this);
    }
    public Boid() {
        this(new Vector3f(0, 0, 0));
    }

    //This will allow the boids to flock and update their positions
    @Override
    public void run() {
        flock();
        update();
        borders();
    }

    //Flock the boids
    private void flock() {

        //Create the separation, cohesion and align
        Vector3f sep = separation();
        Vector3f coh = cohesion();
        Vector3f ali = align();

        //Add the vectors to the acceleration
        acceleration.add(sep);
        acceleration.add(coh);
        acceleration.add(ali);

        for (Boid boid : boids) {
            //Align the boids
            float distance = getPosition().distance(boid.getPosition());
            if (distance < 8f && boid != this) {
                acceleration.sub(getPosition().sub(boid.getPosition(), new Vector3f()));

                //Separation
                if (distance < 6f) {
                    acceleration.add(getPosition().sub(boid.getPosition(), new Vector3f()).mul(1.5f));
                }
                acceleration.normalize();
                limitVector(acceleration, new Vector3f(maxSpeed));
            }
        }
    }

    //Update the boid
    private void update() {
        velocity.add(acceleration);
        limitVector(velocity, new Vector3f(maxSpeed));
        getPosition().add(velocity);
        acceleration.mul(0);
    }

    //Steer the boids making flocking behaviour
    private Vector3f separation() {
        return new Vector3f();
    }
    private Vector3f cohesion() {
        return new Vector3f();
    }
    private Vector3f align() {
        return new Vector3f();
    }

    //Make sure the boid doesnt leave the area
    private void borders() {
        if (getPosition().x < -50)
            getPosition().x = 50;
        else if (getPosition().x > 50)
            getPosition().x = -50;
        if (getPosition().y < -50)
            getPosition().y = 50;
        else if (getPosition().y > 50)
            getPosition().y = -50;
        if (getPosition().z < -50)
            getPosition().z = 50;
        else if (getPosition().z > 50)
            getPosition().z = -50;
    }

    //Limit the vector size
    private void limitVector(Vector3f vector, Vector3f limit) {
        if (vector.x > limit.x)
            vector.x = limit.x;
        else if (vector.x < -limit.x)
            vector.x = -limit.x;
        if (vector.y > limit.y)
            vector.y = limit.y;
        else if (vector.y < -limit.y)
            vector.y = -limit.y;
        if (vector.z > limit.z)
            vector.z = limit.z;
        else if (vector.z < -limit.z)
            vector.z = -limit.z;
    }

    //Make sure that the model is always the same for a clone
    public Boid clone() {
        Boid boid = new Boid(this.getPosition());
        boid.setModel(this.getModel());
        return boid;
    }
}
