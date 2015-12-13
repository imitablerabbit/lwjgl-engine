package info.markhillman.Models;

import org.joml.Vector3f;

/**
 * Class: Boid
 * Description: This will move the entity according to other
 * boids in the vecinity of this one.
 * Created by Mark on 13/12/2015.
 */
public class Boid extends Entity {

    public Boid(Vector3f position) {
        super(position, new Vector3f(0.2f, 0.6f, 1f), new Vector3f(0, 0, 0));
    }
}
