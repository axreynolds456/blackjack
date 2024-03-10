import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Blackjack {

    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;


    Scanner kb;

    int cardValue = 0;
    int dealerValue = 0;
    int playerBank = 500;
    int currentBet = 0;
    private boolean playerStand;
    private boolean dealerStand;


    public Blackjack() {
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
        dealerStand = false;
        playerStand = false;
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.run();
    }

    public void gameStart() {
        //clears the player and deal hands
        player.clear();
        dealer.clear();

        //makes a new deck
        deck = new Deck();

        //shuffles the deck
        deck.shuffle();
    }

    public void deal() {
        int playerDeal = handValue(player);
        int dealerDeal = handValue(dealer);
        //deals the cards to the player and dealer
        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

        if (playerDeal == 21) {
            System.out.println("Wow...that was over quick.");
            System.out.println("You have won " + currentBet + " currency.");
            playerBank += currentBet;
        }

        if (dealerDeal == 21) {
            System.out.println("That was unlucky.");
            System.out.println("You have lost " + currentBet + " currency.");
            playerBank -= currentBet;
        }
    }

    public void bet() {
        System.out.println("Player, do you want to bet?");
        System.out.println("You can bet 0-" + playerBank + " currency.");
        currentBet = kb.nextInt();
        kb.nextLine();
    }

    public void playerTurn() {
        while (!playerStand && !dealerStand) {
            System.out.println("Your hand: " + player + " (Value: " + handValue(player) + ")");
            System.out.println("Dealer's hand: [" + dealer.get(0) + ", ?]");
            System.out.println("Would you like to hit or stand?");
            String action = kb.nextLine();

            if (action.equals("hit")) {
                playerHit();
            } else if (action.equals("stand")) {
                playerStand();
                break;
            } else {
                System.out.println("Invalid option. Please choose to hit or stand.");
            }
        }
    }

    private void endGame() {
        int playerValue = handValue(player);
        int dealerValue = handValue(dealer);

        System.out.println("Final hands:");
        System.out.println("Player: " + player + " (Value: " + playerValue + ")");
        System.out.println("Dealer: " + dealer + " (Value: " + dealerValue + ")");

        if (playerValue > 21 || (dealerValue <= 21 && dealerValue > playerValue)) {
            System.out.println("The Dealer wins!");
            System.out.println("Better luck next time!");
            playerBank -= currentBet;
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            System.out.println("The Player wins!");
            System.out.println("Nice Job!");
            playerBank += currentBet;
        } else {
            System.out.println("It's a tie!");
            // No change to player's bankroll on a tie
        }

        // playerTurn()
        // dealerTurn()
        // calcHandValue()
        System.out.println("You currently have " + playerBank + " currency left.");
    }


    private void run() {

        gameStart();
        bet();
        deal();

        System.out.println("dealer hand:\t" + dealer.get(0) + " [?]");
        System.out.println("player hand:\t" + player.get(0) + " " + player.get(1));

        playerTurn();
        dealerTurn();

        playAgain();
    }

    public void playerHit() {
        //adds a card
        player.add(deck.getCard());
        System.out.println("You draw: " + player.get(player.size() - 1));

        //checks if the player busted
        if (handValue(player) > 21) {
            System.out.println("You have busted");
            playerStand = true;
            dealerStand = true;

            endGame();
        }
    }

    //for when the player does not want to hit
    public void playerStand() {
        System.out.println("You have decided to stand");
        playerStand = true;
    }

    private void dealerTurn() {
        while (handValue(dealer) < 17) {
            dealer.add(deck.getCard());
            System.out.println("The dealer drew a " + dealer.get(dealer.size() - 1));
        }

        dealerStand = true;
        endGame();
    }


    private int handValue(ArrayList<Card> hand) {
        //checks the value of the hand
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            int cardValue = card.getValue();
            if (cardValue >= 11 && cardValue <= 13) {
                value += 10;
            } else if (cardValue == 14) {
                value += 11;
                aces++;
            } else {
                value += cardValue;
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10; // Convert Ace from 11 to 1
            aces--;
        }

        return value;
    }

    public void playAgain() {
        System.out.println("Do you want to play another round?");
        System.out.println("Yes or No");
        String answer = kb.nextLine();

        if (answer.equals("yes")) {
            run();
        } else if (answer.equals("no")) {
            exit(0);
        }
        else {
            System.out.println("Come on man, answer the question. Yes or No.");
        }
    }
}