



import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Queue;

public class RisingCityRecords {
	
	//This custom data structure primarily consists of a min heap and a red black tree
	//mh points to the building record currently being worked on and rh points to the corresponding node in the red black tree
	//A hashmap is used to check for duplicate entries
	//local counter 
	
	static int localCounter=0;
	BuildingRecord mh;
	Node rb;
	MinHeap minHeap;
	RedBlackBST redBlackBST;
	HashMap<Integer,Boolean> hashMap;
	
	public RisingCityRecords() {
		this.mh = null;
		this.rb = null;
		this.minHeap = new MinHeap();
		this.redBlackBST = new RedBlackBST();
		this.hashMap = new HashMap<>();
	}
	
	//inserts the new building record in the min heap and the red black tree and throws an exception if a record with the same building
	//was inserted previously
	public void insert(BuildingRecord br) throws ADSException {
		if(this.hashMap.get(br.getBuildingNum())==null) {
			this.hashMap.put(br.getBuildingNum(), true);
			this.minHeap.insert(br);
			this.redBlackBST.put(br.getBuildingNum(), br.getExecuted_time(),br.getTotal_time());
		}else {
			throw new ADSException("Duplicate Building Number");
		}
	}
	
	//Updates the pointers and exits the program if all the buildings are completed
	public void update() throws ADSException {
		if(this.minHeap.recordList.isEmpty() && mh.getExecuted_time()==mh.getTotal_time()) {
			System.exit(1);
		}else {
			this.mh = this.minHeap.removeMin();
			this.rb = this.redBlackBST.getNode(mh.getBuildingNum());
		}
	}
	
	//returns true if the executed time of the building being worked on is 1 less than it's total time
	public boolean checker() {
		if(mh.getExecuted_time()+1==mh.getTotal_time()) {
			return true;
		}
		return false;
	}
	
	//performs a day's work on the current building
	public void work(boolean x,Command c) throws ADSException, IOException {
		if(localCounter==0) {
			//Update the building to be worked on
			this.update();
		}
		
		int t = mh.getExecuted_time();
		mh.setExecuted_time(t+1);
		rb.executed_time=t+1;
		localCounter++;
		
		if(mh.getExecuted_time()==mh.getTotal_time()) {
			
			//If a print command happens to be on the same time as the current building is getting completed
			//then execute the command first
			if(x) {
				if(c.values.size()>1) {
					printBuilding(c.values.get(0),c.values.get(1));
				}else {
					printBuilding(c.values.get(0));
				}
			}
			
			//print the building number of the completed building and the time it got completed and delete the record from the red black tree subsequently
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			sb.append(mh.getBuildingNum());
			sb.append(",");
			sb.append(risingCity.counter);
			sb.append(")");
			PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));
			writer.println(sb.toString());
			writer.close();

			redBlackBST.delete(mh.getBuildingNum());
			
			localCounter=0;
		}
		
		if(localCounter==5) {
			//We have worked on the current building for 5 days so insert it back on the heap
			localCounter=0;
			minHeap.insert(mh);
		}

	}
	
	//prints the building record with the given building number onto the output file
	public void printBuilding(int buildingNum) throws IOException {
		BuildingRecord br = this.redBlackBST.get(buildingNum);
		
		//If no building record exists with the given building number then print (0,0,0) onto the output file
		if(br==null) {
			PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));
			writer.println("(0,0,0)");
			writer.close();
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(br.getBuildingNum());
		sb.append(",");
		sb.append(br.getExecuted_time());
		sb.append(",");
		sb.append(br.getTotal_time());
		sb.append(")");
		
		PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));
		writer.println(sb.toString());
		writer.close();

	}
	
	//prints the building records with building numbers in the given range onto the file
	public void printBuilding(int buildingNum1,int buildingNum2) throws IOException {
		Queue<BuildingRecord> l = this.redBlackBST.keys(buildingNum1, buildingNum2);
		int s = l.size();
		StringBuilder sb = new StringBuilder();
		
		//if no building records exist in the given range, print (0,0,0) onto the output file
		if(l.size()==0) {
			PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));
			sb.append("(0,0,0)");
			writer.println(sb.toString());
			writer.close();
			return;
		}
		
		int c=0;
		while(c<s) {
			BuildingRecord br = l.poll();
			sb.append("(");
			sb.append(br.getBuildingNum());
			sb.append(",");
			sb.append(br.getExecuted_time());
			sb.append(",");
			sb.append(br.getTotal_time());
			sb.append(")");
			if(c!=s-1) {
				sb.append(",");
			}
			c++;
		}
		
		PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true));
		writer.println(sb.toString());
		writer.close();

	}
}
