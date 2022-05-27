import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class stressProto {

    public void start(Stage splashStage) {

        Image image = new Image("/test.png");

        // Setting the image view
        ImageView imageView = new ImageView(image);

        // Setting the position of the image
        imageView.setX(100);
        imageView.setY(70);

        // setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(400);

        // Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        Group root = new Group();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 640, 480);

        splashStage.setTitle("Stress Test");
        splashStage.setScene(scene);
        splashStage.show();
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
