package game.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class SelectNameController {

    private String sourceFile;

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    @FXML
    private TextField nameField;

    @FXML
    private void initialize() {
        nameField.setText(System.getProperty("user.name"));
    }

    @FXML
    private void onStart(ActionEvent event) throws IOException {
        Logger.info("Name entered: {}", nameField.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameUI.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.<GameController>getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.createGame(sourceFile, nameField.getText(), stage);

        Scene scene = new Scene(root, 800, 800);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}

