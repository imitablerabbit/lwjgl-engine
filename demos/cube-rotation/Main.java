package info.markhillman;

import info.markhillman.Loaders.EntityLoader;
import info.markhillman.Models.Entity;
import info.markhillman.Models.TexturedEntity;
import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Scene;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {

        //Create a basic cube class
        class Cube extends TexturedEntity {
            Cube() {
                EntityLoader loader = new EntityLoader();
                TexturedEntity e = loader.loadTexturedEntity("models/cube.obj", "textures/cube.bmp");
                clone(e);
            }
            @Override
            public void run() {
                //Rotate the model 90 degrees every second
                getRotationAngles().setYaw(getRotationAngles().getYaw() + (float)(90 * Engine.timer.getDT()));
                getRotationAngles().setPitch(getRotationAngles().getPitch() + (float)(90 * Engine.timer.getDT()));
            }
        }

        Engine engine = new Engine();
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();

        //Create the Falcon and add to the scene
        Cube cube = new Cube();
        cube.setPosition(new Vector3f(1, -3, -2));
        scene.addEntity(cube);

        //Make the camera look at the falcon
        camera.setPosition(new Vector3f(0, 0, 1));
        camera.lookAt(cube.getPosition());

        engine.run();
    }
}
