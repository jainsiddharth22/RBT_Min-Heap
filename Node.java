

public class Node {
    int buildingNum;           // key
	int executed_time;
	int total_time;       // associated data
    Node left, right;  // links to left and right subtrees
    boolean color;     // color of parent link
    int size;          // subtree count

    public Node(int buildingNum,int executed_time, int total_time, boolean color, int size) {
        this.buildingNum = buildingNum;
        this.executed_time = executed_time;
        this.total_time = total_time;
        this.color = color;
        this.size = size;
    }

	@Override
	public String toString() {
		return "Node1 [buildingNum=" + buildingNum + ", executed_time=" + executed_time + ", total_time=" + total_time
				+ ", left=" + left + ", right=" + right + "]";
	}
}
