package day18;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main
{
    static String inputFile = "src/day18/input.txt";

    static List<Long> results = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();

        int lineNo = 1;
        for (String string : inputFileList)
        {
            List<String> problemList = new ArrayList<>();

            // convert problem to a List
            for (int i = 0; i < string.length(); i++)
            {
                if (!String.valueOf(string.charAt(i)).isBlank())
                {
                    problemList.add (String.valueOf(string.charAt(i)));
                }
            }

            sop("\n====================================================================================================================================");

            sop("line " + lineNo + "\n" + getStringOfList(problemList));

            while (problemList.size() > 1)
            {
                if (isParsableToInt(problemList.get(0)) && isParsableToInt(problemList.get(2)))
                {
                    int firstNumber = Integer.parseInt(problemList.get(0));
                    String operator = problemList.get(1);
                    int secondNumber = Integer.parseInt(problemList.get(2));

                    if (operator.equals("+"))
                    {
                        problemList.set(0, String.valueOf(firstNumber + secondNumber));
                        problemList.remove(1);
                        problemList.remove(1);
                    }
                    else if (operator.equals("*"))
                    {
                        problemList.set(0, String.valueOf(firstNumber * secondNumber));
                        problemList.remove(1);
                        problemList.remove(1);
                    }
                }
                else if (problemList.contains("("))
                {
                    for (int i = 0; i < problemList.size(); i++)
                    {
                        if (problemList.get(i).equals("("))
                        {

                            if (isNextParenACloseParen(problemList.subList(i + 1, problemList.size())))
                            {
                                int openParenIndex = i;
                                int closeParenIndex = problemList.subList(i + 1, problemList.size()).indexOf(")") + i + 1;

                                String startState = getStringOfList(problemList);

                                List<String> thisParenContents = new ArrayList<>(problemList.subList(openParenIndex + 1, closeParenIndex));

                                printListString(problemList.subList(openParenIndex, closeParenIndex + 1));

                                int thisParenSize = thisParenContents.size() + 1;

                                while (thisParenContents.size() > 1)
                                {
                                    int firstNumber = Integer.parseInt(thisParenContents.get(0));
                                    String operator = thisParenContents.get(1);
                                    int secondNumber = Integer.parseInt(thisParenContents.get(2));

                                    if (operator.equals("+"))
                                    {
                                        sop(firstNumber + " + " + secondNumber + " = " + (firstNumber + secondNumber));
                                        thisParenContents.set(0, String.valueOf(firstNumber + secondNumber));
                                        thisParenContents.remove(1);
                                        thisParenContents.remove(1);
                                    }
                                    else if (operator.equals("*"))
                                    {
                                        sop(firstNumber + " * " + secondNumber + " = " + (firstNumber * secondNumber));
                                        thisParenContents.set(0, String.valueOf(firstNumber * secondNumber));
                                        thisParenContents.remove(1);
                                        thisParenContents.remove(1);
                                    }
                                }

                                problemList.set(openParenIndex, thisParenContents.get(0));

                                // remove simplified section
                                for (int x = 0; x < thisParenSize; x++)
                                {
                                    problemList.remove(openParenIndex + 1);
                                }

                                sop("\nSimplified: " + startState);
                                sop("            " + getStringOfList(problemList));

                                break;
                            }
                        }
                    }
                }

                printListString(problemList);
            }

            results.add(Long.parseLong(problemList.get(0)));
            lineNo++;
        }

        sop("\n====================================================================================================================================");
        sop("Total sum: " + results.stream().mapToLong(Long::longValue).sum());
        sop("\n\n\n");
    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

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
            sop(e.toString());
            sop();
            return false;
        }
    }
}