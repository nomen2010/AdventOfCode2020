package day16;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main
{
    static String inputFile = "src/day16/input.txt";
    static List<List<Integer>> validRangeList = new ArrayList<>();
    static List<Integer> invalidValueList = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();

        populateValidRangeList(inputFileList);

        int nearbyTicketListStartLineNo = inputFileList.indexOf("nearby tickets:") + 2;

        List<String> nearbyTicketsList = inputFileList.subList(inputFileList.indexOf("nearby tickets:") + 1, inputFileList.size());

        processNearbyTickets(nearbyTicketsList, nearbyTicketListStartLineNo);

        int scanErrorRate = 0;

        for (Integer value : invalidValueList)
        {
            scanErrorRate += value;
        }

        sop();
        sop("My ticket scanning error rate is " + scanErrorRate);

    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

    public static void populateValidRangeList(List<String> list)
    {
        int lineNo = 1;
        sop();

        for (String line : list)
        {
            if (line.contains("or"))
            {
                StringBuilder sb = new StringBuilder(lineNo + " - Building valid ranges: ");

                List<Integer> thisLineIntegers = new ArrayList<>();

                try (Scanner scanner = new Scanner(line))
                {
                    scanner.useDelimiter("\\D+");

                    while (scanner.hasNextInt())
                    {
                        thisLineIntegers.add(scanner.nextInt());
                    }
                }
                catch (NoSuchElementException e)
                {
                    sop(e.toString());
                }

                if (thisLineIntegers.size() != 4)
                {
                    sop("This line does not have 4 integers.");
                    System.exit(1);
                }

                sb.append(thisLineIntegers.get(0) + "-" + thisLineIntegers.get(1) + " or " + thisLineIntegers.get(2) + "-" + thisLineIntegers.get(3));
                sop(sb.toString());

                validRangeList.add(Arrays.asList(thisLineIntegers.get(0), thisLineIntegers.get(1)));
                validRangeList.add(Arrays.asList(thisLineIntegers.get(2), thisLineIntegers.get(3)));
            }

            lineNo++;
        }
    }

    public static void processNearbyTickets(List<String> nearbyTicketsList, int beginIndex)
    {
        int lineNo = beginIndex;
        sop();

        for (String line : nearbyTicketsList)
        {
            StringBuilder sb = new StringBuilder(lineNo + " - Processing nearby ticket ");
            sop(sb.toString());

            try (Scanner scanner = new Scanner(line))
            {
                scanner.useDelimiter("\\D+");

                while (scanner.hasNextInt())
                {
                    int nextInt = scanner.nextInt();
                    boolean isValid = false;

                    for (List<Integer> integers : validRangeList)
                    {
                        int rangeStart = integers.get(0);
                        int rangeStop = integers.get(1);

                        if (rangeStart <= nextInt && nextInt <= rangeStop)
                        {
                            isValid = true;
                            break;
                        }
                    }

                    if (!isValid)
                    {
                        invalidValueList.add(nextInt);
                    }
                }
            }
            catch (NoSuchElementException e)
            {
                sop(lineNo + e.toString());
            }

            lineNo++;
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
            sop(e.toString());
            sop();
            return false;
        }
    }
}