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
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {

    class buttonActionHandler implements EventHandler<ActionEvent> {

        private final int number ;

        buttonActionHandler(int number) {
            this.number = number ;
        }
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Event " + number);
            Button temp_button = ((Button)event.getSource());
            if( temp_button.getText() == "" ){
            String mark = player.get(starter);
            System.out.printf(mark);
            temp_button.setText(mark);
            starter = !starter;
            round_counter++;
//
//            //TODO: Win check from here
            }
        }


    }

    Map<Boolean, String> player= new HashMap<>();
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

    private boolean starter = firstPlayer();
    private int round_counter;

    private Button createGridButton(int number) {
        Button button = createButton(Integer.toString(number));
        button.setOnAction(new buttonActionHandler(number));
        return button ;
    }

    private Button createButton(String number) {
        Button button = new Button();
        // set (CSS) id for element
        button.setId(number);

        button.setPrefWidth(100);
        button.setPrefHeight(100);
        return button ;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        round_counter = 1;

        player.put(true, "X");
        player.put(false, "O");

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        GridPane rootBoard = new GridPane();
        rootBoard.setMinSize(300,324);

        GridPane gameBoard = new GridPane();
        // CREATE BUTTON
        for (int n = 1; n<10; n++) {
            Button button = createGridButton(n);
            int row = (n-1) / 3;
            int col = (n-1) % 3;
            gameBoard.add(button, col, row);
        }

        // set (CSS) id for element


        // set button action
//        List P1Win = new ArrayList<>();

//        button.setOnAction(new buttonHandler(number));
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

    public static boolean firstPlayer() {
        return Math.random() < 0.5;
    }
}

