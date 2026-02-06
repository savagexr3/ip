package voy.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import voy.exception.OrbitException;

/**
 * The {@code MainApp} class serves as the entry point of the Voy GUI application.
 * It is responsible for initializing the JavaFX stage, loading the main window,
 * and connecting the user interface with the core application logic.
 */
public class MainApp extends Application {

    /** The core logic handler for processing user commands. */
    private Voy voy;

    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     * @throws Exception if an unexpected error occurs during application startup
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            voy = new Voy("./data/voy.txt");

            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = loader.load();

            stage.setTitle("Voy");

            Image image = new Image("/images/icon.jpeg");
            stage.getIcons().add(image);

            MainWindow controller = loader.getController();
            controller.setVoy(voy);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (OrbitException e) {
            // Handles errors that occur during application initialization
            e.getMessage();
        }
    }
}
