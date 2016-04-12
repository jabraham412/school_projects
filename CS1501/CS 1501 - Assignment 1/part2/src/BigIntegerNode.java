/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 1
 * 6/1/2014
 *************************************************************************/
import java.math.BigInteger;

/**
* Represents a Doubly Linked List node that holds BigInteger objects
*
* @author John
*
*/
public class BigIntegerNode {
	private BigInteger data;
	private BigIntegerNode next;
	private BigIntegerNode prev;

	/**
	* constructor with no parameters creates a node with everything set to null
	*
	*/
	public BigIntegerNode() {
		data = null;
		next = null;
		prev = null;
	}

	/**
	* constructor with one parameter sets the data to a BigInteger and sets
	* references to next node and previous node to null
	*
	* @param data: A BigInteger
	*
	*/
	public BigIntegerNode(BigInteger data) {
		this (data, null, null);
	}

	/**
	* constructor with three parameters data of a BigInteger, reference to
	* the next node, and a reference to the previous node.
	*
	* @param data A BigInteger
	* @param next Reference to next node in Linked List
	* @param prev Reference to previous node in Linked List
	*
	*/
	public BigIntegerNode(BigInteger data, BigIntegerNode next, BigIntegerNode prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	/**
	* sets a BigInteger type value to the node <br><br>
	* Best Case: Big Theta (1) <br>
	* Worst Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param data BigInteger value to set to to this node
	*/
	public void setData(BigInteger data) {
		this.data = data;
	}

	/**
	* sets the reference to the next node in the Linked List <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param next Reference pointer of type BigIntegerNode to next node
	*/
	public void setNext(BigIntegerNode next) {
		this.next = next;
	}

	/**
	* sets the reference to the previous node in the Linked List <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param prev Reference pointer of type BigIntegerNode to previous node
	*
	*/
	public void setPrev(BigIntegerNode prev) {
		this.prev = prev;
	}

	/**
	* returns the value of the BigInteger data stored in the node <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @return Value of the BigInteger value stored in the node
	*
	*/
	public BigInteger getData() {
		return data;
	}

	/**
	* returns the next node in the Linked List that this node is referring to <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @return The reference pointer to the next node in the linked List
	*
	*/
	public BigIntegerNode getNext() {
		return next;
	}

	/**
	* returns the previous node in the Linked List that this node is reffering to <br><br>
	* Best Case: Big Theta (1) <br>
	* Wost Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @return The reference pointer the previous node in the Linked List
	*
	*/
	public BigIntegerNode getPrev() {
		return prev;
	}	
}