package sample;

import com.sun.deploy.util.BlackList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private String[] gameState = {"-", "-", "-", "-", "-", "-", "-", "-", "-"};

    Player playerX = new Player("Player", 0);
    Player playerO = new Player("Player", 0);
    Tie tie = new Tie(0);
    Label tieLabel = new Label();
    Label player1Label = new Label();
    Label player2Label = new Label();

    class buttonActionHandler implements EventHandler<ActionEvent> {

        private final int number ;

        buttonActionHandler(int number) {
            this.number = number;
        }

        @Override
        public void handle(ActionEvent event) {
            System.out.println("Event " + number);
            Button temp_button = ((Button)event.getSource());
            if( temp_button.getText() == "" ){
                if (starter == false) {
                    player1Label.setStyle("-fx-background-color: #ff7c7c;");
                    player2Label.setStyle("-fx-background-color: transparent;");
                } else {
                    player2Label.setStyle("-fx-background-color: #ff7c7c;");
                    player1Label.setStyle("-fx-background-color: transparent;");
                }
                String mark = player.get(starter);
                System.out.println("Player: " + mark);
                temp_button.setText(mark);
                starter = !starter;
                System.out.println(starter);
                System.out.println("Round " + round_counter);
                gameState[number - 1] = mark;

//
//          //TODO: Win check from here
                if (round_counter >= 5) {
                    boolean win = checkWin(mark, gameState);
                    System.out.println("checkwin: " + win);
                    String name = null;
                    if (win) {
                        if (mark.equals("X")) {
                            name = playerX.getPlayerName();
                            playerX.setPlayerScore(playerX.getPlayerScore() + 1);
                            player1Label.setText(name +" X: "+ playerX.getPlayerScore());
                        } else {
                            name = playerO.getPlayerName();
                            playerO.setPlayerScore(playerO.getPlayerScore() + 1);
                            player2Label.setText(name +" X: "+ playerO.getPlayerScore());
                        }
                        alertGameOver(win, name);
                    } else if (round_counter == 9) {
                        alertGameOver(win, name);
                        tie.setTieScore(tie.getTieScore() + 1);
                        tieLabel.setText("Tie: "+ tie.getTieScore());
                    }
                }
                round_counter++;
            }
        }
    }

    public static void alertGameOver(boolean win, String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over!");
        if (win) {
            alert.setHeaderText("The winner is: ");
            alert.setContentText(name);
        } else {
            alert.setHeaderText("It is a tie!");
            alert.setContentText("Tie!");
        }
        alert.showAndWait();
    }

    Map<Boolean, String> player= new HashMap<>();

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

        //set the players
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

        round_counter = 1;

        player.put(true, "X");
        player.put(false, "O");

        /////////////////////////////////////////////////////////

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("First player info");
        String mark = player.get(starter);
        alert.setHeaderText("The first player is: " + mark);
        String firstPlayerName;
        if (mark == "X") {
            firstPlayerName = playerX.getPlayerName();
        } else {
            firstPlayerName = playerO.getPlayerName();
        }
        alert.setContentText(firstPlayerName);
        alert.showAndWait();

        /////////////////////////////////////////////////////////

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


        gameBoard.setMinSize(300,300);

        GridPane gameScore = new GridPane();
        gameScore.setMinSize(300, 24);


        String playerXName = playerX.getPlayerName();
        if (7 < playerXName.length()) {
            playerXName = playerXName.substring(0, 7);
        }
        int playerXScore = playerX.getPlayerScore();

        player1Label.setMinSize(100, 24);
        player1Label.setAlignment(Pos.CENTER);
        player1Label.setText(playerXName+" X: "+playerXScore);


        String playerOName = playerO.getPlayerName();
        if (7 < playerOName.length()) {
            playerOName = playerOName.substring(0, 7);
        }
        int playerOScore = playerO.getPlayerScore();
        
        player2Label.setMinSize(100, 24);
        player2Label.setAlignment(Pos.CENTER);
        player2Label.setText(playerOName +" O: " + playerOScore);

        if (starter == true) {
            player1Label.setStyle("-fx-background-color: #ff7c7c;");
        } else {
            player2Label.setStyle("-fx-background-color: #ff7c7c;");
        }

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


    private static boolean checkWin(String player, String[] game) {
        int[][] winnerCombos = new int[][] {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
        };
        for (int[] combination : winnerCombos) {
            if (game[combination[0]].equals(player) && game[combination[1]].equals(player) && game[combination[2]].equals(player)) {
                return true;
            }
        }
        return false;
    }
}


