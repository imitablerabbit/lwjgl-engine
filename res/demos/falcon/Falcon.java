package info.markhillman;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import info.markhillman.Loaders.EntityLoader;
import info.markhillman.Models.TexturedEntity;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Class: Falcon
 * Description: This is a demo class which will
 * fly a model of the millenium falcon around the
 * screen towards randomly generated points
 * Created by Mark on 27/12/2015.
 */
public class Falcon extends TexturedEntity {

    private Vector3f destination;
    private Matrix4f orientation;

    public Falcon() {

        //Create a default entity and set this entity to it
        EntityLoader loader = new EntityLoader();
        TexturedEntity e = loader.loadTexturedEntity("millenium_falcon.obj", "millenium_falcon.bmp");
        this.angle = e.getRotationAngles();
        this.model = e.getModel();
        this.velocity = new Vector3f(1, 0, 1);
        this.acceleration = new Vector3f();
        this.position = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(0.005f);
        randDestination();
        orientation = new Matrix4f();
    }

    @Override
    public void move() {

        limitVector(velocity, new Vector3f(3));
        Vector3f distance = new Vector3f(velocity);
        distance.mul((float)Engine.timer.getDT());
        this.position.add(distance);
    }

    @Override
    public void run() {

        //Calculate the acceleration to steer the velocity of the ship
        steerVelocity();

        //Check if the ship comes close to the destination, if it does then
        //Generate a new destination
        if (destination.sub(position, new Vector3f()).length() < 5) {
            randDestination();
        }

        //Move the ship towards its destination
        //move();
    }

    @Override
    public Matrix4f getRotationMatrix(){

        return orientation;
    }

    //This will steer the ship to the destination
    private void steerVelocity() {

        acceleration = destination.sub(position, new Vector3f());
        acceleration.normalize();
        acceleration.mul((float)Engine.timer.getDT() * 4);
        velocity.add(acceleration);
    }

    //generate a new random destination
    private void randDestination() {
        destination = new Vector3f(
                (float)Math.random() * 40 - 20,
                0,
                (float)Math.random() * 40 - 20
        );
    }

    //Limit a vector max an min
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
}
