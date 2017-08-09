package sample;

public class Tie {
    private int score;

    public Tie(int tieScore){
        this.score = tieScore;
    }
    public int getTieScore(){
        return score;
    }
    public void setTieScore(int newScore){
        score = newScore;
    }
}
