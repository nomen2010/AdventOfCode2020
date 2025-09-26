package day20;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main
{
    static String inputFile = "src/day20/input.txt";
    static List<Tile> tiles = new ArrayList<>();
    static int solvedTileCount = 0;

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();
        populateTiles(inputFileList);
        processTiles();


//        while (getUnSolvedTileCount() > 0)
//        {
            for (Tile tile : tiles)
            {
                if (getTileNeighborCount(tile) == 0)
                {
                    rotateTile90Degrees(tile);
                    processTiles();

                    if (isTileSolved(tile))
                    {
                        sop(tile.getTileNo() + " SOLVED");
                        tile.setSolved(true);
                        updateSolvedTileCount();
                    }
                }

                if (getTileNeighborCount(tile) == 0)
                {
                    flipTile(tile);
                    processTiles();

                    if (isTileSolved(tile))
                    {
                        sop(tile.getTileNo() + " SOLVED");
                        tile.setSolved(true);
                        updateSolvedTileCount();
                    }
                }
                sop();
//            }

            // get unsolved tile with maximum solved neighbors
        }


        sop("\n====================================================================================================================================");
        sop("Total tiles: " + tiles.size() + " | Solved tiles: " + getUnSolvedTileCount());



    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================



    public static void processTiles()
    {
        for (Tile tile1 : tiles)
        {
            for (Tile tile2 : tiles)
            {
                if (tile1.getTileNo() != tile2.getTileNo())
                {
                    if (tile1.getTopNeighbor() == 0 && tile1.getTopEdge().equals(tile2.getBottomEdge()))
                    {
                        sop("\n=======================================================================================================================");
                        sop(tile2.getTileNo() + " bottom matches "  + tile1.getTileNo() + " top");
                        printList(tile2.getTileLines());
                        printList(tile1.getTileLines());

                        tile1.setTopNeighbor(tile2.getTileNo());
                        tile2.setBottomNeighbor(tile1.getTileNo());
                    }
                    else if (tile1.getLeftNeighbor() == 0 && tile1.getLeftEdge().equals(tile2.getRightEdge()))
                    {
                        sop("\n=======================================================================================================================");
                        sop(tile2.getTileNo() + " right matches "  + tile1.getTileNo() + " left");
                        print2ListsSideBySide(tile2.getTileLines(), tile1.getTileLines());

                        tile1.setLeftNeighbor(tile2.getTileNo());
                        tile2.setRightNeighbor(tile1.getTileNo());
                    }
                    else if (tile1.getRightNeighbor() == 0 &&tile1.getRightEdge().equals(tile2.getLeftEdge()))
                    {
                        sop("\n=======================================================================================================================");
                        sop(tile1.getTileNo() + " right matches "  + tile2.getTileNo() + " left");
                        print2ListsSideBySide(tile1.getTileLines(), tile2.getTileLines());

                        tile1.setRightNeighbor(tile2.getTileNo());
                        tile2.setLeftNeighbor(tile1.getTileNo());
                    }
                    else if (tile1.getBottomNeighbor() == 0 && tile1.getBottomEdge().equals(tile2.getTopEdge()))
                    {
                        sop("\n=======================================================================================================================");
                        sop(tile1.getTileNo() + " bottom matches "  + tile2.getTileNo() + " top");
                        printList(tile1.getTileLines());
                        printList(tile2.getTileLines());

                        tile1.setBottomNeighbor(tile2.getTileNo());
                        tile2.setTopNeighbor(tile1.getTileNo());
                    }
                }
            }
        }
    }

    public static void rotateTile90Degrees(Tile tile)
    {
        sop("Rotating tile " + tile.getTileNo());
        List<String> newTileLines = new ArrayList<>();
        List<String> reverseTileLines = new ArrayList<>(tile.getTileLines());
        Collections.reverse(reverseTileLines);

        for (int i = 0; i < reverseTileLines.size(); i++)
        {
            StringBuilder newLine = new StringBuilder();
            for (String row : reverseTileLines)
            {
                newLine.append(row.charAt(i));
            }

            newTileLines.add(newLine.toString());
        }

        tile.setTileLines(newTileLines);
    }

    public static void flipTile(Tile tile)
    {
//        sop();
        sop("Flipping tile " + tile.getTileNo());
        List<String> reverseTileLines = new ArrayList<>(tile.getTileLines());
        Collections.reverse(reverseTileLines);
        tile.setTileLines(reverseTileLines);
    }

    public static class Tile
    {
        int tileNo;
        List<String> tileLines;
        String topEdge;
        String leftEdge;
        String rightEdge;
        String bottomEdge;
        int topNeighbor;
        int leftNeighbor;
        int rightNeighbor;
        int bottomNeighbor;
        boolean solved = false;

        public int getTileNo()
        {
            return tileNo;
        }

        public void setTileNo(int tileNo)
        {
            this.tileNo = tileNo;
        }

        public List<String> getTileLines()
        {
            return tileLines;
        }
        public void setTileLines(List<String> tileLines)
        {
            this.tileLines = tileLines;
        }

        public String getTopEdge()
        {
            return topEdge;
        }

        public void setTopEdge(String topEdge)
        {
            this.topEdge = topEdge;
        }

        public String getLeftEdge()
        {
            return leftEdge;
        }

        public void setLeftEdge(String leftEdge)
        {
            this.leftEdge = leftEdge;
        }

        public String getRightEdge()
        {
            return rightEdge;
        }

        public void setRightEdge(String rightEdge)
        {
            this.rightEdge = rightEdge;
        }

        public String getBottomEdge()
        {
            return bottomEdge;
        }

        public void setBottomEdge(String bottomEdge)
        {
            this.bottomEdge = bottomEdge;
        }

        public int getTopNeighbor()
        {
            return topNeighbor;
        }

        public void setTopNeighbor(int topNeighbor)
        {
            this.topNeighbor = topNeighbor;
        }

        public int getLeftNeighbor()
        {
            return leftNeighbor;
        }

        public void setLeftNeighbor(int leftNeighbor)
        {
            this.leftNeighbor = leftNeighbor;
        }

        public int getRightNeighbor()
        {
            return rightNeighbor;
        }

        public void setRightNeighbor(int rightNeighbor)
        {
            this.rightNeighbor = rightNeighbor;
        }

        public int getBottomNeighbor()
        {
            return bottomNeighbor;
        }

        public void setBottomNeighbor(int bottomNeighbor)
        {
            this.bottomNeighbor = bottomNeighbor;
        }

        public boolean isSolved()
        {
            return solved;
        }

        public void setSolved(boolean solved)
        {
            this.solved = solved;
        }

        private Tile(int tileNo, List<String> tileLines)
        {
            this.tileNo = tileNo;
            this.tileLines = tileLines;
        }

        private void initializeObject()
        {
            this.topEdge = tileLines.get(0);

            // left and right edge
            StringBuilder leftEdgeSb = new StringBuilder();
            StringBuilder rightEdgeSb = new StringBuilder();

            for (String line : tileLines)
            {
                leftEdgeSb.append(line.charAt(0));
                rightEdgeSb.append(line.charAt(line.length() - 1));
            }

            this.leftEdge = leftEdgeSb.toString();
            this.rightEdge = rightEdgeSb.toString();

            this.bottomEdge = tileLines.get(tileLines.size() - 1);
        }
    }

    public static void populateTiles(List<String> list)
    {
        boolean isNewTile = false;
        int tileNo = 0;
        List<String> tileLines = new ArrayList<>();
        int rowNumber = 0;

        for (String s : list)
        {
            if (isNewTile)
            {
                tileNo = 0;
                tileLines.clear();
                isNewTile = false;
            }
            
            if (!s.isEmpty() && s.startsWith("Tile"))
            {
                tileNo = Integer.parseInt(s.replaceAll("[\\D]", ""));
            }
            else if (!s.isEmpty() && (s.startsWith(".") || s.startsWith("#")))
            {
                tileLines.add(s);
            }
            else // add the tile and reset
            {
                tiles.add(new Tile(tileNo, new ArrayList<>(tileLines)));
                isNewTile = true;
            }

            rowNumber++;

            if (rowNumber == list.size())
            {
                tiles.add(new Tile(tileNo, new ArrayList<>(tileLines)));
            }
        }

        for (Tile tile : tiles)
        {
            tile.initializeObject();
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

    public static Tile getTileByTileNo(int tileNo)
    {
        for (Tile tile : tiles)
        {
            if (tile.getTileNo() == tileNo)
            {
                return tile;
            }
        }
        return null;
    }

    public static int getTileNeighborCount(Tile tile)
    {
        int count = 0;

        if (tile.getTopNeighbor() > 0)
        {
            count++;
        }

        if (tile.getLeftNeighbor() > 0)
        {
            count++;
        }

        if (tile.getRightNeighbor() > 0)
        {
            count++;
        }

        if (tile.getBottomNeighbor() > 0)
        {
            count++;
        }

        return count;
    }

    public static boolean isTileSolved(Tile tile)
    {
        if (tile.getTopNeighbor() == 0)
        {
            return false;
        }

        if (tile.getLeftNeighbor() == 0)
        {
            return false;
        }

        if (tile.getRightNeighbor() == 0)
        {
            return false;
        }

        if (tile.getBottomNeighbor() == 0)
        {
            return false;
        }

        return true;
    }

    public static int getUnSolvedTileCount()
    {
        int count = 0;

        for (Tile tile : tiles)
        {
            if (!tile.isSolved())
            {
                count++;
            }
        }

        return count;
    }

    public static void updateSolvedTileCount()
    {
        int count = 0;

        for (Tile tile : tiles)
        {
            if (tile.isSolved())
            {
                count++;
            }
        }

        setSolvedTileCount(count);
    }

    public static int getSolvedTileCount()
    {
        return solvedTileCount;
    }
    public static void setSolvedTileCount(int solvedTileCount)
    {
        Main.solvedTileCount = solvedTileCount;
    }

}