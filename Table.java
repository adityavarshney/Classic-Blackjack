import java.util.*;
/**
 * Contains methods to update cash amounts, bets, and player information while a game of blackjack is taking place.
 * 
 * @author Aditya Varshney 
 * @version 9/19/16
 */
public class Table
{
    public int starting_cash = 500;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> outed_players = new ArrayList<Player>();
    private Dealer dealer = new Dealer();
    int[]deck;
    public Table(){
        play_blackjack(false);
    }

    /**
     * Sets player information using user inputs.
     */
    public void start_game(){
        Scanner in = new Scanner(System.in);
        System.out.println("<><><><><><><>\t Welcome to Blackjack \t<><><><><><><>");
        System.out.println("How many players are playing?");
        int x = in.nextInt();
        while(x <= 0){
            System.out.println("\nSorry, that value is too low.");
            System.out.println("How many players are playing? ...");
            x = in.nextInt();
        }
        for(int i = 0; i < x; i++)
        {
            System.out.println("\nPlayer " + (i+1) + " name? ...");
            String name = in.next();
            this.players.add(new Player(name));
        }
    }

    /**
     * Prints player information from the lists of outed players and players who are still in the round.
     */
    public void show_results(){
        System.out.println("<><><><><><><>\tRound results\t<><><><><><><>");
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            System.out.println(p.toString() + "\t Cash: " + (p.get_cash_available()));
        }
        for(int i = 0; i < outed_players.size(); i++){
            Player p = outed_players.get(i);
            System.out.println(p.toString() + "\t Cash: " + (p.get_cash_available()));
        }
    }

    /**
     * Determines if a player is still in the round and prints this information for the user to see.
     */
    public boolean check_game_over(Player p){
        if(p.is_busted()){
            System.out.println(p.toString());
            System.out.println(p.get_name() + " is busted!");
            return true;
        }
        else if(p.has_blackjack()){
            System.out.println(p.toString());
            System.out.println(p.get_name() + " wins! Blackjack!");
            return true;
        }
        else if(p.has_21()){
            System.out.println(p.toString());
            System.out.println(p.get_name() + " wins!");
            return true;
        }
        else if(p.get_has_stayed())
            return true;
        return false;
    }

    /**
     * Removes any players that have either busted or reached 21.
     */
    public void remove_outed_players(){
        for(int i = players.size()-1;i>=0; i--)
        {
            if(check_game_over(players.get(i))){
                System.out.println("The round is over for " + players.get(i).get_name() + "\n"); 
                outed_players.add(players.remove(i));
            }
        }
    }

    /**
     * Resets the appropriate player parameters to prepare the game for a new round.
     * This involves empyting the outed_players list into the players list and creating a 
     * new Dealer who has the same settings as a new player.
     */
    public void round_reset(){
        for(int i= outed_players.size()-1; i >=0; i--)
            players.add(outed_players.remove(i));
        for(int i = 0; i < players.size(); i++){
            players.get(i).reset_hand();
            players.get(i).set_score(0);
            players.get(i).set_has_stayed(false);
        }
        dealer = new Dealer();
        System.out.println("\n");
    }

    /**
     * Determines if the users would like to play a new game. If a player does not have any funds remaining,
     * he/she is removed from the game. If none of the players have funds, then this method will start a new game.
     */
    public void keep_playing_or_quit(){
        Scanner in = new Scanner(System.in);
        System.out.println("Game is over. Would you like to keep playing? Type 'yes' or 'no'.");
        String str = in.next();
        if(str.equals("yes")){
            for(int i = players.size()-1; i >=0; i--){
                Player p = players.get(i);
                if(p.get_cash_available()<=0)
                {
                    players.remove(i);
                }
            }
            for(int i = outed_players.size()-1; i >=0; i--){
                Player p = outed_players.get(i);
                if(p.get_score()!=21 && p.get_cash_available()<=0)
                {
                    outed_players.remove(i);
                }
            }
            round_reset();
            if(players.size()==0 && outed_players.size()==0){
                play_blackjack(false);
            }
            else{
                play_blackjack(true);
            }
        }
        else if(str.equals("no"))
            System.exit(0);
        else{
            System.out.println("Did not register.");
            keep_playing_or_quit();
        }
    }

    /**
     * Plays a game of blackjack. Creates and shuffles the deck to be used in the game. Calls the other methods in this class.
     * @param new_round a boolean that determines if the game is a new game or a new round. 
     */
    public void play_blackjack(boolean new_round){
        if(!new_round)
            start_game();

        deck = dealer.generate_deck();
        Scanner in = new Scanner(System.in);

        for(int i = 0; i < 2; i++){
            int k = dealer.deal_card()%13;
            dealer.update_after_card(k);
            if(i == 0)
                System.out.println(dealer.toString() + "[X]");
        }

        for(int i = players.size()-1; i >= 0; i--)
        {
            System.out.println("\n" + players.get(i).get_name() + ", you have " + players.get(i).get_cash_available() + " available to bet.");
            System.out.println("Place your bet. ...");
            int b = in.nextInt();
            if(players.size() <= 0 || b > players.get(i).get_cash_available() || b == 0){
                i++;
            }
            else{
                Player p = players.get(i);
                p.set_bet(b);
                p.set_cash_available(p.get_cash_available()-b);
                System.out.println("Cash remaining: " + p.get_cash_available());
            }
        }
        for(int i = 0; i < players.size(); i++)
        {
            int k = dealer.deal_card()%13;
            this.players.get(i).update_after_card(k);
            int j = dealer.deal_card()%13;
            this.players.get(i).update_after_card(j);
        }

        if(dealer.has_blackjack()){
            System.out.println("\n" + dealer.get_name() +" has blackjack!" + "\t Hand:" + dealer.get_hand());
            play_blackjack(true);
        }

        remove_outed_players();
        System.out.println("\nCards Dealt. Let's Play!");

        for(int i = players.size()-1; i>=0; i--){
            if(i < players.size()){
                Player p = players.get(i);
                int move = p.get_move();
                if(move == 1){
                    System.out.println(p.toString());
                    i++;
                }
                else if(move == 2){
                    int k = dealer.deal_card()%13;
                    players.get(i).update_after_card(k);
                    if(!p.is_busted()){
                        i++;
                    }
                }
                else if(move==3){
                    p.set_has_stayed(true);
                }
                remove_outed_players();
            }
        }
        
        for(int i = outed_players.size()-1;i>=0;i--){
            Player p = outed_players.get(i);
            if(p.get_has_stayed()==true){
                players.add(outed_players.remove(i));//send players who opted to "Stay" back into the players list.
            }
        }
        if(players.size()!=0){
            System.out.println("\n" + dealer.toString());
            while(dealer.get_score()<17){ //deal until dealer hits 17 (a common casino blackjack stopping point)
                int k = dealer.deal_card()%13;
                dealer.update_after_card(k);
                System.out.println(dealer.toString()); 
            }
            if(dealer.get_score()>21){ //dealer busts
                System.out.println("\n" + dealer.get_name() + " is busted! Everyone left on the table wins! \n");
                for(int i=0;i<players.size();i++){
                    Player p = players.get(i);
                    p.set_cash_available(p.get_cash_available()+2*p.get_bet());
                }
            }
            else if(dealer.get_score()==21){ //dealer hits 21. (blackjack case already covered)
                System.out.println("\n" + dealer.get_name() + " has 21!");
                System.out.println(dealer.toString());
            }
            else{ //dealer has between 17 and 21
                for(int i = 0; i < players.size();i++){
                    Player p = players.get(i);
                    if(p.get_score() >= dealer.get_score()){
                        p.set_cash_available(p.get_cash_available()+2*p.get_bet());
                    }
                }
            }
        }
        else{
            System.out.println("No players left on the table.");
        }

        for(int i = 0; i < outed_players.size(); i++){
            Player p = outed_players.get(i);
            if(p.has_blackjack()){
                p.set_cash_available(p.get_cash_available()+(int)(2.5*p.get_bet()));
            }
            else if(p.get_score()==21){
                p.set_cash_available(p.get_cash_available()+2*p.get_bet());
            }
        }
        show_results();

        keep_playing_or_quit();
    }
}
