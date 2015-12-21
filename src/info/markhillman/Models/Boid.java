package info.markhillman.Models;

import info.markhillman.Utils.EulerAngle;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
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
    private float maxSpeed = 0.1f;
    private Matrix4f rotation;

    public Boid(Vector3f position) {
        super(position);
        setVelocity(new Vector3f(
                (float)(Math.random() - 0.5),
                (float)(Math.random() - 0.5),
                (float)(Math.random() - 0.5)
        ));
        setAcceleration(new Vector3f(0));

        //Get any previous boids models for flyweight
        if (boids.size() > 0)
            setModel(boids.get(0).getModel());
        else {
            ModelLoader loader = new ModelLoader();
            setModel(loader.loadOBJModel("models/cube.obj"));
        }

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
                alignment.add(boid.getVelocity());
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
                limitVector(getAcceleration(), new Vector3f(maxSpeed));
            }
        }
        getAcceleration().add(alignment);
        getAcceleration().add(cohesion);
        getAcceleration().add(separation);
        getAcceleration().normalize();

        //Make the acceleration smaller
        getAcceleration().div(300);
    }

    //Update the boids velocity
    public void move() {
        getVelocity().add(getAcceleration());
        limitVector(getVelocity(), new Vector3f(maxSpeed));
        getPosition().add(getVelocity());
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
        Boid boid = new Boid(getPosition());
        return boid;
    }

    @Override
    public Matrix4f getRotationMatrix() {

        return calculateRotationMatrix();
    }

    public Matrix4f calculateRotationMatrix() {

        Matrix4f matrix4f = new Matrix4f();
        Quaternionf quaternionf = new Quaternionf();

        quaternionf.lookRotate(getVelocity(), new Vector3f(0, 1, 0));
        quaternionf.get(matrix4f);

        return matrix4f;
    }
}
