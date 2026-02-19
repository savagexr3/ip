package voy.ui.gui;

import static voy.command.CommandType.GREET;

import java.net.URISyntaxException;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import voy.command.CommandType;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private Media media = new Media(getClass().getResource("/media/sound-effect.mp3").toExternalForm());
    private MediaPlayer sfx = new MediaPlayer(media);
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

    /**
     * Initializes the Main Window UI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getVoyDialog("Hello! I'm Voy 🌊 "
                                + "\nHow can I help you today?\n\nHINT: Type \"list\" to start!",
                        voyImage, GREET)
        );
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
        sfx.stop(); // rewind to start
        sfx.play();
        String input = userInput.getText();
        String response = voy.getResponse(input);
        CommandType commandType = voy.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getVoyDialog(response, voyImage, commandType)
        );
        if (commandType == CommandType.BYE) {
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
        userInput.clear();
    }
}
