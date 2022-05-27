/**
 * PicturePixel class to define pixels for the array in the main class
 * 
 * @author Sean Yang, Vlad Surdu, Ana-Maria
 * @version 1.0
 * @since 2022-05-25
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PicturePixel {
    public ImageView imageView;
    public int type;
    public double sideLength;

    PicturePixel(Image img, int t, double sL){
        imageView = new ImageView(img);
        type = t;
        sideLength = sL;
    }

    
    /** This method returns the type of the pixel and the side length of the pixrel
     * @return String contains information about pixel
     */
    public String toString(){
        return type + ", " + sideLength;
    }
    
}
