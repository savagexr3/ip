package voy.ui.gui;

import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import voy.command.CommandType;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    private final AudioClip sound = new AudioClip(getClass().getResource("/media/sound-effect.mp3").toURI().toString());
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Voy voy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image voyImage = new Image(this.getClass().getResourceAsStream("/images/voy.png"));

    public MainWindow() throws URISyntaxException {
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Orbit instance */
    public void setVoy(Voy voy) {
        this.voy = voy;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        sound.play();
        String input = userInput.getText();
        String response = voy.getResponse(input);
        CommandType commandType = voy.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getVoyDialog(response, voyImage, commandType)
        );
        userInput.clear();
    }
}
