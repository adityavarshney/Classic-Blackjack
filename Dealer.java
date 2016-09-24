
/**
 * Creates a Dealer who can deal cards
 * Created generate_deck() and deal_card() methods with help 
 * from http://math.hws.edu/javanotes/source/chapter5/Deck.java
 * 
 * @author Aditya Varshney
 * @version 9/19/16
 */
public class Dealer extends Player
{
    private int[] deck = new int[]{1,2,3,4,5,6,7,8,9,10,10,10,10,1,2,3,4,5,6,7,8,9,10,10,10,10,1,2,3,4,5,6,7,8,9,10,10,10,10,1,2,3,4,5,6,7,8,9,10,10,10,10};
    //[1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6];
    private int cardsUsed = 0;
    private String name;
    
    public Dealer(){
        super("Dealer");
    }
    /**
     * Fills the deck with random values and sets the value of cardsUsed to 0.
     */
    public int[] generate_deck(){
        for (int i = 0; i < 52; i++) {
            int swap_index = 0+(int)(Math.random()*52);
            int swap_value = deck[swap_index];
            deck[swap_index] = deck[i];
            deck[i]=swap_value;
        }
        cardsUsed = 0;
        return deck;
    }
    
    public int deal_card(){
        if(cardsUsed >= deck.length){
            throw new IllegalStateException("No cards left in this deck!");
        }
        cardsUsed++;
        return deck[this.cardsUsed];
    }
}
