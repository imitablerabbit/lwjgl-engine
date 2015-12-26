package info.markhillman;

import info.markhillman.Loaders.EntityLoader;
import info.markhillman.Loaders.ModelLoader;
import info.markhillman.Models.Boid;
import info.markhillman.Models.Entity;
import info.markhillman.Models.TexturedEntity;
import info.markhillman.Models.TexturedModel;
import info.markhillman.Scene.Camera;
import info.markhillman.Scene.Scene;
import info.markhillman.Utils.EulerAngle;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine();
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();

        //Create the cube and add to the scene
        EntityLoader loader = new EntityLoader();
        TexturedEntity milleniumFalcon = loader.loadTexturedEntity(
                "models/millenium_falcon.obj",
                "textures/millenium_falcon.bmp",
                new Vector3f(0, -2, -3),
                new Vector3f(0.005f)
        );
        milleniumFalcon.getModel().getMaterial().setReflectivity(0.2f);
        scene.addEntity(milleniumFalcon);

        //Make the camera look at the falcon
        camera.lookAt(milleniumFalcon.getPosition());

        //Create the boids and add to scene
        /*
        for (int i = 0; i < 500; i++) {

            Boid b = new Boid();
            b.setPosition(new Vector3f(
                    (float)(Math.random() * 80 - 40),
                    (float)(Math.random() * 80 - 40),
                    (float)(Math.random() * 80 - 40)
            ));
            scene.addEntity(b);
        }
        */

        engine.run();
    }
}
