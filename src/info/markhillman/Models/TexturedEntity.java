package info.markhillman.Models;

import javax.xml.soap.Text;

/**
 * Class:
 * Description:
 * Created by Mark on 22/12/2015.
 */
public class TexturedEntity extends Entity {

    private TexturedModel model;

    public TexturedEntity(Entity entity, TexturedModel model) {
        super(entity);
        this.model = model;
    }

    public TexturedModel getModel() {
        return model;
    }
}

