package info.markhillman.Models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class: Texture
 * Description: This class will load and store
 * the bytes in a texture as well as the width and
 * height of the texture.
 * Created by Mark on 22/12/2015.
 */
public class Texture {

    private byte[] rgb;
    private int width;
    private int height;
    private String texturePath;

    public Texture(String texturePath) {
        this.texturePath = texturePath;
        loadTexture();
    }

    //Load the texture image into the texture object
    private void loadTexture() {
        //Load the texture
        try {
            BufferedImage io = ImageIO.read(new File(texturePath));
            width = io.getWidth();
            height = io.getHeight();

            int[] pixels = new int[width * height * 3];
            pixels = io.getData().getPixels(0, 0, width, height, pixels);

            rgb = new byte[pixels.length];
            for (int i = 0; i < pixels.length; i++) {
                rgb[i] = (byte)pixels[i];
            }
        } catch (IOException e) {
            System.out.println("Could not load the texture");
            e.printStackTrace();
        }
    }

    //Getters
    public byte[] getRGB() {
        return rgb;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getTexturePath() {
        return texturePath;
    }
}
