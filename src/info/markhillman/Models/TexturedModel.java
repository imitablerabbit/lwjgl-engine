package info.markhillman.Models;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Class: TexturedModel
 * Description: This class is a child class of the
 * Model class, it will hold and create a texture to
 * render onto the model
 * Created by Mark on 22/12/2015.
 */
public class TexturedModel extends Model {

    private int textureID;

    public TexturedModel(Model model, String texturePath) {
        super(model.getVertices(), model.getNormals(), model.getUvs(), model.getMaterial());
        textureID = generateTextureID(texturePath);
    }

    //This will load a texture into a textureID
    private int generateTextureID(String texturePath) {

        byte[] rgb = new byte[0];
        int width = 0, height = 0;

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

        //Create the texture ID
        int id = glGenTextures();

        //Bind the texture as a 2D texture
        glBindTexture(GL_TEXTURE_2D, id);

        //Add the pixels to a byte buffer
        ByteBuffer buffer = BufferUtils.createByteBuffer(rgb.length);
        buffer.put(rgb);
        buffer.flip();

        //Send the image data to openGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);

        //Trilinear filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

        //Generate the mips
        glGenerateMipmap(GL_TEXTURE_2D);

        return id;
    }

    public int getTextureID() {
        return textureID;
    }
    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}
