package com.libertymutual.blackjack.models;

import java.util.List;

public class Player {

	private int availableCash;
	private Hand hand;
	public String name;

	public Player(String playerName, int startingCash) {
		availableCash = startingCash;
		hand = new Hand();
	}

	public void clearHand() {
		hand = new Hand();
	}

	public boolean isBust() {
		int[] values = hand.getValues();
		return values[0] > 21 && values[1] > 21;
	}

	public int getTotalAmount() {
		return availableCash;
	}

	public int adjustTotalAmount(int bet) {
		hand = new Hand();
		bet = Math.min(bet, availableCash);
		availableCash -= bet;
		return bet;
	}

	public void takeCard(Card card) {
		hand.addCard(card);
	}

	public List<Card> getCards() {
		return hand.getCards();
	}

	public void payout(int money) {
		availableCash += money;
	}

	public boolean hasBlackjack() {
		return hand.isBlackjack();
	}

	public int getBestScore() {
		return hand.getHighestValidValue();
	}

	public String getName() {
		return name;
	}

	public void addCard(Card card) {
		hand.addCard(card);

	}
	
}