package day11;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    static String inputFile = "src/day11/input.txt";
    static char e = 'L'; // empty chair
    static char o = '#'; // occupied chair
    static char f = '.'; // floor
    static int maxAdjacentOccupiedSeats = 3; // any more than this value and an occupied seat will be emptied

    public static void main(String[] args) throws IOException
    {
        List<String> lines = importInputFileIntoInputFileList();
        List<String> lastLines = new ArrayList<>();
        int roundCount = 1;

        sop();
        while (true)
        {
            lastLines = List.copyOf(lines);

            for (int lineIndex = 0; lineIndex < lastLines.size(); lineIndex++)
            {
            String line = lastLines.get(lineIndex);

                for (int charIndex = 0; charIndex < line.length(); charIndex++)
                {
                    // occupy empty seats
                    char seat = line.charAt(charIndex);
                    if (seat == e)
                    {
                        boolean occupyTheSeat = true;
                        int beginIndex = charIndex == 0 ? charIndex : charIndex - 1;
                        int endIndex = charIndex == line.length() - 1 ? charIndex + 1 : charIndex + 2;

                        // process previous line
                        if (lineIndex > 0) // process previous line
                        {
                            String prevLineAdjacent = lastLines.get(lineIndex - 1).substring(beginIndex, endIndex);

                            if (prevLineAdjacent.contains(String.valueOf(o)))
                            {
                                occupyTheSeat = false;
                            }
                        }

                        // process current line
                        if (occupyTheSeat)
                        {
                            String currLineAdjacent = line.substring(beginIndex, endIndex);

                            if (currLineAdjacent.contains(String.valueOf(o)))
                            {
                                occupyTheSeat = false;
                            }
                        }

                        // process next line
                        if (occupyTheSeat && lineIndex < lastLines.size() - 1)
                        {
                            String nextLineAdjacent = lastLines.get(lineIndex + 1).substring(beginIndex, endIndex);

                            if (nextLineAdjacent.contains(String.valueOf(o)))
                            {
                                occupyTheSeat = false;
                            }
                        }

                        if (occupyTheSeat)
                        {
                            StringBuilder sb = new StringBuilder(lines.get(lineIndex));
                            sb.setCharAt(charIndex, o);
                            lines.set(lineIndex, sb.toString());
                        }
                    }

                    // empty occupied seats
                    if (seat == o)
                    {
                        {
                            int adjacentOccupiedSeats = 0;
                            int beginIndex = charIndex == 0 ? charIndex : charIndex - 1;
                            int endIndex = charIndex == line.length() - 1 ? charIndex + 1 : charIndex + 2;

                            // process previous line
                            if (lineIndex > 0) // process previous line
                            {
                                String prevLineAdjacent = lastLines.get(lineIndex - 1).substring(beginIndex, endIndex);
                                adjacentOccupiedSeats += prevLineAdjacent.chars().filter(ch -> ch == o).count();
                            }

                            // process current line
                            if (adjacentOccupiedSeats <= maxAdjacentOccupiedSeats)
                            {
                                String currLineAdjacent = line.substring(beginIndex, endIndex);
                                adjacentOccupiedSeats += currLineAdjacent.chars().filter(ch -> ch == o).count();
                                adjacentOccupiedSeats--; // subtract 1 to eliminate the current seat, which, here, is always occupied
                            }

                            // process next line
                            if (lineIndex < lastLines.size() - 1 && adjacentOccupiedSeats <= maxAdjacentOccupiedSeats)
                            {
                                String nextLineAdjacent = lastLines.get(lineIndex + 1).substring(beginIndex, endIndex);
                                adjacentOccupiedSeats += nextLineAdjacent.chars().filter(ch -> ch == o).count();
                            }

                            if (adjacentOccupiedSeats > maxAdjacentOccupiedSeats)
                            {
                                StringBuilder sb = new StringBuilder(lines.get(lineIndex));
                                sb.setCharAt(charIndex, e);
                                lines.set(lineIndex, sb.toString());
                            }
                        }
                    }
                }
            }

            // debug printing
//            for (int i = 0; i < lines.size(); i++)
//            {
//                sop(lastLines.get(i) + "    " + lines.get(i));
//            }
//            sop();

            if (lastLines.equals(lines))
            {
                break;
            }

            roundCount++;
        }

//        sop();
        sop("Last round: " + roundCount);
        sop("Occupied seats: " + charCount(lines, o));
        sop("Empty seats: " + charCount(lines, e));
        sop("Floor: " + charCount(lines, f));
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
}