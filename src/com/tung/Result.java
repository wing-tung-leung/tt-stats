package com.tung;

public class Result {
	
	final public static int HOME = 100;
	final public static int VISITOR = -100;
	
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
	
	public int[] getSets(int team, int player) {
		if (team != HOME && team != VISITOR) throw new IllegalArgumentException("Invalid team " + team); 
		if (player < 0 || player > 3) throw new IllegalArgumentException("Invalid player: " + player);
		
		switch (team) {
		case HOME:
			switch (player) {
			case 1:
				return getSetsFromGames(2, 5, 9);
			case 2:
				return getSetsFromGames(1, 4, 8);
			case 3:
				return getSetsFromGames(0, 3, 7);
			}
		case VISITOR:
			switch (player) {
			case 1:
				return invert(getSetsFromGames(1, 3, 9));
			case 2:
				return invert(getSetsFromGames(0, 5, 8));
			case 3:
				return invert(getSetsFromGames(2, 4, 7));
			}
		}
		throw new RuntimeException("Internal error");
	}
	
	public static int[] invert(int[] result) {
		if (result.length != 2) throw new IllegalArgumentException("result is no pair: " + result.toString());
		int swap = result[0];
		result[0] = result[1];
		result[1] = swap;
		return result;
	}

	private int[] getSetsFromGames(int g1, int g2, int g3) {
		int won = scores[g1][0] + scores[g2][0] + scores[g3][0];
		int lost = scores[g1][1] + scores[g2][1] + scores[g3][1];
		return new int[]{won, lost};
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
		
		int[] home3 = r.getSets(HOME, 3);
		System.out.println(home3[0] + " - " + home3[1]);
	}

	public int[] getDoubleSets() {
		return new int[]{scores[6][0], scores[6][1]};
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

}
