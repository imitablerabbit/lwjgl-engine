package info.markhillman;

import info.markhillman.Exceptions.SingletonException;
import info.markhillman.Loaders.EntityLoader;
import info.markhillman.Models.Entity;
import info.markhillman.Models.TexturedEntity;
import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Light;
import info.markhillman.Scene.Scene;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {

        // Create the engine and get the objects
        Engine engine = new Engine(700, 500, "Cube Rotation");
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();
        Light light = new Light(new Vector3f(0, 1, 1), new Vector3f(1, 1, 1));        
        scene.setLight(light);

        //Create the Falcon and add to the scene
        EntityLoader loader = new EntityLoader();
        TexturedEntity falcon = loader.loadTexturedEntity("res/models/millenium_falcon.obj", "res/textures/millenium_falcon.bmp");
        falcon.setPosition(new Vector3f(0, 0, 0));
        falcon.setScale(new Vector3f(0.005f));
        scene.addEntity(falcon);
        
        Vector3f lookAt = new Vector3f(-3, 0, -3);
        Entity sphere = loader.loadEntity("res/models/sphere.obj", lookAt);
        sphere.setScale(new Vector3f(0.2f));
        scene.addEntity(sphere);

        //Should change the direction value each time
        Vector3f forward = new Vector3f(0, 0, 1);
        Quaternionf rotation = new Quaternionf();

        falcon.setAction(()-> {
            Vector3f direction = lookAt.sub(falcon.getPosition(), new Vector3f()).normalize();
            Quaternionf q = new Quaternionf().rotationTo(forward, direction);

            rotation.nlerp(q, (float)Engine.timer.getDT() / 5); // Rotation over 5 seconds
            //cube.getRotation().rotateX((float)(Math.toRadians(-45) * (Engine.timer.getDT() / 5))); // Handle X axis rotation seperately
            
            falcon.setRotation(rotation.get(new Matrix4f()));
            falcon.getPosition().add(new Vector3f(1, 0, 0).mul((float)Engine.timer.getDT() / 5)); // Move right 1 unit every 5 seconds
        });

        //Make the camera look at the falcon
        camera.setPosition(new Vector3f(0, 1, 3));
        camera.lookAt(falcon.getPosition());

        engine.run();
    }
}
