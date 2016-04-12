/**
* PrefixTree.java
*
* @author John Abraham
*/

class PrefixTree {

	private TreeNode root;

	public PrefixTree() {
		root = new TreeNode();
	}

	public void insert(String dicWord)
	{
		TreeNode auxVar = root;
		for (char c : dicWord.toCharArray())
		{
			TreeNode child = auxVar.traverse(c);
			if (child == null)
				auxVar = auxVar.addEdge(c);
			else
				auxVar = child;
		}
		auxVar.setKey(dicWord);
	}

	public boolean search(String words)
	{
			TreeNode auxVar = root;
			for(char c : words.toCharArray())
			{
				TreeNode child = auxVar.traverse(c);
				if(child == null) return false;
				auxVar = child;
			}
			if(auxVar.getKey() == null)
				return false;
			else
				return true;
	}
		
	public boolean isPrefix(String wordPrefix)
	{
		TreeNode auxVar = root;
		for (char c : wordPrefix.toCharArray())
		{
			TreeNode child = auxVar.traverse(c);
			if (child == null)
				return false;
			else
				auxVar = child;
		}
		return true;
	}
}