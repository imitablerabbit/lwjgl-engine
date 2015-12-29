package info.markhillman.Models;

/**
 * Class: Material
 * Description: This class will hold the reeflectivity
 * and dampening for the Model, so as to be able to
 * calculate lighting effects
 * Created by Mark on 20/12/2015.
 */
public class Material {
    float dampening;
    float reflectivity;

    public Material() {
        this(0.4f, 5);
    }
    public Material(float reflectivity, float dampening) {
        this.dampening = dampening;
        this.reflectivity = reflectivity;
    }

    //Getters and Setters
    public float getDampening() {
        return dampening;
    }
    public void setDampening(float dampening) {
        this.dampening = dampening;
    }
    public float getReflectivity() {
        return reflectivity;
    }
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
