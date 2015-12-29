package info.markhillman;

import info.markhillman.Loaders.EntityLoader;
import info.markhillman.Models.TexturedEntity;
import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Scene;
import info.markhillman.Utils.EulerAngle;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {

        Engine engine = new Engine();
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();

        //Create the Falcon and add to the scene
        EntityLoader loader = new EntityLoader();
        TexturedEntity cube = loader.loadTexturedEntity("res/models/cube.obj", "res/textures/cube.bmp");
        cube.setPosition(new Vector3f(1, -3, -2));
        scene.addEntity(cube);
        cube.setAction(()->{
            EulerAngle angles = cube.getRotationAngles();
            angles.setYaw(angles.getYaw() + (float)(90 * Engine.timer.getDT()));
            angles.setPitch(angles.getPitch() + (float)(90 * Engine.timer.getDT()));
        });

        //Make the camera look at the falcon
        camera.setPosition(new Vector3f(0, 0, 1));
        camera.lookAt(cube.getPosition());

        engine.run();
    }
}
