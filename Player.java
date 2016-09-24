import java.util.*;
/**
 * Creates a generic player who can bet, make a move, and exit the game.
 * 
 * @author Aditya Varshney
 * @version 9/19/2016
 */
public class Player
{
    private ArrayList hand = new ArrayList<>();
    private ArrayList moves = new ArrayList<>();
    private String name;
    private int cash_available = 500;
    private int bet;
    private int score = 0;
    private boolean busted = false;
    private boolean blackjack = false;
    private boolean has_stayed = false;
    /**
     * Creates a Player with a specified name.
     */
    public Player(String name){
        this.name = name;
    }

    public boolean get_has_stayed(){
        return this.has_stayed;
    }

    public void set_has_stayed(boolean bool){
        this.has_stayed = bool;
    }

    public int get_bet(){
        return this.bet;
    }

    public void set_bet(int val){
        this.bet = val;
    }

    public int get_cash_available(){
        return this.cash_available;
    }

    public void set_cash_available(int val){
        this.cash_available = val;
    }

    public String get_name(){
        return this.name;
    }

    public int get_score(){
        return this.score;
    }

    public void set_score(int k){
        score = k;
    }

    public ArrayList get_hand(){
        return this.hand;
    }

    public void reset_hand(){
        hand = new ArrayList<Integer>();
    }

    public boolean is_busted(){
        if(this.score > 21)
            busted = true;    
        return this.busted;
    }

    public boolean has_21(){
        if(this.score == 21)
            return true;
        return false;
    }

    public boolean has_blackjack(){
        if(this.has_21()){
            if(this.hand.get(0) == 1 && this.hand.get(1)==10){
                blackjack = true;
            }
            else if(this.hand.get(0) == 10 && this.hand.get(1)==1){
                blackjack = true;
            }
        }
        return blackjack;
    }

    public void update_after_card(int k){
        if(k==1){
            if(this.get_score() == 10)
            {
                this.set_score(this.get_score()+11);
                return;
            }
        }
        this.set_score(this.get_score()+k);
        this.hand.add(k);
    }

    public int get_move(){
        Scanner in = new Scanner(System.in);
        System.out.println(this.name + ": Press 1 to See Hand, 2 to Hit, and 3 to Stay.");
        int x = in.nextInt();
        return x;
    }

    public String toString(){
        return name + " \t " + hand;
    }
}
