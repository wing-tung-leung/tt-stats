package com.tung;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class Player {
	
	private static final String STORE_NAME = "PLAYER_RECORD_STORE";
	
	static public String find(final long id) {
		RecordStore store = null;
		try {
			store = RecordStore.openRecordStore(STORE_NAME, true);
			for (int i = 0 ; i < store.getNumRecords() ; ++i) {
				byte[] record = store.getRecord(i);
				if (record == null) continue;
				
				String content = new String(record); 
				int separatorPosition = content.indexOf(':');
				if (separatorPosition < 0) continue;
				
				String currentId = content.substring(0, separatorPosition);
				if (currentId.equals(Long.toString(id))) {
					String name = content.substring(separatorPosition + 1);
					return name;
				}
			}
			return null;
			
		} catch (RecordStoreException e) {
			e.printStackTrace();
			return null; 
			
		} finally {
			try {
				store.closeRecordStore();
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates new player entry.
	 * @return record ID
	 */
	static public int add(long id, String name) {
		RecordStore store = null;
		try {
			store = RecordStore.openRecordStore(STORE_NAME, true);
			StringBuffer content = new StringBuffer(Long.toString(id));
			content.append(':').append(name.trim());
			byte[] buffer = content.toString().getBytes();
			int recordId = store.addRecord(buffer, 0, buffer.length);
			return recordId;
			
		} catch (RecordStoreException e) {
			e.printStackTrace();
			return -1;
			
		} finally {
			try {
				store.closeRecordStore();
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}
	}

}
