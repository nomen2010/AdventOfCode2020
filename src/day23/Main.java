package day23;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    static String inputFile = "src/day23/input.txt";
    static List<Integer> cupList = new ArrayList<>();
    static int currentCupIndex = 0;
    static int destinationCupLabel = 0;
    static List<Integer> currentPickedUpCupList = new ArrayList<>();
    static int moveNo = 1;


    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();
        populateStartingCupList(inputFileList.get(0));
        sop();

        while(moveNo <= 10)
        {
            printMoveNo();
            printCups(cupList);

            pickupNextThreeCups();
            sop();
            moveNo++;
            currentPickedUpCupList.clear();
        }
    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

    public static void populateStartingCupList(String inputString)
    {
        for (int i = 0; i < inputString.length(); i++)
        {
            String thisChar = String.valueOf(inputString.charAt(i));
            cupList.add(Integer.valueOf(thisChar));
        }
    }

//    public static void updateCurrentCup()
//    {
//        if (currentCupIndex >= cupList.size())
//        {
//            currentCupIndex = 0;
//        }
//        else
//        {
//            currentCupIndex++;
//        }
//    }

    public static void pickupNextThreeCups()
    {
        int count = 0;
        int pickupIndex = currentCupIndex + 1;
        List<Integer> pickupCupList = new ArrayList<>();
        List<Integer> newCupList = new ArrayList<>();

        destinationCupLabel = cupList.get(currentCupIndex) - 1;
//        if (currentCupIndex == 0)
//        {
//            destinationCupIndex = cupList.size() - 1;
//        }
//        else
//        {
//            destinationCupIndex = currentCupIndex - 1;
//        }

        while (count < 3)
        {
            if (pickupIndex >= cupList.size())
            {
                pickupIndex = 0;
            }

            pickupCupList.add(cupList.get(pickupIndex));

            count++;
            pickupIndex++;
        }

        currentPickedUpCupList.addAll(pickupCupList);
        printPickUpList(currentPickedUpCupList);
        sop("destination: " + destinationCupLabel);

        for (Integer cup : cupList)
        {
            if (!pickupCupList.contains(cup))
            {
                if (cup == destinationCupLabel)
                {
                    newCupList.add(cup);
                    newCupList.addAll(pickupCupList);
                }
                else
                {
                    newCupList.add(cup);
                }
            }
        }

        cupList = new ArrayList<>(newCupList);

        currentCupIndex = destinationCupLabel;

        sop();
    }









    public static int getPreviousCupIndex()
    {
        if (currentCupIndex == 0)
        {
            return currentCupIndex = cupList.size() - 1;
        }

        return currentCupIndex--;
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
    public static void printListIntegers(List<Integer> list)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++)
        {
            if (i == currentCupIndex)
            {
                sb.append("(" + list.get(i) + ")");
            }
            else
            {
                sb.append(list.get(i));
            }

            if (i < list.size() - 1)
            {
                sb.append(", ");
            }
        }

        sop(sb.toString());
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

    public static void printMoveNo()
    {
        sop("-- move " + moveNo + " --");
    }

    public static void printCups(List<Integer> list)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++)
        {
            if (i == currentCupIndex)
            {
                sb.append("(" + list.get(i) + ")");
            }
            else
            {
                sb.append(list.get(i));
            }

            if (i < list.size() - 1)
            {
                sb.append(" ");
            }
        }

        sop("cups: " + sb);
    }

    public static void printPickUpList(List<Integer> list)
    {
        StringBuilder sb = new StringBuilder("pick up: ");

        for (int i = 0; i < list.size(); i++)
        {
            sb.append(list.get(i));

            if (i < list.size() - 1)
            {
                sb.append(", ");
            }
        }

        sop(sb.toString());
    }




}