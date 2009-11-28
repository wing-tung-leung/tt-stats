package com.tung;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

public class EnterResultForm extends Form implements CommandListener {

	private Command exitCommand;
	private Command calculateCommand;
	private Command saveCommand;
	
	private TextField[] input;
	private Display display;
	
	private Result result;
	
	public EnterResultForm(Display display, Result result) {
		super("Enter result");
		this.display = display;
		this.result = result;
		
		exitCommand = new Command("Exit", Command.EXIT, 1);
		calculateCommand = new Command("Calculate", Command.SCREEN, 2);
		saveCommand = new Command("Save", Command.SCREEN, 3);
		
		input = new TextField[10];
		for (int i = 0; i < input.length; ++i) {
			input[i] = new TextField(Integer.toString(i + 1), null, 2,
					TextField.NUMERIC);
			input[i].setLayout(Item.LAYOUT_2);
			append(input[i]);
		}
		
		addCommand(exitCommand);
		addCommand(calculateCommand);
		addCommand(saveCommand);
		setCommandListener(this);
	}

	public void commandAction(Command cmd, Displayable disp) {
		if (cmd == calculateCommand) {
			storeResult();
			ResultView view = new ResultView(result);
			display.setCurrent(view);
		}
	}

	private void storeResult() {
		for (int i = 0 ; i < input.length ; ++i) {
			String inputText = input[i].getString();
			int setsHome = Integer.parseInt(inputText.substring(0, 1));
			int setsVisitor = Integer.parseInt(inputText.substring(1, 2));
			result.setScore(i + 1, setsHome, setsVisitor);
		}
	}

}
