package com.tung;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 * Message sender service. Separate thread because network IO should not block. 
 */
public class TextSender extends Thread {
	
	final private String content;
	final private String phoneNumber;
	final private Display display;
	
	public TextSender(Display display, String content, String phoneNumber) {
		this.display = display;
		this.content = content;
		this.phoneNumber = phoneNumber;
	}
	
	public void run() {
		MessageConnection connection = null;
		try {
			connection = (MessageConnection) Connector.open("sms://" + phoneNumber);
			TextMessage msg = (TextMessage) connection.newMessage(MessageConnection.TEXT_MESSAGE);
			msg.setPayloadText(content);
			connection.send(msg);
			Alert okMessage = new Alert("Message sent");
			display.setCurrent(okMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
			Alert error = new Alert(e.getMessage());
			display.setCurrent(error);
			
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				// silently ignore
			}
		}
	}

}
