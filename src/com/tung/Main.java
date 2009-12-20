package com.tung;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Main extends MIDlet implements CommandListener {

	public static final char NEWLINE = '\n';

	private Command exitCommand;
	
	protected Result result;
	
	private Display display;
	
	protected EnterResultForm enterResultForm;
	protected ResultSender resultSender;
	protected ResultView resultView;
	protected ResultList resultList;
	protected MatchInfo matchInfo;
	
	protected Navigation navigation;
	
	public Main() {
		display = Display.getDisplay(this);
		
		exitCommand = new Command("Exit", Command.EXIT, 1);
		
		navigation = new Navigation();
		
		result = new Result();
		matchInfo = new MatchInfo(display, this);
		
		resultSender = new ResultSender(display, result, this);
		resultView = new ResultView(result, display, this);
		resultList = new ResultList(display, this);
		
		enterResultForm = new EnterResultForm(display, result, this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(enterResultForm);
	}

	public void commandAction(Command cmd, Displayable disp) {
		if (cmd == exitCommand) {
			try {
				destroyApp(false);
			} catch (MIDletStateChangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notifyDestroyed();
		}
	}

}
