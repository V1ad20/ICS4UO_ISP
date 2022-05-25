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

    public String toString(){
        return type + ", " + sideLength;
    }
    
}
