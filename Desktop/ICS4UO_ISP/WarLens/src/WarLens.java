
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.geometry.Insets;

public class WarLens extends Application {
    PicturePixel[][] pictureArr;

    int checkTime;
    int curTime;
    int disInt;
    int curIndex;
    String currentString;
    boolean keyEventActive;
    boolean animationLocked;
    int sceneState;

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
        //scene2(mainStage);
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

        ImageView testChar = new ImageView();
        ImageView vignette = new ImageView(new Image("resources/vignette.png"));

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

        ImageView returnedTest = new ImageView(new Image("resources/returnedTest.png"));
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
                    scene2QSet1(root, scene2);
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

        AnimationTimer part2Trigger = new AnimationTimer(){

            @Override
            public void handle(long arg0) {
                if(!animationLocked){
                    scene2P2(stage);
                }
            }
        };

        AnimationTimer part2Text = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if(sceneState == 9){
                    try {
                        textTool("Desktop/ICS4UO_ISP/WarLens/src/resources/scene2TextPart3.txt", root, scene2);
                        this.stop();
                        part2Trigger.start();
                    } catch (IOException e) {
                    }
                }
            }
        };
        part2Text.start();

        

        stage.setScene(scene2);
        stage.show();
    }

    public void scene2QSet1(Group root, Scene scene) {

        sceneState = 1;

        Text question = new Text("When you are stressed, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set1/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set1/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set1/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! When you are stressed, taking deep breathes will help you stay calm! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 1) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet2(root, scene);
                        }
                    }
                });
            }
        });

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });
    }

    public void scene2QSet2(Group root, Scene scene) {

        sceneState = 2;

        Text question = new Text("When you are stressed, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set2/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set2/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set2/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                question.setText(
                        "Correct! Not resting can cause you to be even more stressed! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 2) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet3(root, scene);
                        }
                    }
                });
            }
        });
    }

    public void scene2QSet3(Group root, Scene scene) {

        sceneState = 3;

        Text question = new Text("When you are stressed, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set3/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set3/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set3/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                question.setText(
                        "Correct! Always remember that you are never alone! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 3) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet4(root, scene);
                        }
                    }
                });
            }
        });
    }

    public void scene2QSet4(Group root, Scene scene) {

        sceneState = 4;

        Text question = new Text("When you feel hopeless, should you: (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set4/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set4/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set4/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! When you feel hopeless, talk to people with past experiences for advice! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 4) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet5(root, scene);
                        }
                    }
                });
            }
        });
    }

    public void scene2QSet5(Group root, Scene scene) {

        sceneState = 5;

        Text question = new Text("Should you use the Internet as help? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set5/option1.png")));
        option1.setLayoutX(150);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set5/option2.png")));
        option2.setLayoutX(270);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        Button option3 = new Button();
        option3.setGraphic(new ImageView(new Image("resources/buttons/set5/option3.png")));
        option3.setLayoutX(390);
        option3.setLayoutY(575);
        option3.setPadding(Insets.EMPTY);
        root.getChildren().add(option3);

        root.requestFocus();

        option3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option3.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! Although the Internet can be helpful, make sure to only use trusted sources! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 5) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option3.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            option3.setDisable(true);
                            scene2QSet6(root, scene);
                        }
                    }
                });
            }
        });
    }

    public void scene2QSet6(Group root, Scene scene) {

        sceneState = 6;

        Text question = new Text(
                "Is it a good idea to only use Internet sources that provide negative information? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set6/option1.png")));
        option1.setLayoutX(200);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set6/option2.png")));
        option2.setLayoutX(340);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        root.requestFocus();

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                question.setText(
                        "Correct! It is important to balance staying informed and staying positive! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 6) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            scene2QSet7(root, scene);
                        }
                    }
                });
            }
        });
    }

    public void scene2QSet7(Group root, Scene scene) {

        sceneState = 7;

        Text question = new Text(
                "Is an official government website a good Internet source? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set6/option1.png")));
        option1.setLayoutX(200);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set6/option2.png")));
        option2.setLayoutX(340);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        root.requestFocus();

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! Government websites are one of the most reliable sources of information! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 7) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            scene2QSet8(root, scene);
                        }
                    }
                });
            }
        });
    }

    public void scene2QSet8(Group root, Scene scene) {

        sceneState = 8;

        Text question = new Text(
                "If an Internet source gives information that doesn't match your opinion, should you trust it? (click a button to select answer)");
        question.setX(20);
        question.setY(535);
        question.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        question.setWrappingWidth(600);
        question.setTextAlignment(TextAlignment.CENTER);
        question.setFill(Color.WHITE);
        root.getChildren().add(question);

        Button option1 = new Button();
        option1.setGraphic(new ImageView(new Image("resources/buttons/set6/option1.png")));
        option1.setLayoutX(200);
        option1.setLayoutY(575);
        option1.setPadding(Insets.EMPTY);
        root.getChildren().add(option1);

        Button option2 = new Button();
        option2.setGraphic(new ImageView(new Image("resources/buttons/set6/option2.png")));
        option2.setLayoutX(340);
        option2.setLayoutY(575);
        option2.setPadding(Insets.EMPTY);
        root.getChildren().add(option2);

        root.requestFocus();

        option2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                root.requestFocus();
            }
        });

        option1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                option1.setGraphic(new ImageView(new Image("resources/buttons/correct.png")));
                option2.setGraphic(new ImageView(new Image("resources/buttons/incorrect.png")));
                question.setText(
                        "Correct! Don't avoid information that doesn't match your opinion! The information may be true! (Press any key to continue)");
                question.setY(535);
                root.requestFocus();
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (sceneState == 8) {
                            question.setVisible(false);
                            option1.setVisible(false);
                            option2.setVisible(false);
                            option1.setDisable(true);
                            option2.setDisable(true);
                            sceneState = 9;
                        }
                    }
                });
            }
        });
    }
    
    public void scene2P2(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 640, 640);

        stage.setScene(scene);
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

        ImageView textBox = new ImageView(new Image("resources/textBox.png"));
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

        ImageView arrow = new ImageView(new Image("resources/arrow.png"));
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
                    keyEventActive = true;
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
                    if (disInt < currentString.length()) {
                        disInt = currentString.length();
                        text.setText(currentString);
                    } else {
                        disInt = 0;
                    }
                    currentString = textCache.get(curIndex);
                    arrow.setVisible(false);
                    keyEventActive = false;
                    playText.start();
                }
            }
        });
        playText.start();
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
    /**
     * Main Method that launches the application
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}