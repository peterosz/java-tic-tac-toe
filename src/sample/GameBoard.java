package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GameBoard extends Main{
    public GridPane gameBoardPane;
    public Button GameButton;
    public ArrayList<Button> gameButtonList;

    public GameBoard(){
        this.gameButtonList = new ArrayList<>();
        this.gameBoardPane = new GridPane();
        for (int n = 1; n<10; n++) {
            Button button = createButton(Integer.toString(n));
            gameButtonList.add(button);
            int row = (n-1) / 3;
            int col = (n-1) % 3;
            gameBoardPane.add(button, col, row);
        }

    }

    private Button createButton(String number) {
        Button button = new Button();
        // set (CSS) id for element
        button.setId(number);
        button.setPrefWidth(100);
        button.setPrefHeight(100);
        return button;
    }

    public void resetBoard(){
        for (Button b: this.gameButtonList){
            b.setText("");
        }
    }
}
