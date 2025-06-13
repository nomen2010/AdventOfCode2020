package day12;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    static String inputFile = "src/day12/input.txt";
    static String currentHeading = "E";
    static String lastCurrentHeading = currentHeading; // for debugging
    static int currentHeadingDegrees = 90;
    static int lastCurrentHeadingDegrees = currentHeadingDegrees; // for debugging
    static int currentX = 0;
    static int currentY = 0;

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();

        for (String line : inputFileList)
        {
            lastCurrentHeading = currentHeading;
            lastCurrentHeadingDegrees = currentHeadingDegrees;

            String action = String.valueOf(line.charAt(0));
            int value = Integer.parseInt(line.substring(1));

            switch (action)
            {
                case "N":
                    currentY += value;
                    break;
                case "S":
                    currentY -= value;
                    break;
                case "E":
                    currentX += value;
                    break;
                case "W":
                    currentX -= value;
                    break;
                case "L":
                    updateHeading(action, value);
                    break;
                case "R":
                    updateHeading(action, value);
                    break;
                case "F":
                    switch (currentHeading)
                    {
                        case "N":
                            currentY += value;
                            break;
                        case "S":
                            currentY -= value;
                            break;
                        case "E":
                            currentX += value;
                            break;
                        case "W":
                            currentX -= value;
                            break;
                    }
                    break;
            }
//            sopStatus(line); // for debugging
        }

        sop();
        sop("Final Manhattan distance: " + (Math.abs(currentX) + Math.abs(currentY)));
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
    public static List importInputFileIntoInputFileList() throws IOException
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
     * Prints an empty line to the console.
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

    public static void updateHeading(String direction, int degrees)
    {
        List<Integer> validDegrees = List.of(0, 90, 180, 270);
        if (!validDegrees.contains(degrees))
        {
            sop("Error degrees: " + degrees);
        }

        if (direction.equals("L"))
        {
            currentHeadingDegrees -= degrees;
            if (currentHeadingDegrees < 0)
            {
                currentHeadingDegrees += 360;
            }
        }
        else if (direction.equals("R"))
        {
            currentHeadingDegrees += degrees;
            if (currentHeadingDegrees >= 360)
            {
                currentHeadingDegrees -= 360;
            }
        }

        switch (currentHeadingDegrees)
        {
            case 0:
                currentHeading = "N";
                break;
            case 90:
                currentHeading = "E";
                break;
            case 180:
                currentHeading = "S";
                break;
            case 270:
                currentHeading = "W";
                break;
        }
    }

    public static void sopStatus(String line)
    {
        String currentHeadingX = currentX >= 0 ? "east" : "west";
        String currentHeadingY = currentY >= 0 ? "north" : "south";
        String headingStatus = !lastCurrentHeading.equals(currentHeading) ? "\uD83E\uDDED " + lastCurrentHeading + " (" + lastCurrentHeadingDegrees + "°) > "
            + currentHeading + " (" + currentHeadingDegrees + "°)" : currentHeading + " (" + currentHeadingDegrees + "°)";
        sop(line + ", " + headingStatus + ", " + currentHeadingX + " " + Math.abs(currentX) + ", " + currentHeadingY + " " + Math.abs(currentY));
    }
}