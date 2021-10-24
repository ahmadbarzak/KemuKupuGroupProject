package application;

import java.util.Comparator;

public class ScoreComparator implements Comparator<String> {
/**
 * This is the class that overrides the compare method to allow leading integer to be sorted in descending order
 * Used to sort the leaderboard	
 */
	
	/**
	 * This function sorts strings that have a leader integer in descending order
	 * Interpolated code from Thermech and Bee from StackOverflow
	 * link : https://stackoverflow.com/questions/28556129/java-sort-one-array-based-on-values-of-another-array
	 * @param player 1, player 1 - strings to compare (must have integer at beginning of it, seperated from rest of string by space)
	 */
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

