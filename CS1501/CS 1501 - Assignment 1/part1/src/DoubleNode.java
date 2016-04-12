/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 1
 * 6/1/2014
 *************************************************************************/

/**
* Represents a node of a generic type Doubly Linked List 
*
* @author John
*
*/
public class DoubleNode<T> {
	private T data;
	private DoubleNode<T> next;
	private DoubleNode<T> prev;

	/**
	* Constructor with no parameters creates a node with everything set to null
	*
	*/
	public DoubleNode() {
		data = null;
		next = null;
		prev = null;
	}

	/**
	* Constructor with one parameter sets the data and sets references to next
	* node and previous node to null <br><br>
	*
	*@param data Element of specified type T
	*
	*/
	public DoubleNode(T data) {
		this (data, null, null);
	}

	/**
	* Constructor with three parameters data of specified type T, reference to
	* the next node, and a reference to the previous node. <br><br>
	*
	* @param data Value of specified type T
	* @param next Reference to next node in Linked List
	* @param prev Reference to previous node in Linked List
	*
	*/
	public DoubleNode(T data, DoubleNode<T> next, DoubleNode<T> prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	/**
	* Sets a value to the node of to a specified type T value <br><br>
	* Best Case: Big Theta (1) <br>
	* Worst Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param data Value of a specified type T
	*/
	public void setData( T data ) {
		this.data = data;
	}

	/**
	* Sets the reference to the next node in the Linked List <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param next Reference pointer of type DoubleNode<t> to next node
	*/
	public void setNext(DoubleNode<T> next) {
		this.next = next;
	}

	/**
	* Sets the reference to the previous node in the Linked List <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param prev Reference pointer of type DoubleNode<t> to previous node
	*
	*/
	public void setPrev(DoubleNode<T> prev) {
		this.prev = prev;
	}

	/**
	* Returns the value of the data of specified type T stored in the node <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @return Value of the data of specified type T stored in the node
	*
	*/
	public T getData() {
		return data;
	}

	/**
	* Returns the next node in the Linked List that the current node is referring to <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: the node cannot be the last element of the Linked List <br>
	* Post-condition: None <br>
	*
	* @return The reference pointer to the next node in the linked List
	*
	*/
	public DoubleNode getNext() {
		return next;
	}

	/**
	* Returns the previous node in the Linked List that the current node is reffering to <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: the node cannot be the first element in the Linked List <br>
	* Post-condition: None <br>
	*
	* @return The reference pointer the previous node in the Linked List
	*
	*/
	public DoubleNode getPrev() {
		return prev;
	}
}