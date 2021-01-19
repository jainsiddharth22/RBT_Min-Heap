


import java.util.ArrayList;
import java.util.List;

public class Command {
	int commandCounter;
	String command;
	List<Integer> values;
	
	public Command() {
		this.values = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Command [commandCounter=" + commandCounter + ", command=" + command + ", values=" + values + "]";
	}
	
	
	
}
