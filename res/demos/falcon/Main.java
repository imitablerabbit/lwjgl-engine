package info.markhillman;

import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Scene;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {

        Engine engine = new Engine();
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();

        //Create the Falcon and add to the scene
        Falcon falcon = new Falcon();
        falcon.setPosition(new Vector3f(1, -3, -2));
        scene.addEntity(falcon);

        //Make the camera look at the falcon
        camera.setPosition(new Vector3f(0, 0, 1));
        camera.lookAt(falcon.getPosition());

        engine.run();
    }
}
