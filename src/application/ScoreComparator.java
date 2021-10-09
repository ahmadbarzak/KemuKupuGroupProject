package application;

import java.util.Comparator;

public class ScoreComparator implements Comparator<String> {
	
    @Override
    public int compare (String player1, String player2) {
    	
        int score1 = Integer.parseInt(player1.split(" ")[0]);
        int score2 = Integer.parseInt(player2.split(" ")[0]);
        int comp = Integer.compare(score1, score2);
        if (comp != 0) {
            return comp;
        }
        return player1.compareTo(player2);
    }
}

