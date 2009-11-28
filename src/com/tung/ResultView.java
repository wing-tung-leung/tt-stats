package com.tung;

import javax.microedition.lcdui.List;

// TODO add totals per team
// TODO separate view for home and visitor team, switch by single click instead of scrolling?
public class ResultView extends List {
	
	private Result result;
	
	public ResultView(Result result) {
		super("Results", List.EXCLUSIVE);
		
		this.result = result;

		addResult(Result.HOME, 1);
		addResult(Result.HOME, 2);
		addResult(Result.HOME, 3);
		addDoublesResult(Result.HOME);
		
		addResult(Result.VISITOR, 1);
		addResult(Result.VISITOR, 2);
		addResult(Result.VISITOR, 3);
		addDoublesResult(Result.VISITOR);
	}
	
	private void addResult(int team, int player) {
		int[] entry = result.getSets(team, player);
		String prefix = team == Result.HOME ? "Home " : "Visitor ";  
		append(prefix + player + ": " + entry[0] + "-" + entry[1], null);
	}
	
	private void addDoublesResult(int team) {
		int[] entry = result.getDoubleSets();
		if (team == Result.VISITOR) {
			entry = Result.invert(entry);
		}
		String prefix = team == Result.HOME ? "Home " : "Visitor";  
		append(prefix + " DBL : " + entry[0] + "-" + entry[1], null);
	}


}
