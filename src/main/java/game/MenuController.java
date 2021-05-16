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
    private void onStartNewGame(ActionEvent event) throws IOException {
        String sourceFile = "/DefaultGameState.xml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/selectName.fxml"));
        Parent root = fxmlLoader.load();
        SelectNameController controller = fxmlLoader.<SelectNameController>getController();
        controller.setSourceFile(sourceFile);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void onLoadGame(ActionEvent event) throws IOException {
        String sourceFile = "/SavedGameState.xml";
        String savedDataPath = System.getProperty("user.dir") + "/helperFolder" + sourceFile;
        File saveFile = new File(savedDataPath);
        if(saveFile.exists()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameUI.fxml"));
            Parent root = fxmlLoader.load();
            GameController controller = fxmlLoader.<GameController>getController();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.createGame(sourceFile, "", stage);
            Scene scene = new Scene(root, 800, 800);
            scene.getRoot().requestFocus();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } else {
            Logger.warn("Couldn't find savefile, loading default game");
            onStartNewGame(event);
        }
    }


    @FXML
    private void onInfo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/infoMenuUI.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onExit(ActionEvent event) throws IOException {
        Logger.debug("Exiting application");
        Platform.exit();
    }

    private void startGame(ActionEvent event, String sourceFile) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/selectName.fxml"));
        Parent root = fxmlLoader.load();
        SelectNameController controller = fxmlLoader.<SelectNameController>getController();
        controller.setSourceFile(sourceFile);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
