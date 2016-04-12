/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 5
 * 34/7/2014
 *************************************************************************/
public class AdaptiveHuffmanTree {
	Node[] rlo;		//list of nodes in the tree in reverse-level order rlo[255], rlo[254], ...
	Node NYT;		//special node representing unseen characters
	int size;		//number of Nodes in the tree

    public AdaptiveHuffmanTree() {
        rlo = new Node[256];

        for(int k=0; k<256; k++){
            rlo[k] = null;
        }

        NYT = new Node(0);
        NYT.place  = 255;		//NYT is going to be placed at the root of the tree
        NYT.weight = 0;			//NYT has weight 0
        NYT.label  = '0';		//NYT is always a leftchild
        NYT.leaf   = true;		//NYT is always a leaf
        rlo[NYT.place] = NYT;	//place NYT at the root of the tree
        NYT.parent = null;		//the root of the tree has no parent
        size = 1;
    }

    public void update(char c) {
    	/*If first appearance for symbol*/
    	if ( !characterInTree(c) ) {
    		size += 2;
	    	/*create 3 nodes: NYT -> right new external node and left new NYT*/
	    	rlo[NYT.place] = NYT;
	    	rlo[NYT.place].leaf = false;

			rlo[NYT.place-1] = new Node(1, c);
			rlo[NYT.place-1].place   = NYT.place-1;
			rlo[NYT.place-1].parent  = NYT;
			rlo[NYT.place-1].label   = '1';
			rlo[NYT.place-1].leaf    = true;

			int oldNYTPos = NYT.place;
			NYT = new Node(0);	
			NYT.place  = oldNYTPos-2;
			NYT.parent = rlo[oldNYTPos];
			NYT.label  = '0';
			NYT.leaf   = true;
			rlo[oldNYTPos-2] = NYT;

			/*increment weights of parent nodes of new char*/
			/*swap if siblings or higher order properties are not satisfied*/
			Node currNode = NYT.parent;
			while (currNode != root()) {
				int i;
				boolean flag = false;
				for(i = 254; i >= 0; i--) {
					if(rlo[i] != null && rlo[i] != currNode.parent && rlo[i].place > currNode.place && rlo[i].weight == currNode.weight) {
						flag = true;
						break;
					}
				}
				if(flag == true) {
					swap(currNode.place, i);
					currNode.weight++;
					currNode = rlo[i].parent;
				} else {
					currNode.weight++;
					currNode = currNode.parent;
				}
			}

		} else {
    		/*Go to the symbol in the tree*/
    		int repetedChar;
			for (repetedChar = 255; repetedChar >= 0; repetedChar--) {
				if (rlo[repetedChar] != null && rlo[repetedChar].character == c) {
					break;
				}
			}

			/*increment the weights of all of its parents to the root*/
			/*swap if siblings or higher order properties are not satisfied*/
			Node currNode = rlo[repetedChar];
			while (currNode != root()) {
				int i;
				boolean flag = false;
				for(i = 254; i >= 0; i--) {
					if(rlo[i] != null && rlo[i] != currNode.parent && rlo[i].place > currNode.place && rlo[i].weight == currNode.weight) {
						flag = true;
						break;
					}
				}
				if(flag == true) {
					swap(currNode.place, i);
					currNode.weight++;
					currNode = rlo[i].parent;
				} else {
					currNode.weight++;
					currNode = currNode.parent;
				}
			}
		}
    }

    private void swap(int u, int v) {
    	Node temp1       = rlo[u];
    	Node temp2       = rlo[v];

    	Node tempParent1 = rlo[u].parent;
    	Node tempParent2 = rlo[v].parent;

    	int tempPlace1   = rlo[u].place;
    	int tempPlace2   = rlo[v].place;

		char tempLabel1  = rlo[u].label;
		char tempLabel2  = rlo[v].label;

    	rlo[u] = temp2; rlo[v] = temp1;

		rlo[u].place  = tempPlace1;  rlo[v].place   = tempPlace2;
		rlo[u].label  = tempLabel1;  rlo[v].label   = tempLabel2;
		rlo[u].parent = tempParent1; rlo[v].parent  = tempParent2;

	}

    public boolean characterInTree(char c) {
    	Node curr = rlo[255];
    	while (curr != NYT) {
    		if (curr != null && curr.character == c)
    			return true;
    		curr = rlo[curr.place -1];
    	}
    	return false;
    }

	/*
	* Return the sequence of labels (characters) from the root to the NYT node.
	*/
	public StringBuffer getCodeWordForNYT(){
		StringBuffer s = new StringBuffer("");
		Node currNode = NYT;
		while (currNode.parent != null) {
			s.append(currNode.label);
			currNode = currNode.parent;
		}
		s.reverse();
		return s;
	}


	/*
	* Return the sequence of labels (characters from the root to the Node for character c.
	*/
	public StringBuffer getCodeWordFor(char c){
		Node cNode = null;
		for (int i=255; i>=0; i--) {
			if (rlo[i] != null && rlo[i].character == c)
				cNode = rlo[i];
		}

		StringBuffer s = new StringBuffer("");
		Node currNode = cNode;
		while (currNode.parent != null) {
			s.append(currNode.label);
			currNode = currNode.parent;
		}
		s.reverse();
		return s;
	}


	/*
	* return the reference to the root node.
	*/
    public Node root() {

    	return rlo[255];
    }

    public int size() {

    	return size;
    }

	public String toString() {
		String result = "[";

		for(int k=255; k>=0 ; k--){
			if(rlo[k] != null)
			result += "(" + rlo[k].weight
					+ "," + rlo[k].place
					+ "," + rlo[k].character
					+ ") ";
		}

		return result + "]\nsize = " + size;
	}
}