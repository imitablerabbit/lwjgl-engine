package info.markhillman;

import info.markhillman.Models.Boid;
import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Scene;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {
        
        Engine engine = new Engine();
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();

        //Make the camera look at the falcon
        camera.setPosition(new Vector3f(0, 0, 0));

        //Create the boids and add to scene
        for (int i = 0; i < 500; i++) {

            Boid b = new Boid();
            b.setPosition(new Vector3f(
                    (float)(Math.random() * 80 - 40),
                    (float)(Math.random() * 80 - 40),
                    (float)(Math.random() * 80 - 40)
            ));
            scene.addEntity(b);
        }


        engine.run();
    }
}
