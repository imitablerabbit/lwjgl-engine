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
        move();
        borders();
    }

    //Flock the boids
    private void flock() {

        Vector3f alignment = new Vector3f();
        Vector3f cohesion = new Vector3f();
        Vector3f separation = new Vector3f();

        for (Boid boid : boids) {

            float distance = getPosition().distance(boid.getPosition());
            if (distance < 8f && boid != this) {

                //Align the boids
                alignment.add(boid.velocity);
                alignment.normalize();

                //Move the boids together
                if (distance < 6f) {
                    cohesion.add(boid.getPosition().sub(getPosition(), new Vector3f()));
                    cohesion.normalize();
                }

                //Keep the boids from getting too close
                if (distance < 4f) {
                    separation.add(getPosition().sub(boid.getPosition(), new Vector3f()));
                    separation.normalize();
                }
                limitVector(acceleration, new Vector3f(maxSpeed));
            }
        }
        acceleration.add(alignment);
        acceleration.add(cohesion);
        acceleration.add(separation);
        acceleration.normalize();

        //Make the acceleration smaller
        acceleration.div(100);
    }

    //Update the boids velocity
    private void move() {
        velocity.add(acceleration);
        limitVector(velocity, new Vector3f(maxSpeed));
        getPosition().add(velocity);
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
