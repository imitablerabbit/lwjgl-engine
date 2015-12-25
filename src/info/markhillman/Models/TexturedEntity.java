package info.markhillman.Models;

import javax.xml.soap.Text;

/**
 * Class: TexturedEntity
 * Description: This class will create a texturedEntity
 * from an existing entity and a texturePath or with a
 * textured model. A textured entity is basically the same
 * as a normal entity but it is guaranteed to have a texture
 * attached for the render to apply.
 * Created by Mark on 22/12/2015.
 */
public class TexturedEntity extends Entity {

    private TexturedModel model;

    public TexturedEntity(Entity entity, TexturedModel model) {
        super(entity);
        this.model = model;
    }
    public TexturedEntity(Entity entity, String texturePath) {
        super(entity);
        this.model = new TexturedModel(entity.getModel(), texturePath);
    }

    public TexturedModel getModel() {
        return model;
    }
}

