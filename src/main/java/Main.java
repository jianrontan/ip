import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main entry point for KirkStein GUI
 */
public class Main extends Application {

    private KirkStein kirkStein = new KirkStein();

    @Override
    public void start(Stage stage) {
        try {
            // Load background image to get dimensions
            Image backgroundImage = new Image(this.getClass().getResourceAsStream("/images/Motherland.png"));
            double imageWidth = backgroundImage.getWidth();
            double imageHeight = backgroundImage.getHeight();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Set AnchorPane size to match image
            ap.setPrefWidth(imageWidth);
            ap.setPrefHeight(imageHeight);

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("KirkStein");
            fxmlLoader.<MainWindow>getController().setKirkStein(kirkStein);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
