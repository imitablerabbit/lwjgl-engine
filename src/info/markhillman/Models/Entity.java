package info.markhillman.Models;

import info.markhillman.Utils.EulerAngle;
import org.joml.Vector3f;
import org.joml.Matrix4f;

/**
 * Class: Model
 * Description: This is the basic model class, it will
 * contain a position to render a specific model to using
 * the MVP matrix.
 * Created by Mark on 06/12/2015.
 */
public class Entity {

    private Vector3f position;
    private Vector3f scale;
    private EulerAngle angle;
	private Vector3f velocity;
    private Vector3f acceleration;
    private Model model;

    public Entity() {
        this(new Model(), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }
    public Entity(Entity e) {
        this.position = e.getPosition();
        this.scale = e.getScale();
        this.angle = e.getRotationAngles();
        this.velocity = e.getVelocity();
        this.acceleration = e.getAcceleration();
        this.model = e.getModel();
    }
    public Entity(Model model) {
        this(model, new Vector3f());
    }
    public Entity(Vector3f position) {
        this(new Model(), position, new Vector3f(1, 1, 1));
    }
    public Entity(Model model, Vector3f position) {
        this(model, position, new Vector3f(1, 1, 1));
    }
    public Entity(Vector3f position, Vector3f scale) {
        this(new Model(), position, scale);
    }
    public Entity(Vector3f position, Vector3f scale, EulerAngle angle) {
        this(new Model(), position, scale, angle);
    }
    public Entity(Model model, Vector3f position, Vector3f scale) {
        this(model, position, scale, new EulerAngle(0, 0, 0));
    }
    public Entity(Model model, Vector3f position, Vector3f scale, EulerAngle angle) {
        this.model = model;
        this.position = position;
        this.scale = scale;
        this.angle = angle;
    }

    //Clone the entity making sure that the model is the same object
    public Entity clone() {
        return new Entity(model, position, scale, angle);
    }

    //Move the entity
    public void move() {
        position.add(velocity);
    }
	
	//Run the entity
    public void run() {
		System.out.println("Entity has been run");
    }
	
	//Print the entity as a string
    public String toString() {
        return "Position: " + position.toString();
    }
	
    //Getters and Setters
    public Matrix4f getRotationMatrix(){
		return new Matrix4f().rotateX(angle.getPitch()).rotateY(angle.getYaw());
	}	
    public Matrix4f getScaleMatrix(){
		return new Matrix4f().scale(getScale());
	}
    public Matrix4f getTranslationMatrix(){
		return new Matrix4f().translate(getPosition());
	}
	public EulerAngle getRotationAngles() {
        return angle;
    }
    public void setRotationAngles(EulerAngle rotation) {
        this.angle = rotation;
    }
    public Vector3f getScale() {
        return scale;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
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
	public Vector3f getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	public Vector3f getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}
}
