package info.markhillman.Scene;

import org.joml.Vector3f;

/**
 * Class: Light 
 * Description: This class will be used to provide a light in the scene at a given
 * point, the light can have a position and a colour.
 * Created by Mark
 * on 06/12/2015.
 */
public class Light {
    
    private Vector3f position;
    private Vector3f color;
    
    public Light() {
        this.position = new Vector3f(0, 1, 0);
        this.color = new Vector3f(1, 1, 1);
    }    
    public Light(Vector3f position) {
        this.position = position;
        this.color = new Vector3f(1, 1, 1);
    }    
    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public Vector3f getColor() {
        return color;
    }
    public void setColor(Vector3f color) {
        this.color = color;
    }   
}
