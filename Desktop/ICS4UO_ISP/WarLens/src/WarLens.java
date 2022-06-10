
/**
 * Main class for the WarLens game
 * 
 * @author Sean Yang, Vlad Surdu
 * @version 2.0
 * @since 2022-05-19
 */

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.*;

public class WarLens extends Application {
    PicturePixel[][] pictureArr;

    int checkTime;
    int curTime;
    int disInt;
    int curIndex;
    String currentString;
    boolean keyEventActive;
    boolean animationLocked;

    /**
     * This method contains all of the graphics code and calls needed
     * to run the program correclty. At the moment, it contains the call
     * to load and display the array of pixels as well as setting display size,
     * the root stack and setting scene/setting name/showing scene
     * 
     * @param splashStage main stage
     */
    public void start(Stage mainStage) throws IOException {

        splashScreen(mainStage);
    }

    public void fadeIn(Node root, int time) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(time));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public void fadeOut(Node root, int time) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(time));
        ft.setNode(root);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }

    public void splashScreen(Stage stage) throws IOException {
        Parent companyRoot = FXMLLoader.load(getClass().getResource("resources/splashScreen/companyLogo.fxml"));
        Scene companyScene = new Scene(companyRoot, 640, 640);
        Arc arc = (Arc) companyScene.lookup("#arc");

        fadeIn(companyRoot, 3000);

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                if (arc.getLength() > 0) {
                    arc.setLength(arc.getLength() - 3);
                } else {
                    this.stop();
                    try {
                        scene2(stage);
                    } catch (IOException e) {
                    }
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
        Timer timer = new Timer();

        textTool("Desktop/ICS4UO_ISP/WarLens/src/resources/scene2TextPart1.txt", root, scene2);

        Image frame1 = new Image("resources/characters/mainCharacter/frame1.png");
        Image frame2 = new Image("resources/characters/mainCharacter/frame2.png");
        Image frame3 = new Image("resources/characters/mainCharacter/frame3.png");
        Image frame4 = new Image("resources/characters/mainCharacter/frame4.png");
        Image vignetteImage = new Image("resources/vignette.png");

        ImageView testChar = new ImageView();
        ImageView vignette = new ImageView(vignetteImage);

        testChar.setX(-10);
        testChar.setY(400);
        testChar.setPreserveRatio(true);
        testChar.setScaleX(1.5);
        testChar.setScaleY(1.5);
        testChar.setEffect(new Glow(0.5));

        vignette.setFitWidth(640);
        vignette.setFitHeight(506);
        vignette.setPreserveRatio(false);
        vignette.setEffect(new Glow(0.5));

        root.getChildren().add(testChar);
        root.getChildren().add(vignette);

        ArrayList<Image> rightMoveFrames = new ArrayList<Image>();
        rightMoveFrames.add(frame1);
        rightMoveFrames.add(frame2);
        rightMoveFrames.add(frame3);
        rightMoveFrames.add(frame4);

        AnimationTimer runningRightAnim = new AnimationTimer() {
            @Override
            public void handle(long nanos) {
                int milis = (int) (nanos / 1000000);
                curTime = (int) Math.floor(0.004 * milis);
                if (checkTime != curTime) {
                    testChar.setImage(rightMoveFrames.get(curIndex));
                    curIndex++;
                    if (curIndex == rightMoveFrames.size()) {
                        curIndex = 0;
                    }
                    checkTime = curTime;
                }
            }
        };

        TranslateTransition charMove = new TranslateTransition();
        charMove.setDuration(Duration.millis(5000));
        charMove.setNode(testChar);
        charMove.setByX(700);
        charMove.setCycleCount(1);
        charMove.setAutoReverse(false);

        AnimationTimer scene2Anim1 = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (!animationLocked) {
                    checkTime = 0;
                    curTime = 0;
                    curIndex = 0;
                    runningRightAnim.start();
                    charMove.play();
                    this.stop();
                }
            }
        };
        scene2Anim1.start();

        Image returnedTestImage = new Image("resources/returnedTest.png");
        ImageView returnedTest = new ImageView(returnedTestImage);
        returnedTest.setPreserveRatio(true);
        returnedTest.setScaleX(3);
        returnedTest.setScaleY(3);
        returnedTest.setX(280);
        returnedTest.setY(210);

        GaussianBlur gausBlur = new GaussianBlur();
        gausBlur.setRadius(15);
        returnedTest.setEffect(gausBlur);

        root.getChildren().add(returnedTest);

        returnedTest.setVisible(false);

        RotateTransition returnedTestRotate = new RotateTransition();
        returnedTestRotate.setAxis(Rotate.Z_AXIS);
        returnedTestRotate.setByAngle(360);
        returnedTestRotate.setDuration(Duration.millis(30000));
        returnedTestRotate.setCycleCount(99999);
        returnedTestRotate.setAutoReverse(false);
        returnedTestRotate.setNode(returnedTest);

        

        AnimationTimer scene2Anim2 = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (!animationLocked) {
                    fadeOut(returnedTest, 2000);
                    scene2Questions(root, scene2);
                    this.stop();
                }
            }
        };

        TimerTask runTextPart2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            textTool("Desktop/ICS4UO_ISP/WarLens/src/resources/scene2TextPart2.txt", root, scene2);
                            returnedTest.setVisible(true);
                            returnedTestRotate.play();
                            fadeIn(returnedTest, 5000);
                            scene2Anim2.start();
                        } catch (IOException e) {
                            System.out.println("FATAL ERROR: file scene2TextPart2 - NOT FOUND");
                        }
                    }
                });
            };
        };

        charMove.setOnFinished(event -> {
            vignette.setVisible(false);
            scene2.setFill(Color.BLACK);
            runningRightAnim.stop();
            timer.schedule(runTextPart2, 1000);
        });

        stage.setScene(scene2);
        stage.show();
    }

    public void textTool(String filepath, Group root, Scene scene) throws IOException {

        ArrayList<String> textCache = new ArrayList<String>();

        checkTime = 0;
        curTime = 0;
        disInt = 0;
        curIndex = 0;
        keyEventActive = false;
        animationLocked = true;

        Scanner sc = new Scanner(new File(filepath));

        while (sc.hasNext()) {
            textCache.add(sc.nextLine());
        }
        sc.close();

        currentString = textCache.get(curIndex);

        Image textBoxImage = new Image("resources/textBox.png");
        ImageView textBox = new ImageView(textBoxImage);
        textBox.setX(0);
        textBox.setY(506);
        root.getChildren().add(textBox);

        Text text = new Text();
        text.setX(20);
        text.setY(570);
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        text.setWrappingWidth(600);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        root.getChildren().add(text);

        Image arrowImage = new Image("resources/arrow.png");
        ImageView arrow = new ImageView(arrowImage);
        arrow.setX(590);
        arrow.setY(605);
        arrow.setPreserveRatio(true);
        arrow.setFitHeight(28);
        arrow.setFitWidth(28);
        arrow.setVisible(false);
        root.getChildren().add(arrow);

        AnimationTimer playText = new AnimationTimer() {

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
                    animationLocked = false;
                    playText.stop();
                } else if (keyEventActive) {
                    currentString = textCache.get(curIndex);
                    disInt = 0;
                    arrow.setVisible(false);
                    keyEventActive = false;
                    playText.start();
                }
            }
        });
        playText.start();
    }

    public void scene2Questions(Group root, Scene scene) {
        Text text = new Text("Question");
        text.setX(20);
        text.setY(570);
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        text.setWrappingWidth(600);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        root.getChildren().add(text);
    }

    /**
     * Main Method that launches the application
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}