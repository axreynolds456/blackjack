public class Card {

    private int value;
    private int suit;

    public Card(int suit, int value) {
        this.value = value;
        this.suit = suit;
    }
    public int getSuit() {
            return suit;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        String[] suits = {"♥", "♦", "♣", "♠"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

       return values[this.value-2] + " " + suits[this.suit];
    }
}
