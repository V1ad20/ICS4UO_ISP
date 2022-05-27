/**
 * Main class for the WarLens game
 * 
 * @author Sean Yang, Vlad Surdu, Ana-Maria
 * @version 2.0
 * @since 2022-05-19
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    public void start(Stage splashStage){

        loadArr(100,100);

        Group root = new Group();
        
        displayArr(0,0,root);

        Scene scene = new Scene(root,  640, 480);
        
        splashStage.setTitle("Map Test");
        splashStage.setScene(scene);
        splashStage.show();
    }

    
    /** 
     * This method loads a 2D array of size b and h with the
     * "redPixel.png" image
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
     * @param x x coordinate of where to check
     * @param y y coordinate of where to check
     * @return boolean returns true if the player can move and false if they cannot (i.e. colour not detected, colour detected)
     * @throws IOException on image not found
     */
    public boolean collisionCheck(int x, int y) throws IOException{
        //File file = new File("/src/test.png");
        BufferedImage image = ImageIO.read(getClass().getResource("/test.png"));

        int check = image.getRGB(x, y);
        System.out.println(check);
        if(check == 000000000){
            return false;
        }else{
            return true;
        }
    }

    
    /** 
     * Main Method that launches the application
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
}