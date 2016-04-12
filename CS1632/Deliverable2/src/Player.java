//package com.laboon;

public class Player {

	private boolean _hasSugar = false;
	private boolean _hasCream = false;
	private boolean _hasCoffee = false;
	
	public Player() {
		
	}
	
	public Player(boolean sugar, boolean cream, boolean coffee) {
		_hasSugar = sugar;
		_hasCream = cream;
		_hasCoffee = coffee;
	}
	
	public void getSugar() {
		System.out.println("You found some sweet sugar!");
		_hasSugar = true;
	}
	
	public void getCream() {
		System.out.println("You found some creamy cream!");
		_hasCream = true;
	}
	
	public void getCoffee() {
		System.out.println("You found some caffeinated coffee!");
		_hasCoffee = true;
	}
	
	public boolean hasAllItems() {
		return (_hasCoffee && _hasCream && _hasSugar);
	}
	
	public void showInventory() {
		if (_hasCoffee) {
			System.out.println("You have a cup of delicious coffee.");
		} else {
			System.out.println("YOU HAVE NO COFFEE!");
		}
		if (_hasCream) {
			System.out.println("You have some fresh cream.");
		} else {
			System.out.println("YOU HAVE NO CREAM!");
		}
		if (_hasSugar) {
			System.out.println("You have some tasty sugar.");
		} else {
			System.out.println("YOU HAVE NO SUGAR!");
		}
	}
	
	public boolean drink() {
		boolean win = false;
		
		showInventory();
		
		System.out.println();
		
		if (_hasCoffee && _hasCream && _hasSugar) {
			System.out.println("You drink the beverage and are ready to study!");
			win = true;
		} else if (_hasCoffee) {
			if (!_hasCream) {
				System.out.println("Without cream, you get an ulcer and cannot study.");
			} else {
				System.out.println("Without sugar, the coffee is too bitter.  You cannot study.");
			}
		} else if (_hasCream) {
			if (!_hasSugar) {
				System.out.println("You drink the cream, but without caffeine, you cannot study.");
			} else {
				System.out.println("You drink the sweetened cream, but without caffeine, you cannot study.");
			}
		} else if (_hasSugar) {
			System.out.println("You eat the sugar, but without caffeine, you cannot study.");
		} else {
			System.out.println("You drink the air, as you have no coffee, sugar, or cream.\n"
					+ "The air is invigorating, but not invigorating enough.  You cannot study.");
		}
		return win;
	}
}
