package game.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class VictoryScreenController {

    private String playerName;
    private int numberOfMoves;
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    public void setNumberOfMoves(int numberOfMoves){
        this.numberOfMoves = numberOfMoves;
    }
    @FXML
    private Text text;


    @FXML
    private void initialize(){
        Platform.runLater(() ->{
            text.setText(playerName + " has completed the game \nin " + numberOfMoves + " moves!");
        });
    }

    @FXML
    private void onMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuUI.fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }


}

