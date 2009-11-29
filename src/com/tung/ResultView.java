package com.tung;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

// TODO add totals per team
// TODO separate view for home and visitor team, switch by single click instead of scrolling?
public class ResultView extends List implements CommandListener {
	
	private Result result;
	
	private Command gotoSend = new Command("Send result", Command.SCREEN, 1);
	
	private Display display;
	private ResultSender resultSender;
	
	public ResultView(Result result, Display display, ResultSender resultSender) {
		super("Results", List.EXCLUSIVE);
		
		this.result = result;
		this.display = display;
		this.resultSender = resultSender;

		addResult(Result.HOME, 1);
		addResult(Result.HOME, 2);
		addResult(Result.HOME, 3);
		addDoublesResult(Result.HOME);
		
		addResult(Result.VISITOR, 1);
		addResult(Result.VISITOR, 2);
		addResult(Result.VISITOR, 3);
		addDoublesResult(Result.VISITOR);
		
		addCommand(gotoSend);
		setCommandListener(this);
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

	public void commandAction(Command cmd, Displayable displayable) {
		if (cmd == gotoSend) {
			display.setCurrent(resultSender);
		}
	}


}
