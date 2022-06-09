
/**
 * Main class for the WarLens game
 * 
 * @author Sean Yang, Vlad Surdu
 * @version 2.0
 * @since 2022-05-19
 */

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.*;

public class WarLens extends Application {
    PicturePixel[][] pictureArr;

    int checkTime = 0;
    int curTime = 0;
    int disInt = 0;
    int curIndex = 0;
    String currentString = "";
    boolean keyEventActive = false;

    /**
     * This method contains all of the graphics code and calls needed
     * to run the program correclty. At the moment, it contains the call
     * to load and display the array of pixels as well as setting display size,
     * the root stack and setting scene/setting name/showing scene
     * 
     * @param splashStage main stage
     */
    public void start(Stage mainStage) throws IOException {

        // splashScreen(mainStage);

        scene2(mainStage);

        // Group gameLogoRoot = new Group();
        // Group mainMenu = new Group();

        // Group root = new Group();

        // //loadArr(100,100);

        // //displayArr(0,0,root);

        // stressEffects(10, root);

        // Scene scene = new Scene(root, 300, 300);

        // mainStage.setTitle("");
        // mainStage.setScene(scene);
        // mainStage.show();
    }

    public void fadeIn(Parent root, int time) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(time));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public void fadeOut(Parent root, int time) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(time));
        ft.setNode(root);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }

    public void splashScreen(Stage stage) throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("companyLogo.fxml"));
        Scene companyScene = new Scene(companyRoot, 1000, 600);
        Arc arc = (Arc) companyScene.lookup("#arc");

        fadeIn(companyRoot, 3000);

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                if (arc.getLength() > 0) {
                    arc.setLength(arc.getLength() - 3);
                } else {
                    this.stop();
                }
            }
        };

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                if (arc.getLength() < 360) {
                    arc.setLength(arc.getLength() + 3);
                } else {
                    timer2.start();
                    fadeOut(companyRoot, 2000);
                    this.stop();
                }

            }
        };
        timer.start();
        stage.setScene(companyScene);
        stage.show();
    }

    public void scene2(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene2 = new Scene(root, 640, 640);

        Image character = new Image("resources/testChar.png");
        ImageView testChar = new ImageView(character);

        testChar.setX(-10);
        testChar.setY(400);
        testChar.setPreserveRatio(true);
        testChar.setScaleX(1);
        testChar.setScaleY(1);

        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        testChar.setEffect(blur);
        
        root.getChildren().add(testChar);

        TranslateTransition charMove = new TranslateTransition();
        charMove.setDuration(Duration.millis(5000));
        charMove.setNode(testChar);
        charMove.setByX(300);
        charMove.setByY(200);
        charMove.setCycleCount(1);
        charMove.setAutoReverse(false);
        charMove.play();
        

        //textTool("Desktop/ICS4UO_ISP/WarLens/src/scene2Text.txt", root, scene2);

        stage.setScene(scene2);
        stage.show();
    }

    public void textTool(String filepath, Group root, Scene scene) throws IOException {

        ArrayList<String> textCache = new ArrayList<String>();

        Scanner sc = new Scanner(new File(filepath));

        while (sc.hasNext()) {
            textCache.add(sc.nextLine());
        }
        sc.close();

        currentString = textCache.get(curIndex);

        Image textBoxImage = new Image("textBox.png");
        ImageView textBox = new ImageView(textBoxImage);
        textBox.setX(0);
        textBox.setY(506);
        root.getChildren().addAll(textBox);

        Text text = new Text();
        text.setX(20);
        text.setY(570);
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        text.setWrappingWidth(600);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        root.getChildren().add(text);

        Image arrowImage = new Image("arrow.png");
        ImageView arrow = new ImageView(arrowImage);
        arrow.setX(590);
        arrow.setY(605);
        arrow.setPreserveRatio(true);
        arrow.setFitHeight(28);
        arrow.setFitWidth(28);
        arrow.setVisible(false);
        root.getChildren().add(arrow);

        // Asking questions
        // A. Correct B. Incorrect C. Incorrect
        // Stage 1:
        Text question = new Text();
        question.setText("When stressed, Gleb should:");
        question.setX(20);
        question.setY(570);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        question.setVisible(false);
        root.getChildren().add(question);

        Button button1 = new Button("Correct");
        button1.setLayoutX(180);
        button1.setLayoutY(600);
        button1.setScaleX(1.5);
        button1.setScaleY(1.5);
        button1.setVisible(false);

        Button button2 = new Button("Incorrect");
        button2.setLayoutX(300);
        button2.setLayoutY(600);
        button2.setScaleX(1.5);
        button2.setScaleY(1.5);
        button2.setVisible(false);

        Button button3 = new Button("Incorrect");
        button3.setLayoutX(430);
        button3.setLayoutY(600);
        button3.setScaleX(1.5);
        button3.setScaleY(1.5);
        button3.setVisible(false);

        root.getChildren().add(button1);
        root.getChildren().add(button2);
        root.getChildren().add(button3);

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long nanos) {
                int milis = (int) (nanos / 1000000);
                curTime = (int) Math.floor(0.03 * milis);
                if (disInt == currentString.length()) {
                    arrow.setVisible(true);
                    curIndex++;
                    keyEventActive = true;
                    this.stop();
                } else if (checkTime != curTime) {
                    text.setText(currentString.substring(0, disInt + 1));
                    checkTime = curTime;
                    disInt++;
                }
            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (curIndex == textCache.size()) {
                    text.setVisible(false);
                    arrow.setVisible(false);
                    keyEventActive = false;
                    timer.stop();
                    question.setVisible(true);
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                } else if (keyEventActive) {
                    currentString = textCache.get(curIndex);
                    disInt = 0;
                    arrow.setVisible(false);
                    timer.start();
                    keyEventActive = false;
                }
            }
        });

        timer.start();
    }

    /**
     * This method loads a 2D array of size b and h with the
     * "redPixel.png" image
     * Worked on by: Vlad, Sean
     * 
     * @param b number of columns
     * @param h number of rows
     */
    public void loadArr(int b, int h) {
        pictureArr = new PicturePixel[h][b];
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < b; col++) {
                Image img = new Image("/redPixel.png");
                pictureArr[row][col] = new PicturePixel(img, 1, img.getHeight());
            }
        }
    }

    /**
     * This method displays the array by adding the images to
     * the root stack starting at the x and y coordinates
     * Worked on by: Vlad
     * 
     * @param x    x coordinate of where to start adding pictures
     * @param y    y coordinate of where to start adding pictures
     * @param root root stack
     */
    public void displayArr(int x, int y, Group root) {
        for (int row = 0; row < pictureArr.length; row++) {
            for (int col = 0; col < pictureArr[0].length; col++) {
                PicturePixel p = pictureArr[row][col];
                p.imageView.setX(x + col * (p.sideLength + 2));
                p.imageView.setY(y + row * (p.sideLength + 2));
                root.getChildren().add(p.imageView);
            }
        }

    }

    /**
     * This method is an experimental method to test two things for the stress
     * mechanic
     * of the game. Test 1: blur effect Test 2: transparent PNG images
     * Worked on by: Sean
     * 
     * @param strength sets the radius (i.e. strength) of the blur effect
     * @param root     root stack
     * @throws IOException on image not found
     */
    public void stressEffects(int strength, Group root) throws IOException {
        Image image = new Image("/test.png"); // use test image for now change it later
        ImageView iv = new ImageView(image);
        iv.setX(0.0);
        iv.setY(0.0);
        iv.setFitWidth(300.0);
        iv.setFitHeight(300.0);
        iv.setPreserveRatio(true);

        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(strength); // due to nature of gaussian blur, the radius controls the strength of the blur
        iv.setEffect(gb);
        root.getChildren().add(iv);

        Image vignette = new Image("/vignette.png"); // testing vignette image. Also testing for transparent backgrounds
                                                     // with PNG. Looks like it works?
        ImageView vin = new ImageView(vignette);
        vin.setX(0.0);
        vin.setY(0.0);
        vin.setFitWidth(300.0);
        vin.setFitHeight(300.0);
        vin.setPreserveRatio(false);
        root.getChildren().add(vin);
        // for vignetting maybe also blur it? maybe not
    }

    /**
     * Main Method that launches the application
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    // /**
    // * This method is an experimental method for collision checking
    // * based on nearby colours
    // * Worked on by: Sean
    // *
    // * @param x x coordinate of where to check
    // * @param y y coordinate of where to check
    // * @return boolean returns true if the player can move and false if they
    // cannot
    // * (i.e. colour not detected, colour detected)
    // * @throws IOException on image not found
    // */
    // public boolean collisionCheck(int x, int y) throws IOException {
    // BufferedImage image = ImageIO.read(getClass().getResource("/test.png")); //
    // we need to use bufferedImage here
    // // since getRGB only works with
    // // bufferedImage

    // int check = image.getRGB(x, y); // getRGB returns a really weird integer,
    // have to fix this later
    // System.out.println(check);
    // if (check == 000000000) { // fix this with getRGB if we're going to use
    // colour picking
    // return false;
    // } else {
    // return true;
    // }
    // }
}