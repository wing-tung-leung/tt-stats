package com.tung;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class MatchInfo extends Form implements CommandListener {

	private static final char PLAYER_SEPARATOR = ',';

	/** Maximum size of generic names. */
	public static final int NAME_SIZE = 15;

	public static final String MATCH_STORE_PREFIX = "match-";

	private static final char NEWLINE = '\n';
	
	private Display display;
	
	private Main main;
	
	private TextField matchNumber;
	private TextField homeTeam;
	private TextField visitorTeam;
	
	private TextField[] homePlayers;
	private TextField[] visitorPlayers;
	
	private Command saveCommand;
	private Command playerDetailCommand;
	
	public MatchInfo(Display display, Main main) {
		super("Match info");
		this.display = display;
		this.main = main;
		
		saveCommand = new Command("Save", Command.SCREEN, 3);
		playerDetailCommand = new Command("Player details", Command.ITEM, 1);

		addCommand(main.navigation.sendCommand);
		addCommand(saveCommand);
		addCommand(main.navigation.editResult);
		setCommandListener(this);
		
		matchNumber = new TextField("Match", null, 5, TextField.NUMERIC);
		append(matchNumber);
		
		homeTeam = new TextField("H", null, NAME_SIZE, TextField.ANY);
		visitorTeam = new TextField("V", null, NAME_SIZE, TextField.ANY);
		
		loadDefaults();

		append(homeTeam);
		homePlayers = createPlayerList("Home ");
		
		append(visitorTeam);
		visitorPlayers = createPlayerList("Visit ");
	}

	private void loadDefaults() {
		// TODO load default home team and players from persistent storage 
		
	}

	private TextField[] createPlayerList(String prefix) {
		TextField[] list = new TextField[3];
		for (int i = 0 ; i < list.length ; ++i) {
			PlayerField textField = new PlayerField(prefix + (i + 1), display, playerDetailCommand);
			list[i] = textField;
			append(textField);
		}
		return list;
	}
	
	private void save() {
		if (! validateInput()) {
			return;
		}
		
		RecordStore store = null;
		try {
			String matchId = matchNumber.getString();
			String storeName = MATCH_STORE_PREFIX + matchId;
			store = RecordStore.openRecordStore(storeName, true);
			String title = homeTeam.getString() + "-" + visitorTeam.getString();
			byte[] text = title.getBytes();
			int recordId = store.addRecord(text, 0, text.length);
			System.out.println("added record " + recordId);
			int count = store.getNumRecords();
			System.out.println("count = " + count);
			
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
			
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
			
		} catch (RecordStoreException e) {
			e.printStackTrace();
			
		} finally {
			if (store != null) {
				try {
					store.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Checks if all input fields contains "valid" text. Returns "true" if ok, "false" if incomplete.
	 */
	public boolean validateInput() {
		if (matchNumber.getString().length() < 5) {
			return false;
		}
		if (homeTeam.getString().length() < 1) {
			return false;
		}
		if (visitorTeam.getString().length() < 1) {
			return false;
		}
		for (int i = 0 ; i > homePlayers.length ; ++i) {
			if (homePlayers[i].getString().trim().length() < 3) {
				return false;
			}
		}
		for (int i = 0 ; i > visitorPlayers.length ; ++i) {
			if (visitorPlayers[i].getString().trim().length() < 3) {
				return false;
			}
		}
		return true;
	}

	public void commandAction(Command cmd, Displayable arg1) {
		if (cmd == saveCommand) {
			save();
		} else if (cmd == main.navigation.editResult) {
			display.setCurrent(main.enterResultForm);
		} else if (cmd == main.navigation.sendCommand) {
			if (validateInput()) {
				display.setCurrent(main.resultSender);
			} else {
				Alert alert = new Alert("Incomplete input");
				display.setCurrent(alert);
			}
		}
		
	}

	/**
	 * Adds match meta data to string buffer. Match number and player IDs.
	 */
	public void dump(StringBuffer buffer) {
		buffer.append("#").append(homeTeam.getString()).append('-').append(visitorTeam.getString()).append(NEWLINE);
		buffer.append(matchNumber.getString()).append(NEWLINE);
		for (int i = 0 ; i < homePlayers.length ; ++i) {
			if (i > 0) {
				buffer.append(PLAYER_SEPARATOR);
			}
			buffer.append(homePlayers[i].getString());
		}
		buffer.append(NEWLINE);
		for (int i = 0 ; i < visitorPlayers.length ; ++i) {
			if (i > 0) {
				buffer.append(PLAYER_SEPARATOR);
			}
			buffer.append(visitorPlayers[i].getString());
		}
		buffer.append(NEWLINE);
	}


}
