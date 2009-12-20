package com.tung;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextField;

public class PlayerField extends TextField implements CommandListener {

	private static final int PLAYER_ID_SIZE = 5;
	
	final private Display display;
	
	public final Command itemCommand;

	public PlayerField(String label, Display display, Command editDetailCommand) {
		super(label, null, PLAYER_ID_SIZE, TextField.NUMERIC);
		itemCommand = new Command("Player details", Command.ITEM, 1);
		this.display = display;
		addCommand(editDetailCommand);
	}

	public void commandAction(Command cmd, Displayable displayable) {
		if (getString().trim().length() == 0) {
			return;
		}
		
		long currentId = Long.parseLong(getString());
		String name = Player.find(currentId);
		if (name == null) {
			String playerName = "joske vermeulen";
			Player.add(currentId, playerName);
			String msg = "Stored player " + playerName + ": " + currentId;
			Alert newPlayer = new Alert("New player", msg, null, AlertType.CONFIRMATION);
			display.setCurrent(newPlayer);
		} else {
			setLabel(name);
		}
		
	}

}
