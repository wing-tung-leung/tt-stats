package com.tung;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;

public class HomeResultView extends ResultView implements CommandListener {
	
	public HomeResultView(Result result, Display display, Main main) {
		super(result, display, main, HOME_DESC);
		addCommand(main.navigation.showVisitorResult);
	}
	
	public void refresh() {
		
		deleteAll();
		
		addPlayerResult(Result.HOME, 1);
		addPlayerResult(Result.HOME, 2);
		addPlayerResult(Result.HOME, 3);
		addDoublesResult(Result.HOME);
		addTotals(Result.HOME);
	}
	
}
