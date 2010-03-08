package com.tung;

import javax.microedition.lcdui.Command;

public class Navigation {

	protected final Command editResult = new Command("Edit result", Command.SCREEN, 1);
	protected final Command showSummary = new Command("Summary", Command.SCREEN, 2);
	
	protected final Command sendCommand;
	protected final Command saveCommand;
	protected final Command listCommand;
	protected final Command showHomeResult;
	protected final Command showVisitorResult;
	
	protected final Command exitCommand;
	
	public Navigation() {
		sendCommand = new Command("Send", Command.SCREEN, 1);
		saveCommand = new Command("Save", Command.SCREEN, 3);
		listCommand = new Command("List", Command.SCREEN, 4);
		showHomeResult = new Command("Home result", Command.SCREEN, 5);
		showVisitorResult = new Command("Visitor result", Command.SCREEN, 6);
		
		exitCommand = new Command("Exit", Command.EXIT, 0);
	}

}
