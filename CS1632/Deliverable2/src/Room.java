// package com.laboon;

public class Room {

	private static int _pseudoRand = 0;
	
	private String _description = null;
	
	private boolean _northExit = false;
	private boolean _southExit = false;
	
	private boolean _hasSugar = false;
	private boolean _hasCream = false;
	private boolean _hasCoffee = false;
	
	public boolean hasItem() {
		return _hasSugar || _hasCream || _hasCoffee;
	}
	
	public boolean hasSugar() {
		return _hasSugar;
	}
	
	public boolean hasCream() {
		return _hasCream;
	}
	
	public boolean hasCoffee() {
		return _hasCoffee;
	}
	
	public boolean northExit() {
		return _northExit;
	}
	
	public boolean southExit() {
		return _southExit;
	}
	
	private String getAdjective() {
		String[] adjs = { "Small",  "Quaint", 
        "Shiny", "Magenta", "Funny", "Sad", "Fuchsia", "Beige", "Massive", "Refinanced", "Tight", "Loose",
        "Dead", "Smart", "Dumb", "Flat", "Bumpy", "Vivacious", 
        "Slim", "Bloodthirsty", "Beautiful", 
        "Flowery", "Purple", "Sandy", "Rough",
        "Perfect", "Heroic", "Minimalist", "Shoe-Losing", "Major", 
        "Wonderful", "Loving", "Fun-Loving",
        "High-Level", "Functional","Static", "Dynamic",
        "Fast", "Bulletproof", "Late", "Silly",
        "Salty", "Sour", 
        "Chair-Adjusting", "Brave", "Forgetful", "Chair-Sitting", "Mind-Blowing", "Crazy",
        "Funny", "Birdlike", "Bird-Brained", "Miniature", 
        "High-Strung", "Famous", "Light", "Dark", "Feral", "Hairy",
        "Leaky", "Criminal", "Sassy", "Frumpy", "Tiny", 
        "Prehistoric", "Metallic", "Sharp", "Historical", "Fierce", "Loud",
        "Lunar", "Bohemian", "Bored", "Suspicious", 
        "Flirtatious", "Street-Smart", "Forgetful",  
        "Tooth-Filled", "Ravenous", 
        "Well-Directed", "Well-Fed", "Well-Maintained", "Deep", "Shallow",
        "Victorian", "Formal", "Creamy", "Tangy", "Fresh", "Magical", "Mystical",
        "Secret", "Prophetic", "Immortal", "Far-Sighted",
        "Short-Sighted",  "Latin", "Nepalese",  "Medical",
        "Straightforward", "Literary", "Critical", "Backward", "Rabid", "Bombastic",
        "Smelly", "Wanton", "Confusing", "Cheesy",
        "Devious", "Pumpkin Spice", "Submerged", "Muscular",
        "Well-Organized", "Smooth", "Delicious", "Creamy", "Dry", 
        "Independent", "Free", "Cheap", "Diaphonous", "Tired",
        "Sultry", "Beguiling", "Long-Lived", "Repetitive",
        "Disgusting", "Swampy", "Dirty", "Muddy", "Clean", "Dry", "Wet",
        "Clear", "Transparent", "Glorious", "Sacrificial", "Electric", "Mechanical",
        "Automatic", "Rapid", "Nervous", "Calm", "Contemplative",
        "Cerebral", "Voracious", "Starving", "Full", "Killer", "Wicked", "Rational", "Real",
        "Homemade", "Cigar-Chomping", "Sinister", "Doubting",
        "Robotic", "Monosyllabic", "Maniacal", "Postmodern", "Prehistoric" };
		return adjs[(_pseudoRand++ % adjs.length)];
	}
	
	private String getNoun() {
		String[] nouns = { "kettle", "sink", "sofa", "plant", "elephant", "bird cage", "record player", "picture of a ghost",
		         "picture of a dog", "dresser", "book", "pizza", "cake", "pinball game",
		         "slug", "board game", "energy drink", "jack o' lantern", "fireplace",
		         "pumpkin spice latte", "copy machine", "bag of money", "stapler",
		         "bottle", "xylophone", "tablecloth", "air hockey table", "textbook", "dog", "duck", "programmer",
		         "radish", "clock", "carpet", "rug", "stamp collection"};
		return nouns[(_pseudoRand++ % nouns.length)];		
	}
	
	private String generateDescription() {
		String toReturn = "\nYou see a " + getAdjective() + " room.\n"
				+ "It has a " + getAdjective() + " " + getNoun() + ".\n";
				
		if (_northExit) {
			toReturn += "A " + getAdjective() + " door leads North.\n";
		}
		
		if (_southExit) {
			toReturn += "A " + getAdjective() + " door leads South.\n";
		}
		
		return toReturn;
		
	}
	
	public Room(boolean hasCoffee, 
				boolean hasCream, 
				boolean hasSugar,
				boolean northExit,
				boolean southExit) {
		_hasCoffee = hasCoffee;
		_hasCream = hasCream;
		_hasSugar = hasSugar;
		_northExit = northExit;
		_southExit = southExit;
		_description = generateDescription();
	}
	
	public String getDescription() {
		return _description;
	}
	
}
