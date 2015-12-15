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
    private Vector3f direction;
    private Vector3f velocity;
    private Vector3f acceleration;
    private float maxForce = 0.0001f;
    private float maxSpeed = 0.2f;

    public Boid(Vector3f position) {
        super(position, new Vector3f(0.2f, 0.6f, 1f), new Vector3f(0));
        direction = new Vector3f();
        velocity = new Vector3f((float)((Math.random() * 2) - 1) * maxSpeed,
                (float)((Math.random() * 2) - 1) * maxSpeed,
                (float)((Math.random() * 2) - 1) * maxSpeed);
        acceleration = new Vector3f(0);
        boids.add(this);
    }
    public Boid() {
        this(new Vector3f(0, 0, 0));
    }

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

    //Flock the boids
    private void flock() {

        //Create the separation, cohesion and align
        Vector3f sep = separation();
        Vector3f coh = cohesion();
        Vector3f ali = align();

        //Weight the vectors
        sep.mul(1);
        coh.mul(1.5f);
        ali.mul(1f);

        //Add the vectors to the acceleration
        // TODO: 15/12/2015 Make sure that the vetors being added are not always positive 
        acceleration.add(sep);
        acceleration.add(coh);
        acceleration.add(ali);
    }

    //Update the boid
    private void update() {
        velocity.add(acceleration);
        limitVector(velocity, new Vector3f(maxSpeed));
        getPosition().add(velocity);
        acceleration.mul(0);
    }

    //Adjust the acceleration in line with the target
    private void seek(Vector3f target) {
        acceleration.add(steer(target, false));
    }

    //Adjust the acceleration when the target is reached
    private void arrive(Vector3f target) {
        acceleration.add(steer(target, true));
    }

    //Steer the boid
    private Vector3f steer(Vector3f target, boolean slowdown) {
        Vector3f steer;
        Vector3f desired = target.sub(getPosition(), new Vector3f());
        float d = desired.length();
        if (d > 0) {
            // Normalize desired
            desired.normalize();
            if ((slowdown) && (d < 100.0))
                desired.mul((float)(maxSpeed * (d/100.0)));
            else
                desired.mul(maxSpeed);

            // Steering = Desired minus Velocity
            steer = desired.sub(velocity, new Vector3f());
            steer.max(new Vector3f(maxForce));  			// Limit to maximum steering force
        }
        else {
            steer = new Vector3f(0);
        }
        return steer;
    }

    //Keep a desired separation
    private Vector3f separation() {
        float desiredseparation = 1f;
        Vector3f steer = new Vector3f(0,0,0);
        int count = 0;
        for (int i = 0 ; i < boids.size(); i++) {
            Boid other = boids.get(i);
            float d = getPosition().distance(other.getPosition());
            if ((d > 0) && (d < desiredseparation)) {
                Vector3f diff = getPosition().sub(other.getPosition(), new Vector3f());
                diff.normalize();
                diff.div(d);
                steer.add(diff);
                count++;
            }
        }
        if (count > 0) {
            steer.div((float)count);
        }
        if (steer.length() > 0) {
            steer.normalize();
            steer.mul(maxSpeed);
            steer.sub(velocity);
            limitVector(steer, new Vector3f(maxForce));
        }
        return steer;
    }
    private Vector3f cohesion() {
        float neighbordist = 5f;
        Vector3f sum = new Vector3f(0);
        int count = 0;
        for (int i = 0 ; i < boids.size(); i++) {
            Boid other = (Boid) boids.get(i);
            float d = getPosition().distance(other.getPosition());
            if ((d > 0) && (d < neighbordist)) {
                sum.add(other.getPosition());
                count++;
            }
        }
        if (count > 0) {
            sum.div((float)count);
            return steer(sum,false);  // Steer towards the location
        }
        return sum;
    }
    private Vector3f align() {
        float neighbordist = 2f;	// defined neighbourhood
        Vector3f steer = new Vector3f(0);
        int count = 0;
        for (int i = 0 ; i < boids.size(); i++) {
            Boid other = (Boid) boids.get(i);
            float d = getPosition().distance(other.getPosition());
            if ((d > 0) && (d < neighbordist)) {
                steer.add(other.velocity);
                count++;
            } }
        if (count > 0) { steer.div((float)count);}
        if (steer.length() > 0) { // Implement Reynolds: Steering = Desired - Velocity
            steer.normalize();
            steer.mul(new Vector3f(maxSpeed));
            steer.sub(velocity);
            limitVector(steer, new Vector3f(maxForce));
        }
        return steer;
    }

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

    //This will allow the boids to flock and update their positions
    @Override
    public void run() {
        flock();
        update();
        borders();
    }

    @Override
    public String toString() {
        return "Velocity: " + velocity;
    }

    //Make sure that the model is always the same for a clone
    public Boid clone() {
        Boid boid = new Boid(this.getPosition());
        boid.setModel(this.getModel());
        return boid;
    }
}
