package info.markhillman;

import info.markhillman.Loaders.ModelLoader;
import info.markhillman.Models.Boid;
import info.markhillman.Models.Entity;
import info.markhillman.Models.TexturedEntity;
import info.markhillman.Models.TexturedModel;
import info.markhillman.Scene.Scene;
import org.joml.Vector3f;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine();
        Scene scene = engine.getScene();

        //Create the cube and add to the scene
        ModelLoader modelLoader = new ModelLoader();
        Entity cube = new Entity(modelLoader.loadOBJModel("models/cube.obj"), new Vector3f(0, 0, -5));
        TexturedEntity cubeTextured = new TexturedEntity(cube, new TexturedModel(cube.getModel(), "textures/cube.bmp"));
        scene.addEntity(cubeTextured);

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
