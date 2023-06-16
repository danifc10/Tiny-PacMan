package pt.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.Parent;
/**
 * class for manage the style
 *
 * @author Daniela Correia
 * @version 1.0.0
 */
public class CSSManager {
    private CSSManager() { }
    /**
     *  add styles defined in the CSS file to the specified Parent element
     * @param (parent, filename) element to apply the style in the filename
     */
    public static void applyCSS(Parent parent, String filename) {
        var url = CSSManager.class.getResource("css/"+filename);
        if (url == null)
            return;
        String fileCSS = url.toExternalForm();
        parent.getStylesheets().add(fileCSS);
    }
}

