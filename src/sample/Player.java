package sample;

public class Player {
    private String name;
    private int score;

    public Player(String playerName, int playerScore){
        this.name = playerName;
        this.score = playerScore;
    }
    public String getPlayerName(){
        return name;
    }
    public void setPlayerName(String newName){
        name = newName;
    }
    public int getPlayerScore(){
        return score;
    }
    public void setPlayerScore(int newScore){
        score = newScore;
    }
}
