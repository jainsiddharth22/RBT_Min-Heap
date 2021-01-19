


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap {
	List<BuildingRecord> recordList;

	public MinHeap() {
		this.recordList = new ArrayList<>();
	}
	
	//inserts the new building record in the heap
	public boolean insert(BuildingRecord br) {
		
		//if heap is empty, merely add the record to the array and return
		if(recordList.isEmpty()) {
			recordList.add(br);
			return true;
		}
		recordList.add(br);
		
		int c = recordList.size()-1;
		int p = (c-1)/2;
		
		while(p>=0) {
			if(recordList.get(c).getExecuted_time()<recordList.get(p).getExecuted_time()) {
				//if child's executed time is less than parent then the child will have higher priority
				Collections.swap(recordList, c, p);
				c=p;
				p=(c-1)/2;
			}else if(recordList.get(c).getExecuted_time()==recordList.get(p).getExecuted_time()) {
				//if child's executed time is equal to that of the parent's, then the one with the lower building number will have higher priority
				if(recordList.get(c).getBuildingNum()<recordList.get(p).getBuildingNum()) {
					Collections.swap(recordList, c, p);
					c=p;
					p=(c-1)/2;
				}else {
					break;
				}
			}else {
				break;
			}
		}
		
		return true;
	}
	
	//removes the building record with the highest priority depending on executed time and building number
	public BuildingRecord removeMin() {
		if(recordList.isEmpty()) {
			return null;
		}
		
		//To remove the first record, swap it with the last record of the array and then remove it and perform heapify subsequently
		Collections.swap(recordList, 0, recordList.size()-1);
		
		BuildingRecord r = recordList.remove(recordList.size()-1);
		
		int p = 0;
		int c1 = 2*p+1;
		int c2 = 2*p+2;
		
		while(c1<recordList.size()) {
			if(c2<recordList.size()) {
				//if both children exist
				if(recordList.get(c2).getExecuted_time()<recordList.get(c1).getExecuted_time()) {
					//if child 2 has less executed time than child 1
					if(recordList.get(p).getExecuted_time()>recordList.get(c2).getExecuted_time()) {
						//if parent has higher executed time than child 2 then child 2 will have higher priority
						Collections.swap(recordList, p, c2);
						p=c2;
						c1 = 2*p+1;
						c2 = 2*p+2;
					}else if(recordList.get(p).getExecuted_time()==recordList.get(c2).getExecuted_time()) {
						//if parent and child 2 have the same executed time
						if(recordList.get(c2).getBuildingNum()<recordList.get(p).getBuildingNum()) {
							//if parent's building number is greater than child 2's building number then child 2 will have higher priority
							Collections.swap(recordList, p, c2);
							p=c2;
							c1 = 2*p+1;
							c2 = 2*p+2;
						}else {
							break;
						}
					}else {
						break;
					}
				}else if(recordList.get(c2).getExecuted_time()>recordList.get(c1).getExecuted_time()) {
					//if child 1 has less executed time than child 2
					if(recordList.get(p).getExecuted_time()>recordList.get(c1).getExecuted_time()) {
						//if parent has higher executed time than child 1 then child 1 will have higher priority
						Collections.swap(recordList, p, c1);
						p=c1;
						c1 = 2*p+1;
						c2 = 2*p+2;
					}else if(recordList.get(p).getExecuted_time()==recordList.get(c1).getExecuted_time()) {
						//if parent and child 1 have the same executed time
						if(recordList.get(c1).getBuildingNum()<recordList.get(p).getBuildingNum()) {
							//if parent's building number is greater than child 1's building number then child 1 will have higher priority
							Collections.swap(recordList, p, c1);
							p=c1;
							c1 = 2*p+1;
							c2 = 2*p+2;
						}else {
							break;
						}
					}else {
						break;
					}
				}else {
					//if both the children has equal executed times
					if(recordList.get(c1).getBuildingNum()>recordList.get(c2).getBuildingNum()) {
						//if child 2 has lower building number than child 1
						if(recordList.get(p).getExecuted_time()>recordList.get(c2).getExecuted_time()) {
							//if parent's executed time is greater than child 2 then child 2 will have higher priority
							Collections.swap(recordList, p, c2);
							p=c2;
							c1 = 2*p+1;
							c2 = 2*p+2;
						}else if(recordList.get(p).getExecuted_time()==recordList.get(c2).getExecuted_time()) {
							//if parent's executed time is equal to that of child 2
							if(recordList.get(p).getBuildingNum()>recordList.get(c2).getBuildingNum()) {
								//if parent's building number is greater than child 2 then child 2 will have higher priority
								Collections.swap(recordList, p, c2);
								p=c2;
								c1 = 2*p+1;
								c2 = 2*p+2;
							}else {
								break;
							}
						}else {
							break;
						}
					}else {
						if(recordList.get(p).getExecuted_time()>recordList.get(c1).getExecuted_time()) {
							//if parent's executed time is greater than child 1 then child 1 will have higher priority
							Collections.swap(recordList, p, c1);
							p=c1;
							c1 = 2*p+1;
							c2 = 2*p+2;
						}else if(recordList.get(p).getExecuted_time()==recordList.get(c1).getExecuted_time()) {
							//if parent's executed time is equal to that of child 2
							if(recordList.get(p).getBuildingNum()>recordList.get(c1).getBuildingNum()) {
								//if parent's building number is greater than child 2 then child 2 will have higher priority
								Collections.swap(recordList, p, c1);
								p=c1;
								c1 = 2*p+1;
								c2 = 2*p+2;
							}else {
								break;
							}
						}else {
							break;
						}
					}
				}
			}else if(c1<recordList.size()) {
				//only child 1 exists
				if(recordList.get(c1).getExecuted_time()<recordList.get(p).getExecuted_time()) {
					//if parent's executed time is higher than child 1 then child 1 will have higher priority
					Collections.swap(recordList, p, c1);
					p=c1;
					c1 = 2*p+1;
					c2 = 2*p+2;
				}else if(recordList.get(c1).getExecuted_time()==recordList.get(p).getExecuted_time()) {
					//if parent and child 1 have equal executed times
					if(recordList.get(c1).getBuildingNum()<recordList.get(p).getBuildingNum()) {
						//if parent's building number is higher than child 1 then child 1 will have higher priority
						Collections.swap(recordList, p, c1);
						p=c1;
						c1 = 2*p+1;
						c2 = 2*p+2;
					}else {
						break;
					}
				}else {
					break;
				}
			}else {
				break;
			}
		}
		
		return r;
	}

	@Override
	public String toString() {
		return "MinHeap [recordList=" + recordList + "]";
	}
	
	
	
}
