import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {

    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;

    Scanner kb;
    int cardValue = 0;
    int dealerValue = 0;
    int playerBank = 100;
    int currentBet = 0;
    boolean gameOver = true;

    public Blackjack() {
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.run();
    }

    public void deal() {
        deck.shuffle();

        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

        for(Card tempValue : player) {
            cardValue += tempValue.getValue();
        }

        for(Card tempValue : dealer) {
            dealerValue += tempValue.getValue();
        }

        if(cardValue == 21) {
            System.out.println("Congratulations, you won!");
            gameIsOver(!gameOver);
        }
        if(dealerValue == 21) {
            System.out.println("Sorry, you lost");
            gameIsOver(!gameOver);
        }

    }

    public void bet() {
        System.out.println("Player, do you want to bet?");
        System.out.println("You can bet $0-$" + playerBank);
        currentBet = kb.nextInt();
        kb.nextLine();
    }

    public void playerTurn() {
        boolean turnOver = false;
        while(!turnOver);
            System.out.println("Player, do you want to hit or stay?");
            String response = kb.nextLine();
            if (response.toLowerCase().equals("hit")) {
                player.add(deck.getCard());
                for (int card = 2; card == player.size(); card++) {
                    System.out.println("player hand:\t" + player.get(0) + " " + player.get(1));
                    var playerHand = player.get(card);
                    System.out.println("" + playerHand);
                }
            }
        if(response.toLowerCase().equals("stay")) {
            turnOver = true;
        }
    }

    public void dealerTurn() {
        if(dealerValue < 17) {
            dealer.add(deck.getCard());
        }
        gameIsOver(gameOver);
    }

    public void payOut() {
        if(cardValue > 21 || cardValue < dealerValue) {
            System.out.println("Sorry, you lost. You have lost $" + currentBet + ".");
            playerBank -= currentBet;
            gameIsOver(!gameOver);
        }
        if(cardValue > dealerValue && cardValue < 21) {
            if(cardValue == 21) {
                currentBet *= 1.5;
                System.out.println("Congratulations, you won. You have won $" + currentBet + ".");
                System.out.println("What a thrilling outcome!");
                playerBank *= currentBet;
                gameIsOver(!gameOver);
            }
            else {
                System.out.println("Congratulations, you won. You have won $" + currentBet + ".");
                playerBank += currentBet;
                gameIsOver(!gameOver);
            }
        }
        else {
            System.out.println("Well you didn't win, but at least you didn't lose.");
            playerBank = currentBet;
            gameIsOver(!gameOver);
        }
    }

    public boolean gameIsOver(boolean condition) {
        if(condition) {
            return true;
        }
        else {
            return false;
        }
    }


    private void run() {

        bet();
        deal();

        System.out.println("dealer hand:\t" + dealer.get(0) + " [?]");
        System.out.println("player hand:\t" + player.get(0) + " " + player.get(1));

        playerTurn();
        dealerTurn();

        payOut();


    }
}