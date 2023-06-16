package pt.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
/**
 * class for manage the images
 *
 * @author Daniela Correia
 * @version 1.0.0
 */
public class ImageManager {
    private ImageManager() { }
    private static final HashMap<String, Image> images = new HashMap<>();
    /**
     * Convert the image path to Image
     * @param filename image path
     */
    public static Image getImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try (InputStream is = ImageManager.class.getResourceAsStream("images/"+filename)) {
                image = new Image(is);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }
}