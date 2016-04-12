/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 1
 * 6/1/2014
 *************************************************************************/
import java.math.BigInteger;

/**
* Represents a Doubly Linked List that holds BigIntegerNode objects
*
* @author John
*
*/
public class BigIntegerLinkedList {
	private BigIntegerNode head;
	private BigIntegerNode tail;

 	/**
 	* Constructor sets the head and the tail of Linked List to null
 	*/
 	public BigIntegerLinkedList() {
 		head = null;
 		tail = null;
 	}

 	/**
 	* adds a new node to the end of the Linked List <br><br>
 	* Best Case: Big Theta (1) <br>
 	* Worst Case: Big Theta (1) <br>
 	* Pre-condition: None <br>
 	* Post-condition: None <br>
 	*
 	* @param newBigInteger The BigInteger value to store in the new node
 	*	
 	*/
 	public void addNodeAtEnd(BigInteger newBigInteger) {
 		BigIntegerNode newNode = new BigIntegerNode(newBigInteger, null, tail);
 		if (head == null) {
 			tail = newNode;
 			head = tail;
 		} else {
 			tail.setNext(newNode);
 			tail = newNode;
 		}
 	}

	/**
	* removes the first occuring instance of the given node in the Linked List <br><br>
	* Best Case: Big Theta (1) <br>
	* Worst Case: Big Theta (n) <br>
	* Pre-condition: Linked List cannot be empty when this method is called <br>
	* Post-condition None <br>
	*
	* @param item The node to delete from the Linked List
	* 
	*/
	public void removeNode(BigInteger item) {
		BigIntegerNode temp = head;
 		while (temp != null && !item.equals(temp.getData())) {
 			temp = temp.getNext();
 		}

 		if (temp != null) {
			if (temp.getPrev() != null)
				temp.getPrev().setNext(temp.getNext());
			else
				head = temp.getNext();

			if (temp.getNext() != null)
				temp.getNext().setPrev(temp.getPrev());
			else
				tail = temp.getPrev();
		}
	}

	/**
	* Removes the first node in the Linked List <br><br>
	* Best Case: Big Theta(1) <br>
	* Worst Case: Big Theta(1) <br>
	* Pre-condition: Linked List cannot be empty when this method is called <br>
	* Post-condition: None <br>
	*
	*/
	public void removeFirstNode() {
 		if (head != null) {
 			head = head.getNext();
 		}
	}

	/**
	* returns the BigIntegerNode object at a given index (starting with index 0) <br><br>
	* Best Case: Big Theta (1) <br>
	* Worst Case: Big Theta (n) <br>
	* Pre-condition: Linked List cannot be empty <br>
	* Post-condition: None <br>
	*
	* @param index Location of the requested element in the Linked List to return
	* @return BigIntegerNode object to return if node is found
	*
	*/
	public BigIntegerNode getNodeAt(int index) {
		if(head == null) {
			return null;
		}

		BigIntegerNode temp = head;
		for (int i=0; i<index; i++) {
			temp = temp.getNext();
			if (temp == null) {
				return null;
			}
		}
		return temp;
	}

	/**
	* counts the number of elements in the Linked List <br><br>
	* Best Case:  Big Theta (n) <br>
	* Worst Case: Big Theta (n) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @return Number of elements in the Linked List
	*
	*/
	public int countNodes() {
		int count = 0;
		if(head == null) {
			return count;
		} else {
			BigIntegerNode temp = head;
			while(temp != null) {
				temp = temp.getNext();
				count++;
			}
		}
		return count;
	}

	/**
	* checks if the list is empty or contains one or more elements <br><br>
	* Best Case: Big Theta (1) <br>
	* Worst Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @return True if the list is empty and false otherwise
	*
	*/
	public boolean isEmpty() {
		return head == null;
	}

	/**
	* Reverses the Linked List <br><br>
	* Best Case: Big Theta(n) <br>
	* Worst Case: Big Theta(n) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	*/
 	public void reverse() {
		if (head == null) 
			return;

		BigIntegerNode previous = head;
		BigIntegerNode current = head.getNext();
		head.setNext(null);
		while(current != null)
		{
			BigIntegerNode next = current.getNext();
			current.setNext(previous);
			previous = current;
			current = next;
		}
		head = previous;
 	}

	/**
	* @return String representation of the Linked List
	*
	*/
	@Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        BigIntegerNode temp = head;

        if (temp == null) {
            buffer.append("none");
            return buffer.toString();
        }

        do {
            buffer.append(temp.getData()).append(", ");
        } while ((temp = temp.getNext()) != null);
        buffer.deleteCharAt(buffer.length() - 2);
        return buffer.toString();
    }
}