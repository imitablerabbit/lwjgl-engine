package info.markhillman.Utils;

import org.joml.Vector3f;

/**
 * Class: EulerAngle
 * Description: This class will create an object to hold some Euler
 * angles, these angles can be used to look at an object after
 * moving the mouse
 * Created by Mark on 06/12/2015.
 */
public class EulerAngle {

    private float pitch;
    private float yaw;
    private float roll;

    public EulerAngle() {
        this(0, 0, 0);
    }
    public EulerAngle(float pitch, float yaw) {
        this(pitch, yaw, 0);
    }
    public EulerAngle(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    //This will convert the EulerAngle into a vector to use with a lookat function
    public Vector3f toVector() {

        Vector3f result = new Vector3f();

        //Convert the values to radians
        float yawTemp = (float)Math.toRadians(yaw);
        float pitchTemp = (float)Math.toRadians(pitch);

        //This will do the calculation to a vector
        result.x = (float)(Math.sin(yawTemp) * Math.cos(pitchTemp));
        result.y = (float)(Math.sin(pitchTemp));
        result.z = (float)(Math.cos(pitchTemp) * -Math.cos(yawTemp));
        return result;
    }

    //Converts a vector back into its euler angles
    public void toAngles(Vector3f direction) {

        float pitchTemp = 0;
        float yawTemp = 0;

        //Normalize the vector
        Vector3f dir = new Vector3f(direction);
        //dir.normalize();

        //Calculate the yaw and the pitch from the direction vector
        yawTemp = (float)Math.atan2(dir.x, dir.z);
        pitchTemp = (float)Math.atan2(dir.y, Math.sqrt(dir.x * dir.x + dir.z * dir.z));

        //Convert back into degrees
        pitchTemp = (float)((pitchTemp * 180) / Math.PI);
        yawTemp = (float)((yawTemp * 180) / Math.PI);

        //Clockwise
        this.pitch = -pitchTemp;
        this.yaw = -yawTemp;
    }

    //A constrain function
    public void constrain() {

        //Constrain the values
        if (pitch >= 90)
            pitch = 90;
        else if (pitch <= -90)
            pitch = -90;

        if (yaw > 360)
            yaw -= 360;
        else if (yaw < -360)
            yaw += 360;
    }

    //Getters and Setters
    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public float getRoll() {
        return roll;
    }
    public void setRoll(float roll) {
        this.roll = roll;
    }
    public void setAngles(float pitch, float yaw) {
        setAngles(pitch, yaw, 0);
    }
    public void setAngles(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }
}