package sample;

import com.sun.deploy.util.BlackList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    GameBoard gameBoard;

    class buttonActionHandler implements EventHandler<ActionEvent> {

        private final int number ;

        buttonActionHandler(int number) {
            this.number = number;
        }

        @Override
        public void handle(ActionEvent event) {
            Button temp_button = ((Button)event.getSource());
            if( temp_button.getText() == "" ){
                if (starter == false) {
                    player1Label.setStyle("-fx-background-color: #77ff85;");
                    player2Label.setStyle("-fx-background-color: transparent;");
                    temp_button.setStyle("-fx-text-fill: blue;");
                } else {
                    player2Label.setStyle("-fx-background-color: #77ff85;");
                    player1Label.setStyle("-fx-background-color: transparent;");
                    temp_button.setStyle("-fx-text-fill: red;");
                }
                String mark = player.get(starter);
                temp_button.setText(mark);
                starter = !starter;
                gameState[number - 1] = mark;

//
//          //TODO: Win check from here
                if (round_counter >= 5) {
                    boolean win = checkWin(mark, gameState);
                    String name = null;
                    if (win) {
                        if (mark.equals("X")) {
                            name = playerX.getPlayerName();
                            playerX.setPlayerScore(playerX.getPlayerScore() + 1);
                            player1Label.setText(name +" X: "+ playerX.getPlayerScore());
                        } else {
                            name = playerO.getPlayerName();
                            playerO.setPlayerScore(playerO.getPlayerScore() + 1);
                            player2Label.setText(name +" O: "+ playerO.getPlayerScore());
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

    public void alertGameOver(boolean win, String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game over!");
        if (win) {
            alert.setHeaderText("The winner is: "+name);
            alert.setContentText("Want to play again?");
        } else {
            alert.setHeaderText("It is a tie!");
            alert.setContentText("Want to play again?!");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for (int i = 0; i< gameState.length; i++){
                gameState[i] = "-";
            }
            gameBoard.resetBoard();
            round_counter = 0;
        } else {
            System.exit(0);
        }
    }

    Map<Boolean, String> player= new HashMap<>();

    private boolean starter = firstPlayer();
    private int round_counter;

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

        gameBoard = new GameBoard();

        for (int i = 0; i < 9; i++){
            gameBoard.gameButtonList.get(i).setOnAction(new buttonActionHandler(i+1));
        }

        gameBoard.gameBoardPane.setMinSize(300,300);

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
            playerOName = playerXName.substring(0, 7);
        }
        int playerOScore = playerX.getPlayerScore();

        player2Label.setMinSize(100, 24);
        player2Label.setAlignment(Pos.CENTER);
        player2Label.setText(playerOName+" O: "+playerOScore);

        if (starter == true) {
            player1Label.setStyle("-fx-background-color: #77ff85;");
        } else {
            player2Label.setStyle("-fx-background-color: #77ff85;");
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
        rootBoard.add(gameBoard.gameBoardPane, 1, 2);

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


