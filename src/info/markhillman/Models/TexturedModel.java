package info.markhillman.Models;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
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
    private Texture texture;

    public TexturedModel(Model model, String texturePath) {
        super(model.getVertices(), model.getNormals(), model.getUvs(), model.getMaterial());
        createTexture(texturePath);
    }

    //This will load a texture into a textureID
    private void createTexture(String texturePath) {

        //Create the texture, ID and bind it
        texture = new Texture(texturePath);
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        //Add the pixels to a byte buffer
        ByteBuffer buffer = BufferUtils.createByteBuffer(texture.getRGB().length);
        buffer.put(texture.getRGB());
        buffer.flip();

        //Send the image data to openGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texture.getWidth(), texture.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);

        //Trilinear filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

        //Generate the mips
        glGenerateMipmap(GL_TEXTURE_2D);

        textureID = id;
    }

    public int getTextureID() {
        return textureID;
    }
    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
