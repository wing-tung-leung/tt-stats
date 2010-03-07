package com.tung;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDletStateChangeException;

public class EnterResultForm extends Form implements CommandListener {

	private Command exitCommand;
	
	private TextField[] input;
	private Display display;
	
	private Result result;

	private Main main;
	
	public EnterResultForm(Display display, Result result, Main main) {
		super("Enter result");
		this.display = display;
		this.result = result;
		this.main = main;
		
		exitCommand = new Command("Exit", Command.EXIT, 1);
		
		input = new TextField[10];
		for (int i = 0; i < input.length; ++i) {
			input[i] = new TextField(Integer.toString(i + 1), null, 2,
					TextField.NUMERIC);
			input[i].setLayout(Item.LAYOUT_2);
			append(input[i]);
		}
		
		addCommand(exitCommand);
		addCommand(main.navigation.showSummary);
		addCommand(main.navigation.saveCommand);
		addCommand(main.navigation.listCommand);
		addCommand(main.navigation.sendCommand);
		setCommandListener(this);
	}

	public void commandAction(Command cmd, Displayable disp) {
		if (cmd == main.navigation.listCommand) {
			main.resultList.refresh();
			display.setCurrent(main.resultList);
		} else if (cmd == main.navigation.showSummary) {
			storeResult();
			main.homeResultView.refresh();
			main.visitorResultView.refresh();
			display.setCurrent(main.homeResultView);

		} else if (cmd == main.navigation.saveCommand) {
			storeResult();
			display.setCurrent(main.matchInfo);

		} else if (cmd == main.navigation.sendCommand) {
			storeResult();
			if (main.matchInfo.validateInput()) {
				display.setCurrent(main.resultSender);
			} else {
				display.setCurrent(main.matchInfo);
			}
		}
	}

	private void storeResult() {
		// TODO show alert if input not valid
		for (int i = 0 ; i < input.length ; ++i) {
			String inputText = input[i].getString();
			int setsHome = Integer.parseInt(inputText.substring(0, 1));
			int setsVisitor = Integer.parseInt(inputText.substring(1, 2));
			result.setScore(i + 1, setsHome, setsVisitor);
		}
	}

}
