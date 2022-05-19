import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
 
public class WarLens extends Application {
    public void start(Stage splashStage) {
        Circle circle = new Circle();
        circle.setCenterX(150.0);
        circle.setCenterY(150.0);
        circle.setRadius(50.0);
        circle.setFill(Color.BROWN);
        circle.setStrokeWidth(10);

        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(Duration.millis(1000.0));
        scale.setNode(circle);
        scale.setByX(2.0);
        scale.setByY(2.0);
        scale.setCycleCount(50);
        scale.setAutoReverse(true);
        scale.play();
        
        StackPane root = new StackPane();
        root.getChildren().add(circle);

 Scene scene = new Scene(root, 300, 300);

        splashStage.setTitle("Test Splash");
        splashStage.setScene(scene);
        splashStage.show();
    }
 public static void main(String[] args) {
        launch(args);
    }
}