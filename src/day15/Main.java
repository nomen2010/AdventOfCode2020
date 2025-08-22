package day15;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main
{
    static String inputFile = "src/day15/input.txt";

    public static void main(String[] args) throws IOException
    {
        List<Long> inputFileList = importInputFileIntoInputFileList();
        Long nextValue = null;

        int lineNo = 0;
        sop();

        for (int n = inputFileList.size() + 1; n <= 2020; n++)
        {
            Long lastValue = inputFileList.get(inputFileList.size() - 1);

            List<Long> subList = inputFileList.subList(0, inputFileList.size() - 1);

            List<Integer> lastValueIndexList = new ArrayList<>();

            for (int i = 0; i < subList.size(); i++)
            {
                if (subList.get(i).equals(lastValue))
                {
                    lastValueIndexList.add(i);
                }
            }

            if (lastValueIndexList.size() == 0)
            {
                nextValue = 0L;
            }
            else
            {
                // get last index of lastValue from lastValueIndexList
                int lastIndex = lastValueIndexList.get(lastValueIndexList.size() - 1);

                nextValue = Long.valueOf(inputFileList.size() - 1) - lastIndex;
            }

            sop("turn " + n + " | nextValue " + nextValue);
            inputFileList.add(nextValue);
            sop();
        }
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
    public static List<Long> importInputFileIntoInputFileList() throws IOException
    {
        List<Long> list = new ArrayList<>();

        BufferedReader bf = new BufferedReader(new FileReader(inputFile));
        String readLine;

        while ((readLine = bf.readLine()) != null)
        {
            list.add(Long.valueOf(readLine));
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
}