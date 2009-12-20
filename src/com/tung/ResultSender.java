package com.tung;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

public class ResultSender extends Form implements CommandListener {
	
	private Display display;
	
	private TextField destination;
	
	private Main main;
	
	public ResultSender(Display display, Result result, Main main) {
		super("Send result");
		this.display = display;
		this.main = main;
		
		destination = new TextField("Destination", null, 15, TextField.NUMERIC);
		append(destination);
		
		addCommand(main.navigation.sendCommand);
		addCommand(main.navigation.editResult);
		
		setCommandListener(this);
	}

	private void sendResult() {
		String phoneNumber = destination.getString().trim();
		String content = buildMessage();
		System.out.println("message_size=" + content.length());
		System.out.println(content);
		TextSender sender = new TextSender(display, content, phoneNumber);
		sender.start();
	}

	private String buildMessage() {
		StringBuffer buffer = new StringBuffer(160);
		main.matchInfo.dump(buffer);
		main.result.dump(buffer);
		return buffer.toString();
	}

	public void commandAction(Command cmd, Displayable displayable) {
		if (cmd == main.navigation.sendCommand) {
			if (destination.getString().trim().length() > 3) { 
				sendResult();
			}
		} else if (cmd == main.navigation.editResult) {
			display.setCurrent(main.enterResultForm);
		}
		
	}

}
