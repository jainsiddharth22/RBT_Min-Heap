

import java.util.LinkedList;
import java.util.Queue;

public class RedBlackBST {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    Node root;

    public RedBlackBST() {
    	
    }

    // is Node x red; false if x is null 
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of Node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 

    //returns the number of building records in the tree
    public int size() {
        return size(root);
    }

    //checks if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }


    //returns the node with the given building number
	public Node getNode(int buildingNum) {
		return getNode(root,buildingNum);
	}
    
	private Node getNode(Node x,int buildingNum) {
		while(x!=null) {
			if(buildingNum<x.buildingNum) {
				x=x.left;
			}else if(buildingNum>x.buildingNum) {
				x=x.right;
			}else {
				return x;
			}
		}
		return null;
	} 
    
	//returns the building record with the given building number
    public BuildingRecord get(int buildingNum) {
        return get(root, buildingNum);
    }

    //returns the building record with the given building number from the subtree with root x
    private BuildingRecord get(Node x, int buildingNum) {
        while (x != null) {
        	if(buildingNum<x.buildingNum) {
        		x = x.left;
        	}else if(buildingNum>x.buildingNum) {
        		x = x.right;
        	}else {
        		 return new BuildingRecord(x.buildingNum,x.executed_time,x.total_time);
        	}
        }
        return null;
    }

    //checks if the tree contains a building record with the given building number
    public boolean contains(int buildingNum) {
        return get(buildingNum) != null;
    }

    //inserts the new building record
    public void put(int buildingNum, int executed_time,int total_time) {
        root = put(root, buildingNum, executed_time,total_time);
        root.color = BLACK;
    }

    // insert the building record in the subtree with root h
    private Node put(Node h, int buildingNum, int executed_time,int total_time) { 
        if (h == null) return new Node(buildingNum, executed_time,total_time, RED, 1);
        
        if(buildingNum<h.buildingNum) {
        	h.left  = put(h.left,  buildingNum, executed_time,total_time); 
        }else if(buildingNum>h.buildingNum) {
        	h.right = put(h.right, buildingNum, executed_time, total_time); 
        }else {
        	h.executed_time   = executed_time;
        	h.total_time = total_time;
        }

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

    //delete the building record with the minimum key
    public void deleteMin() throws ADSException {
        if (isEmpty()) throw new ADSException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    // delete the building record with the minimum key in subtree rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    //delete the building record with the given building number
    public void delete(int buildingNum) { 
        if (!contains(buildingNum)) return;

        // if both children of root are black, root should be red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, buildingNum);
        if (!isEmpty()) root.color = BLACK;
    }

    // delete the building record with the given building number in the subtree with root h
    private Node delete(Node h, int buildingNum) { 

        if (buildingNum<h.buildingNum)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, buildingNum);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (buildingNum==h.buildingNum && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (buildingNum==h.buildingNum) {
                Node x = min(h.right);
                h.buildingNum = x.buildingNum;
                h.executed_time = x.executed_time;
                h.total_time = x.total_time;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, buildingNum);
        }
        
        return balance(h);
    }

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a Node and its two children
    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree
    private Node balance(Node h) {

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    //returns the smallest building number
    public int min() throws ADSException {
        if (isEmpty()) throw new ADSException("calls min() with empty symbol table");
        return min(root).buildingNum;
    } 

    //returns the node with the smallest key in subtree with root at x
    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    //returns the largest building number
    public int max() throws ADSException {
        if (isEmpty()) throw new ADSException("calls max() with empty symbol table");
        return max(root).buildingNum;
    } 

    //returns the node with the largest building number in the subtree with root at x
    private Node max(Node x) { 
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 

    //return the number of building records with building number less the given building number
    public int rank(int buildingNum) {
        return rank(buildingNum, root);
    } 

    //returns the number of building records less than building number in the subtree rooted at x
    private int rank(int buildingNum, Node x) {
        if (x == null) return 0; 
        
        if(buildingNum<x.buildingNum) {
        	return rank(buildingNum, x.left); 
        }else if(buildingNum>x.buildingNum) {
        	return 1 + size(x.left) + rank(buildingNum, x.right); 
        }else {
        	return size(x.left); 
        }
    } 

    //Returns all building records in the given range
    public Queue<BuildingRecord> keys(int low, int high) {
        Queue<BuildingRecord> queue = new LinkedList<BuildingRecord>();
        keys(root, queue, low, high);
        return queue;
    } 

    // add the building records between building numbers low and high in the subtree rooted at x
    // to the queue
    private void keys(Node x, Queue<BuildingRecord> queue, int low, int high) { 
        if (x == null) return; 
        
        if(low<x.buildingNum) {
        	keys(x.left, queue, low, high);
        }
        if(low<=x.buildingNum && high>=x.buildingNum) {
        	queue.add(new BuildingRecord(x.buildingNum,x.executed_time,x.total_time)); 
        }
        if(high>x.buildingNum) {
        	keys(x.right, queue, low, high); 
        } 
    } 

    //Return the number of building records in the given range   
    public int size(int low, int high) {
    	
    	if(low>high) {
    		return 0;
    	}
    	if(contains(high)) {
    		return rank(high) - rank(low) + 1;
    	}else {
    		return rank(high) - rank(low);
    	}

    }
}
