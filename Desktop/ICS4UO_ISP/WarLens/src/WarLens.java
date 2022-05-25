import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.spi.ImageWriterSpi;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class WarLens extends Application {
    PicturePixel[][] pictureArr;

    public void start(Stage splashStage) throws IOException{

        loadArr(10, 10);

        Group root = new Group();
        
        displayArr(0,0,root);

        

        System.out.println(root.getChildren());
        // System.out.println(collisionCheck(118, 163));

        Scene scene = new Scene(root, 300, 300);
        

        splashStage.setTitle("Map Test");
        splashStage.setScene(scene);
        splashStage.show();
    }

    public void loadArr(int b, int h){
        pictureArr = new PicturePixel[h][b];
        for (int row = 0; row < h; row++){
            for (int col = 0; col < b; col++){
                Image img = new Image("/redPixel.png");
                pictureArr[row][col] = new PicturePixel(img,1,img.getHeight());
            }
        }
    }

    public void displayArr(int x, int y, Group root){
        for (int row = 0; row < pictureArr.length; row++){
            for (int col = 0; col < pictureArr[0].length; col++){
                PicturePixel p = pictureArr[row][col];
                p.imageView.setX(x+col*(p.sideLength+10));
                p.imageView.setY(y+row*(p.sideLength+10));
                System.out.println(x+col*p.sideLength);
                System.out.println(y+row*p.sideLength); 
                System.out.println(p);
                root.getChildren().add(p.imageView);
            }
        }

    }
    
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

    public static void main(String[] args) {
        launch(args);
    }
}