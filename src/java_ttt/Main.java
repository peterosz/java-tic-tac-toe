package java_ttt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java_ttt.Logic.*;

public class Main extends Application {

    private String[] gameState = {"-", "-", "-", "-", "-", "-", "-", "-", "-"};
    private Player playerX = new Player("Player", 0);
    private Player playerO = new Player("Player", 0);
    private Tie tie = new Tie(0);
    private Label tieLabel = new Label();
    private Label playerXLabel = new Label();
    private Label playerOLabel = new Label();
    private GameBoard gameBoard;
    private Map<Boolean, String> player= new HashMap<>();
    private boolean starter = firstPlayer();
    private int roundCounter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        roundCounter = 1;

        player.put(true, "X");
        player.put(false, "O");

        setPlayers(playerX, playerO);
        showFirstPlayerInfo(player, starter, playerX, playerO);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        GridPane rootBoard = new GridPane();
        rootBoard.setMinSize(600,624);

        gameBoard = new GameBoard();

        for (int i = 0; i < 9; i++){
            gameBoard.gameButtonList.get(i).setOnAction(new buttonActionHandler(i+1));
        }

        gameBoard.gameBoardPane.setMinSize(600,600);

        GridPane gameScore = new GridPane();
        gameScore.setMinSize(600, 24);

        String playerXName = playerX.getPlayerName();
        int playerXScore = playerX.getPlayerScore();

        playerXLabel.setText(cutName(playerXName) +" X: "+playerXScore);
        playerXLabel.setMinSize(200, 24);
        playerXLabel.setAlignment(Pos.CENTER);

        String playerOName = playerO.getPlayerName();
        int playerOScore = playerO.getPlayerScore();

        playerOLabel.setText(cutName(playerOName) +" O: "+playerOScore);
        playerOLabel.setMinSize(200, 24);
        playerOLabel.setAlignment(Pos.CENTER);

        if (starter == true) {
            playerXLabel.setStyle("-fx-background-color: #77ff85;");
        } else {
            playerOLabel.setStyle("-fx-background-color: #77ff85;");
        }

        tieLabel.setMinSize(200, 24);
        tieLabel.setAlignment(Pos.CENTER);
        tieLabel.setText("Tie: "+ tie.getTieScore());

        gameScore.add(playerXLabel, 1, 1);
        gameScore.add(playerOLabel,2, 1);
        gameScore.add(tieLabel,3, 1);

        Scene scene = new Scene(rootBoard, 600, 624);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);

        rootBoard.add(gameScore, 1, 1);
        rootBoard.add(gameBoard.gameBoardPane, 1, 2);

        primaryStage.setTitle("Tic-Tac-Toe by UptownFunktion");
        primaryStage.show();
    }

    public static void setPlayers(Player playerX, Player playerO) {
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
    }

    private static void showFirstPlayerInfo(Map<Boolean, String> player, boolean starter, Player playerX, Player playerO) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("First player info");
        String playersMark = player.get(starter);
        alert.setHeaderText("The first player is: " + playersMark);
        String firstPlayerName;
        if (playersMark == "X") {
            firstPlayerName = playerX.getPlayerName();
        } else {
            firstPlayerName = playerO.getPlayerName();
        }
        alert.setContentText(firstPlayerName);
        alert.showAndWait();
    }

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
                    playerXLabel.setStyle("-fx-background-color: #77ff85;");
                    playerOLabel.setStyle("-fx-background-color: transparent;");
                    temp_button.setStyle("-fx-text-fill: blue;");
                } else {
                    playerOLabel.setStyle("-fx-background-color: #77ff85;");
                    playerXLabel.setStyle("-fx-background-color: transparent;");
                    temp_button.setStyle("-fx-text-fill: red;");
                }
                String mark = player.get(starter);
                temp_button.setText(mark);
                starter = !starter;
                gameState[number - 1] = mark;

                if (roundCounter >= 5) {
                    boolean win = checkWin(mark, gameState);
                    String name = null;
                    if (win) {
                        if (mark.equals("X")) {
                            name = playerX.getPlayerName();
                            playerX.setPlayerScore(playerX.getPlayerScore() + 1);
                            playerXLabel.setText(cutName(name) +" X: "+ playerX.getPlayerScore());
                        } else {
                            name = playerO.getPlayerName();
                            playerO.setPlayerScore(playerO.getPlayerScore() + 1);
                            playerOLabel.setText(cutName(name) +" O: "+ playerO.getPlayerScore());
                        }
                        alertGameOver(win, name);
                    } else if (roundCounter == 9) {
                        alertGameOver(win, name);
                        tie.setTieScore(tie.getTieScore() + 1);
                        tieLabel.setText("Tie: "+ tie.getTieScore());
                    }
                }
                roundCounter++;
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
            roundCounter = 0;
        } else {
            System.exit(0);
        }
    }

}