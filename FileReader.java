


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class FileReader {
	String filename;
	Command cmd;
	Queue<Command> commands;
	
	public FileReader(String filename) {
		this.filename = filename;

		cmd = null;

		commands = new LinkedList<Command>();
	}
	
	//returns a queue consisting of all the commands from the file
	public Queue<Command> getCommands() {
		File file = new File(filename);
		Scanner reader;

		try {
			reader = new Scanner(file);

			while (reader.hasNext()) {
				String s = reader.nextLine();
				
				cmd = new Command();
				
				int colonIndex = s.indexOf(":");
				cmd.commandCounter = Integer.parseInt(s.substring(0, colonIndex));;
				
				String command = s.substring(colonIndex+2, s.indexOf('('));
				cmd.command = command;
						
				String[] values = s.substring(s.indexOf('(')+1, s.indexOf(')')).split(",");
				for(String str : values) {
					cmd.values.add(Integer.parseInt(str));
				}

				commands.add(cmd);

			}

			reader.close();
		} catch (FileNotFoundException e) {
			// If the file isn't found, it prints and error message and exits the program.
			System.err.println("Error: File not found.");
			System.exit(1);
		}

		return commands;
	}
}
