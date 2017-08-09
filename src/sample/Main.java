package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //set the players
        Player playerX = new Player("Player", 0);
        Player playerO = new Player("Player", 0);
        TextInputDialog playerXInput = new TextInputDialog("Name...");
        playerXInput.setTitle("Player X name");
        playerXInput.setHeaderText("Player X, type your name!");
        playerXInput.setContentText("Name:");
        Optional<String> inputX = playerXInput.showAndWait();
        if (inputX.isPresent()) {
            playerX.setPlayerName(inputX.get());
        }
        TextInputDialog playerOInput = new TextInputDialog("Name...");
        playerOInput.setTitle("Player O name");
        playerOInput.setHeaderText("Player O, type your name!");
        playerOInput.setContentText("Name:");
        Optional<String> inputO = playerOInput.showAndWait();
        if (inputO.isPresent()) {
            playerO.setPlayerName(inputO.get());
        }
        Tie tie = new Tie(0);

        // create buttons
        Button button = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();
        Button button5 = new Button();
        Button button6 = new Button();
        Button button7 = new Button();
        Button button8 = new Button();
        Button button9 = new Button();

        // set (CSS) id for element
        button.setId("b1");

        button.setPrefWidth(100);
        button.setPrefHeight(100);

        button2.setPrefWidth(100);
        button2.setPrefHeight(100);

        button3.setPrefWidth(100);
        button3.setPrefHeight(100);

        button4.setPrefWidth(100);
        button4.setPrefHeight(100);

        button5.setPrefWidth(100);
        button5.setPrefHeight(100);

        button6.setPrefWidth(100);
        button6.setPrefHeight(100);

        button7.setPrefWidth(100);
        button7.setPrefHeight(100);

        button8.setPrefWidth(100);
        button8.setPrefHeight(100);

        button9.setPrefWidth(100);
        button9.setPrefHeight(100);

        // set button action
        /*
        List P1Win = new ArrayList<>();

        button.setOnAction(e -> {
            button.setText("O");
            P1Win.add(0);
        });
        */

        GridPane rootBoard = new GridPane();
        rootBoard.setMinSize(300,324);

        GridPane gameBoard = new GridPane();
        gameBoard.add(button, 1, 1);
        gameBoard.add(button2, 2, 1);
        gameBoard.add(button3, 3, 1);
        gameBoard.add(button4, 1, 2);
        gameBoard.add(button5, 2, 2);
        gameBoard.add(button6, 3, 2);
        gameBoard.add(button7, 1, 3);
        gameBoard.add(button8, 2, 3);
        gameBoard.add(button9, 3, 3);
        gameBoard.setMinSize(300,300);

        GridPane gameScore = new GridPane();
        gameScore.setMinSize(300, 24);


        String playerXName = playerX.getPlayerName();
        int playerXScore = playerX.getPlayerScore();

        Label player1Label = new Label();
        player1Label.setMinSize(100, 24);
        player1Label.setAlignment(Pos.CENTER);
        player1Label.setText(playerXName+" X: "+playerXScore);

        Label player2Label = new Label();
        player2Label.setMinSize(100, 24);
        player2Label.setAlignment(Pos.CENTER);
        player2Label.setText(playerO.getPlayerName() +" O: " + playerO.getPlayerScore());

        Label tieLabel = new Label();
        tieLabel.setMinSize(100, 24);
        tieLabel.setAlignment(Pos.CENTER);
        tieLabel.setText("Tie: "+ tie.getTieScore());


        gameScore.add(player1Label, 1, 1);
        gameScore.add(player2Label,2, 1);
        gameScore.add(tieLabel,3, 1);

        Scene scene = new Scene(rootBoard, 300, 324);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);

        rootBoard.add(gameScore, 1, 1);
        rootBoard.add(gameBoard, 1, 2);

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);


    }
}