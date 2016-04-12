/**
* TreeNode.java
*
* @author John Abraham
*/

import java.util.SortedMap;
import java.util.TreeMap;

class TreeNode {

	private String key;
	private SortedMap<Character, TreeNode> edges;

	TreeNode addEdge(char c)
	{
			if (edges == null)
				edges = new TreeMap<Character, TreeNode>();

			TreeNode childNode = new TreeNode();
			edges.put(c, childNode);
			return childNode;
	}

	TreeNode traverse(char c)
	{
		if(edges == null)
			return null;
		else
			return edges.get(c);
	}

	void setKey(String key)
	{
		this.key = key;
	}
	
	String getKey() {
		return key;
	}
}