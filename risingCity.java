

import java.io.IOException;
import java.util.Queue;

public class risingCity {
	
	static RisingCityRecords risingCityRecords;

	static Queue<Command> commands;
	
	//global counter
	static int counter = 0;

	public static void main(String[] args) throws ADSException, IOException{
		FileReader reader = new FileReader(args[0]);
		commands = reader.getCommands();

		risingCityRecords = new RisingCityRecords();

		while(true) {
			Command c = commands.peek();
			
			if(c!=null) {
					if(c.commandCounter==counter){
						if(c.command.equals("Insert")) {
							risingCityRecords.insert(new BuildingRecord(c.values.get(0),0,c.values.get(1)));
							commands.poll();
						}else {
							if(c.values.size()>1) {
								risingCityRecords.printBuilding(c.values.get(0),c.values.get(1));
							}else {
								risingCityRecords.printBuilding(c.values.get(0));
							}
							commands.poll();
						}
						c = commands.peek();
					}
				if(risingCityRecords.minHeap.recordList.isEmpty() && RisingCityRecords.localCounter==0) {
					//if we have no buildings to be worked on but there are still commands in the queue then 
					//update the global counter to the counter of the next command
					counter = commands.peek().commandCounter;
				}else {
					//If a print command is happens to be on the same day as a building is getting over
					//then execute the command first
					if(c!=null) {
						counter++;
						if(c.commandCounter==counter && !c.command.equals("Insert")) {
							boolean x=risingCityRecords.checker();
							risingCityRecords.work(x, c);
							if(x) {
								commands.poll();
							}
						}else {
							risingCityRecords.work(false, c);
						}
					}else {
						break;
					}
				}				
			}else {
				//All commands have been executed
				break;
			}
			
		}
		
		//Work on the remaining buildings on the heap
		
		while(risingCityRecords.mh!=null) {
			counter++;
			risingCityRecords.work(false, null);
		}

	}
}
