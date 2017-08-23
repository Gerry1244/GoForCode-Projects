package com.libertymutual.blackjack.models;


public class HoleCard implements Card {

	
	public String getSuit() {
		return "";
	}

	
	public String getVisualRepresentation() {
		return "«unknown»";
	}


	public int[] getValues() {
		return new int[] { 0, 0 };
	}

}
