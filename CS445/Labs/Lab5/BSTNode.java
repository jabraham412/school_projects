public class BSTNode
{
	Comparable data;
	BSTNode left,right;

	public BSTNode( Comparable data, BSTNode left, BSTNode right )
	{
		this.data = data;
		this.left = left;
		this.right=right;
	}

	public BSTNode getLeft()
	{
		return left;
	}

	public BSTNode getRight()
	{
		return right;
	}


	public void setRight( BSTNode right)
	{
		this.right = right;
	}

	public void setLeft( BSTNode left)
	{
		this.left = left;
	}

	public Comparable getData()
	{
		return data;
	}

	public void setData( Comparable data)
	{
		this.data = data;
	}
}
