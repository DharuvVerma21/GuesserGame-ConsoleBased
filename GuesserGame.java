import java.util.Scanner;
class Range{
    int low;
    int high;
}
class Guesser{
    int guessedNumber;
    int numberOfPlayers;
    int lowerRange;
    int higherRange;
    int numberOfRounds;
    void guessTheNumber(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Guesser, please guess the number: ");
        guessedNumber = sc.nextInt();
    }
    int passTheGuessedNumber(){
        guessTheNumber();
        return guessedNumber;
    }
    void totalNumberOfPlayers(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the total number of players playing the game: ");
        numberOfPlayers = sc.nextInt();
        while(numberOfPlayers <= 0){
            System.out.print("There should be atleast one player.\nPlease enter the total number of players playing the game again: ");
            numberOfPlayers = sc.nextInt();
        }
    }
    int passTotalNumberOfPlayers(){
        totalNumberOfPlayers();
        return numberOfPlayers;
    }
    void decideRangeOfNumbers(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the lower value of the range of numbers: ");
        lowerRange = sc.nextInt();
        while(lowerRange < 0){
            System.out.print("Lower range value should be greater than or equal to 0.\nPlease enter the lower value of the range of numbers again: ");
            lowerRange = sc.nextInt();
        }
        System.out.print("Please enter the higher value of the range of numbers: ");
        higherRange = sc.nextInt();
        while(higherRange < lowerRange+numberOfPlayers){
            System.out.print("Higher range value should be greater than or equal to " + (lowerRange+numberOfPlayers) + ".\nPlease enter the higher value of the range of numbers again: ");
            higherRange = sc.nextInt();
        }
    }
    Range passTheRange(){
        decideRangeOfNumbers();
        Range range = new Range();
        range.low = lowerRange;
        range.high = higherRange;
        return range;
    }
    void totalNumberOfRounds(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the total number of rounds in the game: ");
        numberOfRounds = sc.nextInt();
        while(numberOfRounds <= 0 || numberOfRounds > 9){
            if (numberOfRounds <= 0){
                System.out.print("There should be atleast one round.\nPlease enter the total number of rounds in the game again: ");
            }
            else{
                System.out.print("Number of rounds should be less than 10.\nPlease enter the total number of rounds in the game again: ");
            }
            numberOfRounds = sc.nextInt();
        }
    }
    int passTotalNumberOfRounds(){
        totalNumberOfRounds();
        return numberOfRounds;
    }
}
class Player{
    int playerNumber;
    void playerGuessTheNumber(int i){
        Scanner sc = new Scanner(System.in);
        System.out.print("Player " + i + ", please guess the number: ");
        playerNumber = sc.nextInt();
    }
    int passPlayerNumber(int i){
        playerGuessTheNumber(i);
        return playerNumber;
    }
}
class Umpire{
    int guesserNumber;
    int numberOfRounds;
    int numberOfPlayers;
    Range rangeOfNumbers;
    int []score;
    Player []player;
    int []numbers;
    String []roundWinners;
    int numberOfRoundWinners;
    String []finalWinners;
    int numberOfFinalWinners;
    void getInfoFromGuesser(){
        Guesser guesser = new Guesser();
        numberOfRounds = guesser.passTotalNumberOfRounds();
        numberOfPlayers = guesser.passTotalNumberOfPlayers();
        rangeOfNumbers = new Range();
        rangeOfNumbers = guesser.passTheRange();
        score = new int[numberOfPlayers];
    }
    void getGuesserNumber(){
        Guesser guesser = new Guesser();
        guesserNumber = guesser.passTheGuessedNumber();
        while(guesserNumber < rangeOfNumbers.low || guesserNumber > rangeOfNumbers.high){
            System.out.println("Number should be between " + rangeOfNumbers.low + " and " + rangeOfNumbers.high + ".");
            guesserNumber = guesser.passTheGuessedNumber();
        }
    }
    void getPlayerNumbers(){
        player = new Player[numberOfPlayers];
        numbers = new int[numberOfPlayers];
        for (int i=0; i<player.length; i++){
            player[i] = new Player();
            numbers[i] = player[i].passPlayerNumber(i+1);
            while(numbers[i] < rangeOfNumbers.low || numbers[i] > rangeOfNumbers.high){
                System.out.println("Number should be between " + rangeOfNumbers.low + " and " + rangeOfNumbers.high + ".");
                numbers[i] = player[i].passPlayerNumber(i+1);
            }
        }
    }
    void roundResult(){
        numberOfRoundWinners = 0;
        roundWinners = new String[numberOfPlayers];
        for (int i=0; i<numbers.length; i++){
            if (numbers[i] == guesserNumber){
                score[i] += 10;
                roundWinners[numberOfRoundWinners++] = "Player " + String.valueOf((i+1));
            }
        }
        displayRoundResult();
    }
    void displayRoundResult(){
        System.out.println("Player\t\t\t\t\tScore");
        for (int i=0; i<player.length; i++){
            System.out.println((i+1) + "\t\t\t\t\t" + score[i]);
        }
        if (numberOfRoundWinners == 0){
            System.out.println("No winner in this particular round.");
        }
        else if (numberOfRoundWinners == 1){
            System.out.println("Winner of this round: " + roundWinners[0]);
        }
        else{
            System.out.print("Winners of this round: ");
            for (int i=0; i<numberOfRoundWinners; i++){
                if (i == numberOfRoundWinners-2){
                    System.out.print(roundWinners[i] + " and ");
                }
                else if (i == numberOfRoundWinners-1){
                    System.out.println(roundWinners[i] + ".");
                }
                else {
                    System.out.print(roundWinners[i] + ", ");
                }
            }
        }
    }
    void finalResult(){
        numberOfFinalWinners = 0;
        finalWinners = new String[numberOfPlayers];
        int max = 0;
        for (int i=0; i<score.length; i++){
            if (max < score[i]){
                max = score[i];
            }
        }
        for (int i=0; i<score.length; i++){
            if (score[i] == max && max != 0){
                finalWinners[numberOfFinalWinners++] = "Player " + String.valueOf((i+1));
            }
        }
        displayFinalResult();
    }
    void displayFinalResult(){
        System.out.println("Player\t\t\t\t\tScore");
        for (int i=0; i<score.length; i++){
            System.out.println((i+1) + "\t\t\t\t\t" + score[i]);
        }
        if (numberOfFinalWinners == 0){
            System.out.println("No one won this game.");
        }
        else if (numberOfFinalWinners == 1){
            System.out.println("Winner of this game: " + finalWinners[0]);
        }
        else{
            System.out.print("Winners of this game: ");
            for (int i=0; i<numberOfFinalWinners; i++){
                if (i == numberOfFinalWinners-2){
                    System.out.print(finalWinners[i] + " and ");
                }
                else if (i == numberOfFinalWinners-1){
                    System.out.println(finalWinners[i] + ".");
                }
                else {
                    System.out.print(finalWinners[i] + ", ");
                }
            }
        }
    }
}
public class GuesserGame{
    public static void main(String args[]){
        System.out.println("****************************************************************************************************");
        System.out.println("                                            GUESSER GAME                                            ");
        System.out.println();
        Umpire umpire = new Umpire();
        umpire.getInfoFromGuesser();
        for (int i=1; i<=umpire.numberOfRounds; i++){
            System.out.println();
            System.out.println("****************************************************************************************************");
            System.err.println("           TOTAL ROUNDS: " + umpire.numberOfRounds + "           TOTAL PLAYERS: " + umpire.numberOfPlayers + "           RANGE: " + umpire.rangeOfNumbers.low + " TO " + umpire.rangeOfNumbers.high);
            System.out.println("****************************************************************************************************");
            System.out.println("                                           ROUND: " + i +" OF " + umpire.numberOfRounds);
            umpire.getGuesserNumber();
            umpire.getPlayerNumbers();
            System.out.println();
            System.out.println("After round " + i + ", the results are as follows: ");
            umpire.roundResult();
        }
        System.out.println("****************************************************************************************************");
        System.out.println("                                            FINAL RESULT                                            ");
        if (umpire.numberOfRounds > 1){
            System.out.println("Final result after " + umpire.numberOfRounds + " Rounds: ");
        }
        else{
            System.out.println("Final result after " + umpire.numberOfRounds + " Round: ");
        }
        umpire.finalResult();
    }
}