package com.libertymutual.blackjack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libertymutual.blackjack.models.Dealer;
import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.Player;

@Controller
@RequestMapping("/blackjack")

public class BlackjackController {
	Deck deck = new Deck();
	Player player1 = new Player("Gerry", 500);
	Dealer dealer = new Dealer();
	int total;
	int currentBet;
	boolean noShowDealerCard;
	boolean showDealerCard;
	boolean showPlayerButtons;
	boolean showPlayAgainstButton;



	

	@GetMapping("")
	public String startPage() {
		return "/Home/blackjack";
	}
	@GetMapping("/game")
	public String blackjackhomemodel(Model model) {
		model.addAttribute("playerName", player1.getName());
		model.addAttribute("total", player1.getTotalAmount());
		model.addAttribute("currentTotal", currentBet);
//		model.addAttribute("dealerCard1", dealer.getCard(0).toString());
//		model.addAttribute("dealerCard2", dealer.getCard(1).toString());
		model.addAttribute("noShowDealerCard", noShowDealerCard);
		model.addAttribute("ShowDealerCard", showDealerCard);
		model.addAttribute("playerCards", player1.getCards());
		model.addAttribute("dealerCards", dealer.getCards());
		model.addAttribute("currentBet", currentBet);
		//model.addAttribute("playerValues", player1.getValues()); //
		model.addAttribute("ShowPlayerButtons", showPlayerButtons);
		return "blackjack/game";

	}

	@PostMapping("bet")
	public String makeBet(int playerBet, String playerName, Model model) {
		

		player1.clearHand();
		dealer.clearHand();

		currentBet = playerBet;
		if (player1.getTotalAmount() == 0) {
			return "redirect:/blackjack/game/no-money";

		} else if (playerBet > player1.getTotalAmount()) {
			return "redirect:/blackjack/blackjackgame/please-bet-less";

		} else {
			player1.adjustTotalAmount(playerBet); 
			
		}
		currentBet = playerBet;

		if (deck.getNumberOfCardsLeft() > 0) {
			dealer.addCard(deck.getCard());

		} else {
			return "redirect:/blackjack/blackjackgame/no-cards";
		}
		if (deck.getNumberOfCardsLeft() > 0)  {// Use the greater than above, here
			dealer.addCard(deck.getCard());
			
		} else {
			return "redirect:/blackjack/game/no-cards";

		}
		if (deck.getNumberOfCardsLeft() > 0) {
			player1.addCard(deck.getCard());

		} else {
			return "redirect:/blackjack/game/no-cards";

		}
		if (deck.getNumberOfCardsLeft() > 0) {
			player1.addCard(deck.getCard());

		} else {
			return "redirect:/blackjack/game/no-cards";

		}
		noShowDealerCard = true;
		showDealerCard = false;
		showPlayerButtons = true;
		
		model.addAttribute("currentBet", currentBet);
		model.addAttribute("playerCards", player1.getCards());
		model.addAttribute("dealerCards", dealer.getCards());
		return "redirect:/blackjack/game";

	}

	@PostMapping("start")
	public String startingTheGame() {
		deck.shuffle();
		return "blackjack/new-game-form";

	}

	@PostMapping("hit")
	public String hitDeck(Model model) {
		// You can totally delete this commented code after you've reviewed it
		// int[] values = player1.getValues();
		int bestPlayerScore = player1.getBestScore();
		// if (values[0] > 21 && values[1] > 21) {
		if (bestPlayerScore > 21) {
			return "redirect:/blackjack/blackjackgame/bust";
		} else {
			if (deck.getNumberOfCardsLeft() > 0 )  {
				player1.addCard(deck.getCard());
			} else {
				return "redirect:/blackjack/blackjackgame/no-more-cards";
			}

			bestPlayerScore = player1.getBestScore();
			System.out.println(bestPlayerScore);
			if (bestPlayerScore > 21) {
				return "redirect:/blackjack/blackjackgame/bust";
			} else {
				model.addAttribute("currentBet", currentBet);
				model.addAttribute("playerCards", player1.getCards());
				model.addAttribute("dealerCards", dealer.getCards());
				model.addAttribute("numberOfCardsRemaining", deck.getNumberOfCardsLeft());
				return "redirect:/blackjack/game";
			}
		}

	}

	@PostMapping("stand")
	public String stand() {
		return "redirect:/blackjack/blackjackgame/deal-to-dealer";

	}

	@GetMapping("blackjackgame/deal-to-dealer")
	public String dealToDealer(Model model) {
		int dealerBestScore = dealer.getBestScore();
		while (dealerBestScore < 17) {

			if (deck.getNumberOfCardsLeft() > 0) {
				dealer.addCard(deck.getCard());
			} else {
				return "redirect:/blackjack/blackjackgame/no-more-cards";
			}

			dealerBestScore = dealer.getBestScore();
		}
		return "redirect:/blackjack/blackjackgame/end-hand";
	}

	@GetMapping("/blackjackgame/end-hand")
	public String endHand(Model model) { 
	
		String messageToPlayer = "";
		
		if (dealer.isBust()) {
            player1.payout(currentBet * 2);
            
        } else if (player1.hasBlackjack() && !dealer.hasBlackjack()) {
        	player1.payout(currentBet + currentBet / 2);
        	
        } else if (player1.getBestScore() == dealer.getBestScore()) {
        	player1.payout(currentBet);
        	
        } else if (player1.getBestScore() > dealer.getBestScore()) {
        	player1.payout(currentBet * 2);
        	
        }
        
		
	
		currentBet = 0;
 		model.addAttribute("messageToPlayer", messageToPlayer);
 		model.addAttribute("totalAmount", player1.getTotalAmount()); // Get the real name of this method
 		model.addAttribute("playerCards", player1.getCards());
 		model.addAttribute("dealerCards", dealer.getCards());
// 		model.addAttribute("showPlayAgainButton", showPlayAgainButton); // Don't delete this. Just ignore until other stuff works.
 		return "blackjack/end-hand";
 		
 	}

	@GetMapping("/blackjackgame/bust")
	public String bust(Model model) {
		currentBet = 0;
		//messageToPlayer = "You busted.  ha ha .  you are bad";
		//playerWinLossDollarAmount = 0;
		//get rid of all the model.addattributes in here and no-money and no-cards, 
		//they should all redirect to /blackjackgame/end-hand" - the same as this function does.
		model.addAttribute("totalAmount", player1.getTotalAmount()); // Get the real name of this method
		model.addAttribute("playerCards", player1.getCards());
		model.addAttribute("dealerCards", dealer.getCards());
		model.addAttribute("messageToPlayer", "bust!  You have exceeded the allowable limit for this game");
//		model.addAttribute("showPlayAgainButton", showPlayAgainButton); // Don't delete this. Just ignore until other stuff works.
		player1.clearHand();
		dealer.clearHand();
		return "redirect:/blackjackgame/end-hand";
	}

	@GetMapping("/game/no-money")
	public String noMoreMoney(Model model) {
		//messageToPlayer = "you are out of money.  you stink"
		//playerWinLossAmount = 0;
		return "redirect:/blackjackgame/end-hand";  // MAKE THIS HTML PAGE THAT SHOWS THE GAME IS OVER AND THEY HAVE NO MONEY (JUST TYPE THIS IN THE HTML, NO VARIABLE) AND THEY'RE LOSERS
	}

	@GetMapping("/game/no-cards")
	public String noMoreCards(Model model) {
		//messageToPlayer
		//plyaerWinLossAmount
		currentBet = 0;
//		showPlayAgainButton = false;

		model.addAttribute("totalAmount", player1.getTotalAmount());
		model.addAttribute("messageToPlayer", "No more cards.  Game Over!");
//		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		return "blackjack/game-over"; // MAKE THIS HTML PAGE THAT SHOWS THE GAME IS OVER AND SHOWS totalAmount

	}

	@GetMapping("/game/need-to-bet-less")
	public String needToBetLess(Model model) {
		currentBet = 0;
//		showPlayAgainButton = true;
		model.addAttribute("walletAmount", player1.getTotalAmount());
		model.addAttribute("playerCards", player1.getCards());
		model.addAttribute("dealerCards", dealer.getCards());
		model.addAttribute("messageToPlayer", "Please bet no more than $" + player1.getTotalAmount() + "!");
//		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		return "blackjack/end-hand";
	}

}


