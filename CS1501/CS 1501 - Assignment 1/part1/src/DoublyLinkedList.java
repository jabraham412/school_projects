/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 1
 * 6/1/2014
 *************************************************************************/

/**
* Represents a genertic type Doubly Linked List holding DoubleNode objects
*
* @author John
*
*/
 public class DoublyLinkedList<T> {
 	private DoubleNode<T> head;
 	private DoubleNode<T> tail;

 	/**
 	* Constructor sets head and tail of Linked List to null
 	*/
 	public DoublyLinkedList() {
 		head = null;
 		tail = null;
 	}

	/**
	* Adds an item of a specified type to the beginning of the list <br><br>
	* Best Case: Big Theta (1) <br>
	* Worst Case: Big Theta (1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param item Element to add to the Linked List at front
	*
	*/
	public void addCharAtFront(T item) {
		DoubleNode<T> newnode = new DoubleNode<T> (item);
		if(head == null) {
			head = newnode;
			tail = head;
		}else{
			newnode.setNext(head);
			head.setPrev(newnode);
			head = newnode;
		}
	}

	/**
	* Removes an item of a specified type from the beginning of the list <br><br>
	* Best Case: Big Theta(1) <br>
	* Worst Case: Big Theta(1) <br>
	* Pre-condition: Linked List cannot be empty when this method is called <br>
	* Post-condition: None <br>
	*
	* @return String representation of the list after removing the first element
	*
	*/
 	public String removeCharFromFront() {
 		if (head != null) {
 			head = head.getNext();
 		}

 		return toString();
 	}

	/**
	* Adds an item of a specified type to the end of the list <br><br>
	* Best Case: Big Theta(1) <br>
	* Worst Case: Big Theta(1) <br>
	* Pre-condition: None <br>
	* Post-condition: None <br>
	*
	* @param item Element to add to the Linked List at end
	*
	*/
 	public void addCharAtEnd(T item) {
		DoubleNode<T> newnode = new DoubleNode<T> (item);
		if(head == null){
			head = newnode;
			tail = head;
		}else{
			newnode.setPrev(tail);
			tail.setNext(newnode);
			tail = newnode;
		}
 	}

	/**
	* Removes an item of a specified type from the end of the list <br><br>
	* Best Case: Big Theta(1) <br>
	* Worst Case: Big Theta(1) <br>
	* Pre-condition: Linked List cannot be empty when this method is called <br>
	* Post-condition: None <br>
	*
	* @return String representation of the list after removing the last element
	*
	*/
	public String removeCharAtEnd() {
		DoubleNode<T> temp = tail;
		if (tail.getPrev() != null) {
			tail = tail.getPrev();
			tail.setNext(null);
		}else{
			head = null;
		}

		return toString();
 	}

	/**
	* Removes the first occuring node of a specified type from the list <br><br>
	* Best Case: Big Theta(1) <br>
	* Worst Case: Big Theta(n) <br>
	* Pre-condition: Linked List cannot be empty when this method is called <br>
	* Post-condition: None <br>
	*
	* @param item Element to delete from the linked List
	*
	*/
 	public void deleteChar(T item) {
 		DoubleNode<T> temp = head;
 		while (temp != null && !temp.getData().equals(item)) {
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
	* Counts the number of elements in the list <br><br>
	* Best Case: Big Theta(n) <br>
	* Worst Case: Big Theta(n) <br>
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
		}else{
			DoubleNode<T> temp = head;
			while(temp != null) {
				temp = temp.getNext();
				count++;
			}
		}
		return count;
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

		DoubleNode<T> previous = head;
		DoubleNode<T> current = head.getNext();
		head.setNext(null);
		while(current != null)
		{
			DoubleNode<T> next = current.getNext();
			current.setNext(previous);
			previous = current;
			current = next;
		}
		head = previous;
 	}

	/**
	* Checks if the list is empty or contains one or more elements <br><br>
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
	* @return String representation of the Linked List
	*
	*/
	@Override
 	public String toString() {
		String s = new String();

		for (DoubleNode<T> temp = head; temp != null; temp = temp.getNext()) {
			s += temp.getData();
		}

		s += "\n";
		return s;
	}

/*****************************************************************************/
	/**
	* Test Driver for DoublyLinkedList class
	*
	* @param a Command line arguments
	*
	*/
	public static void main(String a[]) {
		DoublyLinkedList <Character> list = new DoublyLinkedList <Character>();

		list.addCharAtEnd('H');
		list.addCharAtEnd('e');
		list.addCharAtEnd('l');
		list.addCharAtEnd('l');
		list.addCharAtEnd('o');

		System.out.println(list);

		System.out.println("Deleting l");

		list.deleteChar('l');
		System.out.println(list);
		System.out.println("Deleting H");
		list.deleteChar('H');
		System.out.println(list);
		System.out.println("Deleting o");
		list.deleteChar('o');
		System.out.println(list);
		System.out.println("Deleting e");
		list.deleteChar('e');
		System.out.println(list);
		System.out.println("Deleting l");
		list.deleteChar('l');
		System.out.println(list);

		list.addCharAtFront('o');
		list.addCharAtFront('l');
		list.addCharAtFront('l');
		list.addCharAtFront('e');
		list.addCharAtFront('H');
		System.out.println(list);

		System.out.println(list.countNodes());

		System.out.println("Popping everything");

		while( !list.isEmpty() ){
			System.out.println(list.removeCharFromFront());
		}

		list.addCharAtFront('o');
		list.addCharAtFront('l');
		list.addCharAtFront('l');
		list.addCharAtFront('e');
		list.addCharAtFront('H');

		System.out.println("Popping everything from the end");


		while( !list.isEmpty() ){
			System.out.println(list.removeCharAtEnd());
		}

		System.out.println(list.countNodes());

		list.addCharAtEnd('o');
		list.addCharAtEnd('l');
		list.addCharAtEnd('l');
		list.addCharAtEnd('e');
		list.addCharAtEnd('H');

		list.reverse();

		System.out.println(list);

		list.reverse();

		System.out.println(list);
	}
 }