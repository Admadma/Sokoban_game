package game;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;

public class MenuController {

    @FXML
    private void startNewGame(ActionEvent event) throws IOException {
        startGame(event, "default");
    }

    @FXML
    private void onLoadGame(ActionEvent event) throws IOException {
        String savedDataPath = System.getProperty("user.dir") + "/helperFolder" + "/SavedGameState.xml";
        File saveFile = new File(savedDataPath);
        if(saveFile.exists()){
            startGame(event, "save");
        } else {
            Logger.warn("Couldn't find savefile, loading default game");
            startNewGame(event);
        }
    }


    @FXML
    private void onInfo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/InfoMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onExit(ActionEvent event) throws IOException {
        Logger.debug("Exiting application");
        Platform.exit();
    }

    private void startGame(ActionEvent event, String loadingType) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameUI.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.<GameController>getController();
        controller.createGame(loadingType);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 800);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
