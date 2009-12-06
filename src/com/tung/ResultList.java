package com.tung;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class ResultList extends List implements CommandListener {

	private Display display;
	private Main main;
	
	private Command refreshCommand;

	public ResultList(Display display, Main main) {
		super("Match list", List.EXCLUSIVE);
		
		this.display = display;
		this.main = main;
		
		refreshCommand = new Command("Refresh", Command.SCREEN, 1);
		
		addCommand(refreshCommand);
		addCommand(main.editResult);
		setCommandListener(this);
	}

	public void refresh() {
		this.deleteAll();
		
		RecordStore store = null;
		try {
			String storeNames[] = RecordStore.listRecordStores();
			for (int i = 0 ; i < storeNames.length ; ++i) {
				String storeName = storeNames[i];
				store = RecordStore.openRecordStore(storeName, false);
				String title = new String(store.getRecord(1));
				store.closeRecordStore();
				store = null;
				append(title, null);
			}
			
		} catch (RecordStoreFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (store != null) {
				try {
					store.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void commandAction(Command cmd, Displayable arg1) {
		if (cmd == refreshCommand) {
			refresh();
			
		} else if (cmd == main.editResult) {
			display.setCurrent(main.enterResultForm);
		}

	}

}
