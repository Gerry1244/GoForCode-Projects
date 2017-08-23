package com.libertymutual.blackjack.models;

import java.util.ArrayList;
import java.util.List;

public class Dealer {

	private Deck deck;
	private Hand hand;
	private boolean hideHoleCard;
	
	public Dealer() {
		deck = new Deck();
		deck.shuffle();
		hand = new Hand();
	}
	
	public int getNumberOfCardsLeft() {
		return deck.getNumberOfCardsLeft();
	}
	
	public boolean isBust() {
		int[] values = hand.getValues();
		return values[0] > 21 && values[1] > 21;
	}
	
	public void startRound() {
		hideHoleCard = true;
		hand = new Hand();
	}
	
	public void finishRound() {
		hideHoleCard = false;
		int[] values = hand.getValues();
		if (values[0] == 21 || values[1] == 21) {
			return;
		}
		while (values[0] < 17 || values[1] < 17) {
			dealCardToSelf();
			values = hand.getValues();
		}
	}
	
	public List<Card> getCards() {
		List<Card> cards = hand.getCards();
		if (!hideHoleCard || cards.size() == 0) {
			return cards;
		}

		Card firstCard = cards.get(0);
		List<Card> cardsToShow = new ArrayList<Card>();
		cardsToShow.add(firstCard);
		cardsToShow.add(new HoleCard());
		return cardsToShow;
	}
	
	public Card getCard(int index) {
		return hand.get5Card(index);
	}
	
	public void dealCartToPlayer(Player player) {
		Card card = deck.getCard();
		if (card != null) {
			player.takeCard(card);
		}
	}

	public void dealCardToSelf() {
		Card card = deck.getCard();
		if (card != null) {
			hand.addCard(card);
		}
	}

	public boolean hasBlackjack() {
		return hand.isBlackjack();
	}

	public int getBestScore() {
		return hand.getHighestValidValue();
	}

	public void addCard(Card card) {
		hand.addCard(card);
		
	}

	public void clearHand() {
		hand = new Hand();
		
	}
	
}
