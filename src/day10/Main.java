package day10;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main
{
    static String inputFile = "src/day10/input.txt";
    static List<Long> numbers = new ArrayList<>();
    static List<Integer> inputFileList = new ArrayList<>();
    static List<Integer> differenceList = new ArrayList<>();
    static HashMap<Integer, Integer> joltageMap = new HashMap<>();

    public static void main(String[] args) throws IOException
    {
        importInputFileIntoInputFileList();

        // add the starting joltage (charging outlet) of 0
        inputFileList.add(0);

        // sort inputFileList by joltage ascending
        Collections.sort(inputFileList);

        // add to the list the joltage of my device's built-in adapter
        inputFileList.add(Collections.max(inputFileList) + 3);

        // populate list of differences in each joltage in inputFileList
        for (int i = 0; i < inputFileList.size() - 1; i++)
        {
            differenceList.add(inputFileList.get(i + 1) - inputFileList.get(i));
        }

        sop("number of 1's in differenceList: " + Collections.frequency(differenceList, 1) + " | number of 3's in differenceList: " + Collections.frequency(differenceList, 3));
        sop("The number of 1-jolt differences multiplied by the number of 3-jolt differences: " + (Collections.frequency(differenceList, 1) * Collections.frequency(differenceList, 3)));

        // print stuff for debugging
//        sop("inputFileList: " + inputFileList.size() + " | differenceList: " + differenceList.size() + "\n\n");
//        for (int i = 0; i < inputFileList.size(); i++)
//        {
//            if (i < inputFileList.size()-1)
//            {
//                sop("element " + i + ": " + inputFileList.get(i) + " " + differenceList.get(i));
//            }
//            else
//            {
//                sop("element " + i + ": " + inputFileList.get(i));
//            }
//        }
    }

    public static void importInputFileIntoInputFileList() throws IOException
    {
        BufferedReader bf = new BufferedReader(new FileReader(inputFile));
        String readLine;

        while ((readLine = bf.readLine()) != null)
        {
            inputFileList.add(Integer.valueOf(readLine));
        }

        bf.close();
    }

    public static void sop(String string)
    {
        System.out.println(string);
    }
}