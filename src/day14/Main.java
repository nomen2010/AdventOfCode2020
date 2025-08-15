package day14;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main
{
    static String inputFile = "src/day14/input.txt";
    static String mask = "";
    static List<String> outputList = new ArrayList<>();
    static HashMap<Integer, String> outputBinaryMap = new HashMap<>();
    static HashMap<Integer, Long> outputDecimalMap = new HashMap<>();
    static List<Integer> allDecimalList = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();

        int lineNo = 0;
        sop();

        for (String line : inputFileList)
        {
            if (line.startsWith("mask"))
            {
                updateMask(line);
            }
            else if (line.startsWith("mem"))
            {
                processMemLine(line);
            }

            lineNo++;
        }

        Long sum = 0L;
        int entryCount = 1;
        for (String value : outputBinaryMap.values())
        {
            sum += Long.parseLong(value, 2);
        }

        sop("Sum = " + sum);
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

    public static void updateMask(String line)
    {
        String[] parts = line.split(" ");
        mask = parts[parts.length - 1];
    }

    public static void processMemLine(String line)
    {
        String[] parts = line.split(" ");
        String memIndex = getMemIndex(parts[0]);
        Integer memValueDecimal = Integer.valueOf(parts[2]);

        String memValueBinary = convertDecimalToBinaryAndLeftPadMemValue(parts[2]);

        StringBuilder sbBinary = new StringBuilder();

        for (int i = 0; i < memValueBinary.length(); i++)
        {
            if (!"X".equals(String.valueOf(mask.charAt(i))) && memValueBinary.charAt(i) != mask.charAt(i))
            {
                sbBinary.append(mask.charAt(i));
            }
            else
            {
                sbBinary.append(memValueBinary.charAt(i));
            }
        }

        outputList.add(sbBinary.toString());
        outputBinaryMap.put(Integer.parseInt(memIndex), sbBinary.toString());
        outputDecimalMap.put(Integer.parseInt(memIndex), Long.parseLong(sbBinary.toString(), 2));
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