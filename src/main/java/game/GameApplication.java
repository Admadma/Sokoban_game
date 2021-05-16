package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;

public class GameApplication extends Application {
    /**
     * Checks if the helperFolder already exists in the current directory and creates it.
     * Creates the stage in which the application will run.
     *
     * @param stage the Stage object
     * @throws IOException if failed to load file
     */
    @Override
    public void start(Stage stage) throws IOException {
        String helperFolder = System.getProperty("user.dir") + "/helperFolder";
        File file = new File(helperFolder);
        if(!file.exists()){
            if(file.mkdir()){
                Logger.debug("Created folder for savefiles");
            } else {
                Logger.warn("Failed to create folder");
            }
        } else {
            Logger.debug("Helper folder already exists");
        }

        Logger.info("Starting application");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuUI.fxml"));
        stage.setTitle("Sokoban game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
