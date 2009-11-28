package com.tung;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Main extends MIDlet implements CommandListener {

	private Command exitCommand;
	private Command calculateCommand;
	
	private Result result;
	
	private Form form;
	private List overview;
	private Display display;
	
	
	
	private Alert alert;

	public Main() {
		display = Display.getDisplay(this);
		
		exitCommand = new Command("Exit", Command.EXIT, 1);
		
		result = new Result();
		
		form = new EnterResultForm(display, result);
		overview = new List("Overview", List.EXCLUSIVE);
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(form);
	}

	public void commandAction(Command cmd, Displayable disp) {
		display.vibrate(500);
		display.setCurrent(alert, overview);
		if (cmd == exitCommand) {
			try {
				destroyApp(false);
			} catch (MIDletStateChangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notifyDestroyed();
		} else if (cmd == calculateCommand) {
			calculate();
			display.setCurrent(overview);
		}
		display.getDisplay(this).setCurrent(overview);
	}

	private void calculate() {
		System.out.println("calculate");
		overview.append("foobar", null);
	}

}
