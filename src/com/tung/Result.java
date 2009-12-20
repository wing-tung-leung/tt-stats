package com.tung;

public class Result {
	
	final public static int HOME = 100;
	final public static int VISITOR = -100;
	
	private static final int GAMES_PER_PLAYER = 3;
	private static final int DOUBLE_GAME = 6;
	
	private int[][] scores;
	
	public Result() {
		scores = new int[10][2];
	}
	
	public void setScore(int game, int setsHome, int setsVisitor) {
		if (game < 1 || game > 10) throw new IllegalArgumentException("Invalid game number " + game);
		if (setsHome < 0 || setsHome > 3) throw new IllegalArgumentException("Invalid home set count " + setsHome);
		if (setsVisitor < 0 || setsVisitor > 3) throw new IllegalArgumentException("Invalid visitor set count " + setsVisitor);
		
		scores[game - 1] = new int[]{setsHome, setsVisitor};
	}
	
	public void dump() {
		for (int match = 0 ; match < 10 ; ++match) {
			System.out.println("match " + (match + 1) + ": " + scores[match][0] + " - " + scores[match][1]);
		}
	}

	/** Appends in short format the score details to string buffer. */
	public void dump(StringBuffer buffer) {
		for (int match = 0 ; match < scores.length ; ++match) {
			buffer.append(scores[match][0]).append("-").append(scores[match][1]).append(Main.NEWLINE);
		}
	}

	/**
	 * Returns results per player in 4-tuple. First two elements are game results, and last pair
	 * contains detailed set totals.
	 */
	public int[] getSummary(int team, int player) {
		if (team != HOME && team != VISITOR) throw new IllegalArgumentException("Invalid team " + team); 
		if (player < 0 || player > 3) throw new IllegalArgumentException("Invalid player: " + player);
		
		switch (team) {
		case HOME:
			switch (player) {
			case 1:
				return summarizeGames(2, 5, 9);
			case 2:
				return summarizeGames(1, 4, 8);
			case 3:
				return summarizeGames(0, 3, 7);
			}
		case VISITOR:
			switch (player) {
			case 1:
				return invert(summarizeGames(1, 3, 9));
			case 2:
				return invert(summarizeGames(0, 5, 8));
			case 3:
				return invert(summarizeGames(2, 4, 7));
			}
		}
		throw new RuntimeException("Internal error");
	}
	
	public static int[] invert(int[] result) {
		if (result.length != 4) throw new IllegalArgumentException("result not 4-tuple: " + result.toString());
		
		int swap = result[0];
		result[0] = result[1];
		result[1] = swap;
		swap = result[2];
		result[2] = result[3];
		result[3] = swap;
		return result;
	}

	private int[] summarizeGames(int g1, int g2, int g3) {
		int setsWon = scores[g1][0] + scores[g2][0] + scores[g3][0];
		int setsLost = scores[g1][1] + scores[g2][1] + scores[g3][1];
		int gamesWon = gameHomeWon(g1) + gameHomeWon(g2) + gameHomeWon(g3);
		int gamesLost = GAMES_PER_PLAYER - gamesWon;
		return new int[]{setsWon, setsLost, gamesWon, gamesLost};
	}

	/**
	 * Returns 1 if home player has won, or 0 visitor has won.
	 * @param gameNumber starting from 0 up till 9
	 */
	private int gameHomeWon(int gameNumber) {
		int[] gameScores = scores[gameNumber];
		return gameScores[0] > gameScores[1] ? 1 : 0;
	}

	/** Simple test method. */
	static public void main(String args[]) {
		Result r = new Result();
		r.setScore(1, 3, 0);
		r.setScore(2, 2, 3);
		r.setScore(3, 2, 3);
		r.setScore(4, 2, 3);
		r.setScore(5, 2, 3);
		r.setScore(6, 2, 3);
		r.setScore(7, 2, 3);
		r.setScore(8, 3, 0);
		r.setScore(9, 2, 3);
		r.setScore(10, 3, 0);
		r.dump();
		
		int[] home3 = r.getSummary(HOME, 3);
		System.out.println(home3[0] + " - " + home3[1]);
	}

	public int[] getDoubleSets() {
		int[] doubleSetScore = scores[DOUBLE_GAME];
		int doubleGameWon = doubleSetScore[0] > doubleSetScore[1] ? 1 : 0;
		int doubleGameLost = doubleSetScore[0] < doubleSetScore[1] ? 1 : 0;
		return new int[]{doubleSetScore[0], doubleSetScore[1], doubleGameWon, doubleGameLost};
	}

	public int[] getSetTotals() {
		int home = 0;
		int visitor = 0;
		for (int i = 0 ; i < scores.length ; ++i) {
			home += scores[i][0];
			visitor += scores[i][1];
		}
		return new int[]{home, visitor};
	}

	public int[] getTotals() {
		int[] totals = new int[4];
		for (int i = 0 ; i < scores.length ; ++i) {
			int[] gameScore = scores[i];
			totals[0] += gameScore[0];
			totals[1] += gameScore[1];
			if (gameScore[0] > gameScore[1]) {
				++totals[2];
			} else {
				++totals[3];
			}
		}
		return totals;
	}

}
