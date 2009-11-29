package com.tung;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class ResultSender extends Form implements CommandListener {
	
	private TextField destination;
	private Command sendCommand;
	
	private Result result;
	
	public ResultSender(Result result) {
		super("Send result");
		this.result = result;
		
		destination = new TextField("Destination", null, 15, TextField.NUMERIC);
		append(destination);
		
		sendCommand = new Command("Send", Command.SCREEN, 1);
		addCommand(sendCommand);
		setCommandListener(this);
	}

	private void sendResult() {
		MessageConnection connection = null;
		try {
			String phoneNumber = destination.getString().trim();
			connection = (MessageConnection) Connector.open("sms://" + phoneNumber);
			TextMessage msg = (TextMessage) connection.newMessage(MessageConnection.TEXT_MESSAGE);
			msg.setPayloadText(buildMessage());
			connection.send(msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String buildMessage() {
		StringBuffer m = new StringBuffer(160);
		m.append("test result: " + result.getSetTotals());
		return m.toString();
	}

	public void commandAction(Command cmd, Displayable displayable) {
		if (cmd == sendCommand) {
			if (destination.getString().trim().length() > 3) { 
				sendResult();
			}
		}
		
	}

}
