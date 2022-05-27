/**
 * Main class for the WarLens game
 * 
 * @author Sean Yang, Vlad Surdu
 * @version 2.0
 * @since 2022-05-19
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class WarLens extends Application {
    PicturePixel[][] pictureArr;

    
    /** 
     * This method contains all of the graphics code and calls needed
     * to run the program correclty. At the moment, it contains the call
     * to load and display the array of pixels as well as setting display size,
     * the root stack and setting scene/setting name/showing scene
     * @param splashStage main stage
     */
    public void start(Stage splashStage) throws IOException{

        Group root = new Group();

        //loadArr(100,100);

        //displayArr(0,0,root);
        
        stressEffects(10, root);

        Scene scene = new Scene(root,  300, 300);
        
        splashStage.setTitle("Map Test");
        splashStage.setScene(scene);
        splashStage.show();
    }

    
    /** 
     * This method loads a 2D array of size b and h with the
     * "redPixel.png" image
     * Worked on by: Vlad, Sean
     * @param b number of columns
     * @param h number of rows
     */
    public void loadArr(int b, int h){
        pictureArr = new PicturePixel[h][b];
        for (int row = 0; row < h; row++){
            for (int col = 0; col < b; col++){
                Image img = new Image("/redPixel.png");
                pictureArr[row][col] = new PicturePixel(img,1,img.getHeight());
            }
        }
    }

    
    /** 
     * This method displays the array by adding the images to 
     * the root stack starting at the x and y coordinates
     * Worked on by: Vlad
     * @param x x coordinate of where to start adding pictures
     * @param y y coordinate of where to start adding pictures
     * @param root root stack
     */
    public void displayArr(int x, int y, Group root){
        for (int row = 0; row < pictureArr.length; row++){
            for (int col = 0; col < pictureArr[0].length; col++){
                PicturePixel p = pictureArr[row][col];
                p.imageView.setX(x+col*(p.sideLength+2));
                p.imageView.setY(y+row*(p.sideLength+2));
                root.getChildren().add(p.imageView);
            }
        }

    }
    
    
    /** 
     * This method is an experimental method for collision checking
     * based on nearby colours
     * Worked on by: Sean
     * @param x x coordinate of where to check
     * @param y y coordinate of where to check
     * @return boolean returns true if the player can move and false if they cannot (i.e. colour not detected, colour detected)
     * @throws IOException on image not found
     */
    public boolean collisionCheck(int x, int y) throws IOException{
        BufferedImage image = ImageIO.read(getClass().getResource("/test.png")); //we need to use bufferedImage here since getRGB only works with bufferedImage

        int check = image.getRGB(x, y); //getRGB returns a really weird integer, have to fix this later
        System.out.println(check);
        if(check == 000000000){ //fix this with getRGB if we're going to use colour picking
            return false;
        }else{
            return true;
        }
    }

    public void stressEffects(int strength, Group root) throws IOException{
        Image image = new Image("/test.png"); //use test image for now change it later
        ImageView iv = new ImageView(image);
        iv.setX(0.0);
        iv.setY(0.0);
        iv.setFitWidth(300.0);
        iv.setFitHeight(300.0);
        iv.setPreserveRatio(true);
        
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(strength); //due to nature of gaussian blur, the radius controls the strength of the blur
        iv.setEffect(gb);
        root.getChildren().add(iv);

        Image vignette = new Image("/vignette.png"); //testing vignette image. Also testing for transparent backgrounds with PNG. Looks like it works?
        ImageView vin = new ImageView(vignette);
        vin.setX(0.0);
        vin.setY(0.0);
        vin.setFitWidth(300.0);
        vin.setFitHeight(300.0);
        vin.setPreserveRatio(false);
        root.getChildren().add(vin);
    }
    /** 
     * Main Method that launches the application
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
}