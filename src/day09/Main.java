package day09;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    static List<Long> numbers = new ArrayList<>();
    static int historyLength = 25;
    static List<Long> inputFileList = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        importInputFileIntoInputFileList();

        initializeNumbersList();

        List<Long> sumList = getSumList(numbers);

        for (int i = historyLength; i < inputFileList.size(); i++)
        {
            if (!sumList.contains(inputFileList.get(i)))
            {
                System.out.println("Invalid number: " + inputFileList.get(i) + " (line number " + (i + 1) + ")");
                break;
            }

//            System.out.println("Removing " + numbers.get(0) + " adding " + inputFileList.get(i) + " (lineNo " + (i + 1) + ")");
            numbers.remove(0);
            numbers.add(inputFileList.get(i));
            sumList = getSumList(numbers);
        }

    }

    public static List<Long> getSumList(List<Long> numbers)
    {
        List<Long> sumList = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++)
        {
            for (int j = i + 1; j < numbers.size(); j++)
            {
                sumList.add(numbers.get(i) + numbers.get(j));
            }
        }

        return sumList;
    }

    public static void initializeNumbersList()
    {
        int i = 0;
        while (numbers.size() < historyLength)
        {
            numbers.add(inputFileList.get(i));
            i++;
        }
    }

    public static void importInputFileIntoInputFileList() throws IOException
    {
        BufferedReader bf = new BufferedReader(new FileReader("/Users/abradley/IdeaProjects/Advent2020/src/day09/input.txt"));
        String readLine;

        while ((readLine = bf.readLine()) != null)
        {
            inputFileList.add(Long.valueOf(readLine));
        }

        bf.close();
    }
}