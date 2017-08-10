package java_ttt;

public class Logic {

    public static boolean firstPlayer() {
        return Math.random() < 0.5;
    }

    public static boolean checkWin(String player, String[] game) {
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

    public static String cutName(String playerName) {
        if (10 < playerName.length()) {
            return playerName.substring(0,10);
        } else {
            return playerName;
        }
    }
}

