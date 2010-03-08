package com.tung;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;

public class VisitorResultView extends ResultView implements CommandListener {
	
	public VisitorResultView(Result result, Display display, Main main) {
		super(result, display, main, VISITOR_DESC);
		addCommand(main.navigation.showHomeResult);
	}
	
	public void refresh() {
		
		deleteAll();
		
		addPlayerResult(Result.VISITOR, 1);
		addPlayerResult(Result.VISITOR, 2);
		addPlayerResult(Result.VISITOR, 3);
		addDoublesResult(Result.VISITOR);
		addTotals(Result.VISITOR);
	}
	
}
