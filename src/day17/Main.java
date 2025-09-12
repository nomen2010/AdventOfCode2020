package day17;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main
{
    static String inputFile = "src/day17/input.txt";

    // Map = <z-index, comma-separated strings of x-lines
//    static HashMap<Integer, String> zLayers = new HashMap<>();
    static Set<Cube> neighbors = new HashSet<>();

    static Set<Cube> activeCubes = new HashSet<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();

        initialize(inputFileList);

        sop();

    for (int i = 1; i <= 6; i++)
    {
        Set<Cube> nextActiveCubes = new HashSet<>();
        Set<Cube> cubesToConsider = new HashSet<>();

        for (Cube cube : activeCubes)
        {
            cubesToConsider.add(cube);
            cubesToConsider.addAll(getNeighbors(cube));
        }

        for (Cube cube : cubesToConsider)
        {
            int activeNeighborCount = 0;
            for (Cube neighbor : getNeighbors(cube))
            {
                if (activeCubes.contains(neighbor))
                {
                    activeNeighborCount++;
                }
            }

            boolean isActive = activeCubes.contains(cube);

            if (isActive && (activeNeighborCount == 2 || activeNeighborCount == 3))
            {
                nextActiveCubes.add(cube);
            }
            else if (!isActive && activeNeighborCount == 3)
            {
                nextActiveCubes.add(cube);
            }
        }

        activeCubes = nextActiveCubes;

    }

        sop(String.valueOf(activeCubes.size()) + " active cubes after 6 cycles");







    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

    private static class Cube
    {
        int x;
        int y;
        int z;

        public int getX()
        {
            return x;
        }
        public void setX(int x)
        {
            this.x = x;
        }
        public int getY()
        {
            return y;
        }
        public void setY(int y)
        {
            this.y = y;
        }
        public int getZ()
        {
            return z;
        }
        public void setZ(int z)
        {
            this.z = z;
        }

        private Cube(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static void initialize(List<String> list)
    {
        int y = 0;
        int z = 0;

        for (String line : list)
        {
            int x = 0;
            for (char c : line.toCharArray())
            {
                if (c == '#')
                {
                    activeCubes.add(new Cube(x, y, z));
                }

                x+=1;
            }

            y-=1;
        }

        sop();
    }

    static Set<Cube> getNeighbors(Cube cube)
    {
        Set<Cube> neighbors = new HashSet<>();

        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                for (int dz = -1; dz <= 1; dz++)
                {
                    if (dx == 0 && dy == 0 && dz == 0)
                    {
                        continue;
                    }
                    neighbors.add(new Cube(cube.getX() + dx, cube.getY() + dy, cube.getZ() + dz));
                }
            }
        }

        return neighbors;
    }

//    static void printZLayers()
//    {
//        List<Cube> zSortedCubes = new ArrayList<Cube>(activeCubes);
//        Collections.sort(zSortedCubes, Comparator.comparingInt(Cube::getZ));
//
//        for (Cube cube : zSortedCubes)
//        {
//
//        }
//    }

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