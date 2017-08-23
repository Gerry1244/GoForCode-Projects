package com.libertymutual.blackjack.models;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	
	
	private ArrayList<Card> cards;
	
	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
		
	}
	
	public Card get5Card(int cardIndex)  {
		return cards.get(cardIndex);
	}
	
	public int[] getValues() {
		int[] sums = new int[] { 0, 0 };
		
		for (Card c : cards) {
			int[] values = c.getValues();
			sums[0] += values[0];
			sums[1] += values[1];
		}
		
		return sums;
	}

	public boolean isBlackjack() {
		int[] values = getValues();
		return	cards.size() == 2 &&
				(values[0] == 21 || values[1] == 21);
	}

	public int getHighestValidValue() {
		int[] values = getValues();
		if (values[0] < 21 && values[1] < 21) {
			return Math.max(values[0], values[1]);
		} else if (values[0] < 21) {
			return values[0];
		} else if (values[1] < 21) {
			return values[1];
		}
		return 22;
	}


	
}
