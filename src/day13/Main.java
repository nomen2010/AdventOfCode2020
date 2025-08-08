package day13;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main
{
    static String inputFile = "src/day13/input.txt";
    static List<Integer> busIdList = new ArrayList<>();
    static int startTime = 0;

    public static void main(String[] args) throws IOException
    {
        importInputFileIntoVars();

        sop("I'll be ready to leave at " + startTime);

        HashMap<Integer, Integer> busIdDepartureTimeMap = new HashMap<>();

        for (int busId : busIdList)
        {
            int mult = startTime / busId;
            int nextBusDepartureTime = (mult + 1) * busId;
            busIdDepartureTimeMap.put(busId, nextBusDepartureTime);
            sop("My first available ride with busId " + busId + " will be at " + nextBusDepartureTime);
        }

        int earliestBusDepartureTime = Collections.min(busIdDepartureTimeMap.values());
        int earliestBusDepartureTimeBusId = 0;

        for (HashMap.Entry<Integer, Integer> entry : busIdDepartureTimeMap.entrySet())
        {
            if (earliestBusDepartureTime == entry.getValue())
            {
                earliestBusDepartureTimeBusId = entry.getKey();
                break;
            }
        }

        sop("The earliest busId that I can use is " + earliestBusDepartureTimeBusId);
        sop("I'll have to wait " + (earliestBusDepartureTime - startTime) + " minutes");
        sop("The answer is " + (earliestBusDepartureTimeBusId * (earliestBusDepartureTime - startTime)));
    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

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
    public static void importInputFileIntoVars() throws IOException
    {
        String busIdListString = null;

        BufferedReader bf = new BufferedReader(new FileReader(inputFile));
        String readLine;

        int lineNo = 0;

        while ((readLine = bf.readLine()) != null)
        {
            if (lineNo == 0)
            {
                startTime = Integer.parseInt(readLine);
            }
            else
            {
                busIdListString = readLine;
            }

            lineNo++;
        }

        bf.close();

        // populate busIdList
        String[] busIdListStringArray = busIdListString.split(",");

        for (String busId : busIdListStringArray)
        {
            if (!busId.equals("x"))
            {
                busIdList.add(Integer.valueOf(busId));
            }
        }

        sop();

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
}