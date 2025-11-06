package day22;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main
{
    static String inputFile = "src/day22/input.txt";
    static List<Food> foods = new ArrayList<>();
    static List<Integer> deck1 = new ArrayList<>();
    static List<Integer> deck2 = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();
        populateStartingDecks(inputFileList);
        int roundCount = 1;

        while (!deck1.isEmpty() && !deck2.isEmpty())
        {
            sop("-- Round " + roundCount + " --");
            printPlayerDeck(1, deck1);
            printPlayerDeck(2, deck2);

            int player1Card = deck1.get(0);
            int player2Card = deck2.get(0);

            sop("Player 1 plays: " + player1Card);
            sop("Player 2 plays: " + player2Card);

            if (player1Card > player2Card)
            {
                deck1.remove(0);
                deck2.remove(0);
                deck1.add(player1Card);
                deck1.add(player2Card);
                sop("Player 1 wins the round!");
            }
            else if (player1Card < player2Card)
            {
                deck1.remove(0);
                deck2.remove(0);
                deck2.add(player2Card);
                deck2.add(player1Card);
                sop("Player 2 wins the round!");
            }
            else
            {
                sop("\n\n\nERROR ERROR ERROR\nPlayer 1 card (" + player1Card + ") == Player 2 card (" + player2Card + ")\nERROR ERROR ERROR\n\n\n");
                System.exit(1);
            }

            roundCount++;
            sop();
        }

        sop("\n====================================================================================================================================");

        sop("== Post-game results ==");
        printPlayerDeck(1, deck1);
        printPlayerDeck(2, deck2);

        List<Integer> winnerDeck = deck1.size() > deck2.size() ? deck1 : deck2;
        Collections.reverse(winnerDeck);
        int winnerScore = 0;
        int scoreMult = 1;

        for (Integer score : winnerDeck)
        {
            winnerScore += scoreMult * score;
            scoreMult++;
        }

        sop("Winner's score: " + winnerScore);


    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

    public static void populateStartingDecks(List<String> inputFileList)
    {
        boolean buildingPlayer1Deck = false;
        boolean buildingPlayer2Deck = false;

        for (String s : inputFileList)
        {
            if (!isParsableToInt(s))
            {
                if (s.contains("Player 1"))
                {
                    buildingPlayer1Deck = true;
                    buildingPlayer2Deck = false;
                }
                else if (s.contains("Player 2"))
                {
                    buildingPlayer1Deck = false;
                    buildingPlayer2Deck = true;
                }
            }
            else if (isParsableToInt(s))
            {
                if (buildingPlayer1Deck)
                {
                    deck1.add(Integer.parseInt(s));
                }
                else if (buildingPlayer2Deck)
                {
                    deck2.add(Integer.parseInt(s));
                }
            }
        }
    }

    public static class Food
    {
        int foodNo;
        List<String> ingredients;
        List<String> allergens;

        public int getFoodNo()
        {
            return foodNo;
        }

        public void setFoodNo(int foodNo)
        {
            this.foodNo = foodNo;
        }

        public List<String> getIngredients()
        {
            return ingredients;
        }
        public void setIngredients(List<String> ingredients)
        {
            this.ingredients = ingredients;
        }

        public List<String> getAllergens()
        {
            return allergens;
        }
        public void setAllergens(List<String> allergens)
        {
            this.allergens = allergens;
        }

        private Food(int foodNo, List<String> ingredients, List<String> allergens)
        {
            this.foodNo = foodNo;
            this.ingredients = ingredients;
            this.allergens = allergens;
        }
    }

    public static boolean isParsableToInt(String str)
    {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNextParenACloseParen(List<String> list)
    {
        for (String s : list)
        {
            if (s.equals(")"))
            {
                return true;
            }
            else if (s.equals("("))
            {
                return false;
            }
        }

        return false;
    }

    /**
     * Counts the number of occurrences of the given character in the given list of strings.
     * @param list
     * @param c
     * @return
     */
    public static int charCount(List<String> list, char c)
    {
        return (int) list.stream()
            .flatMapToInt(String::chars)
            .filter(ch -> ch == c)
            .count();
    }

    /**
     * Imports the input file into a list.
     * @return
     * @throws IOException
     */
    public static List<String> importInputFileIntoInputFileList() throws IOException
    {
        List<String> list = new ArrayList<>();

        BufferedReader bf = new BufferedReader(new FileReader(inputFile));
        String readLine;

        while ((readLine = bf.readLine()) != null)
        {
            list.add(readLine);
        }

        bf.close();

        return list;
    }

    /**
     * Prints the given string to the console.
     * @param string
     */
    public static void sop(String string)
    {
        System.out.println(string);
    }

    /**
     * Prints an empty line to the console, to help with readability
     */
    public static void sop()
    {
        System.out.println();
    }

    /**
     * Prints the given List<String> to the console.
     * @param list
     */
    public static void printList(List<String> list)
    {
        sop();
        list.forEach(System.out::println);
    }

    /**
     * Prints the given 2 lists of String to the console, beside one another
     */
    public static void print2ListsSideBySide(List<String> list1, List<String> list2)
    {
        sop();
        for (int i = 0; i < list1.size(); i++)
        {
            System.out.println(list1.get(i) + "    " + list2.get(i));
        }
    }

    /**
     * Prints the given List<String> to the console as one concatenated string
     * @param list
     */
    public static void printListString(List<String> list)
    {
        sop();

        StringBuilder sb = new StringBuilder();
        for (String string : list)
        {
            sb.append(string).append(" ");
        }

        sop(sb.toString());
    }

    /**
     * Prints the given List<Integer> to the console as one concatenated string
     * @param list
     */
    public static void printPlayerDeck(int player, List<Integer> list)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++)
        {
            sb.append(list.get(i));

            if (i < list.size() - 1)
            {
                sb.append(", ");
            }
        }

        sop("Player " + player + "'s deck: " + sb);
    }

    /**
     * Returns the given List<String> to the console as one concatenated string
     * @param list
     */
    public static String getStringOfList(List<String> list)
    {
        StringBuilder sb = new StringBuilder();
        for (String string : list)
        {
            sb.append(string).append(" ");
        }

        return sb.toString();
    }


    public static String convertDecimalToBinaryAndLeftPadMemValue(String line)
    {
        String value = Integer.toBinaryString(Integer.parseInt(line));

        int length = 36;
        String padChar = "0";

        if (value.length() >= length)
        {
            return value;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(padChar.repeat(length - value.length()));
        sb.append(value);

        return sb.toString();
    }

    public static String getMemIndex(String line)
    {
        int startIndex = line.indexOf("[") + 1;
        int endIndex = line.lastIndexOf("]");

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
        {
            return line.substring(startIndex, endIndex);
        }
        else
        {
            sop("Characters not found or invalid range.");
            return null;
        }
    }

    public static boolean isInteger(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static List<Integer> getListOfIntegersInString(String s)
    {
        List<Integer> listOfIntegers = new ArrayList<>();

        for (char c : s.toCharArray())
        {
            if (isInteger(String.valueOf(c)))
            {
                if (!listOfIntegers.contains(Integer.valueOf(String.valueOf(c))))
                {
                    listOfIntegers.add(Integer.valueOf(String.valueOf(c)));
                }
            }
        }

        return listOfIntegers;
    }

    public static List<String> getListOfStringsFromBSVString(String s)
    {
        List<String> listOfStrings = new ArrayList<>();
        String[] stringArray = s.split("\\|");

        for (String str  : stringArray)
        {
            str = str.replaceAll(" ", "");

            listOfStrings.add(str);
        }

        return listOfStrings;
    }

    public static List<String> getListOfStringsFromCSVString(String s)
    {
        String[] stringArray = s.split(",");
        return Arrays.asList(stringArray);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}