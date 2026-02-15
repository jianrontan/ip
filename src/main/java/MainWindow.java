import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private KirkStein kirkStein;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Kirk.png"));
    private Image kirkSteinImage = new Image(this.getClass().getResourceAsStream("/images/Netanyahu.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the KirkStein instance
     *
     * @param d KirkStein instance
     */
    public void setKirkStein(KirkStein d) {
        kirkStein = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing KirkStein's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.equals("bye")) {
            Platform.exit();
            return;
        }

        String response = kirkStein.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKirkSteinDialog(response, kirkSteinImage)
        );
        userInput.clear();
    }
}
