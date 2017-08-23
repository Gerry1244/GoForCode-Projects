package com.libertymutual.blackjack;

import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.Hand;
import com.libertymutual.blackjack.models.Player;

@SpringBootApplication
public class BlackjackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackApplication.class, args);

		Deck deck = new Deck();
		deck.shuffle();

		Hand hand = new Hand();
		hand.addCard(deck.getCard());
		hand.addCard(deck.getCard());
		int[] values = hand.getValues();
		System.out.println("Value1: " + values[0] + " Value2: " + values[1]);

		Player player1 = new Player("Gerry", 500);

		player1.takeCard(deck.getCard());
		player1.takeCard(deck.getCard());

	}
}
