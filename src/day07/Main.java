package day07;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = new ArrayList<>();
        Map<Integer, Integer> lineNoCountMap = new HashMap<>();

        // Import input file into the string array =============================
        BufferedReader bf = new BufferedReader(new FileReader("/Users/abradley/IdeaProjects/Advent2020/src/day07/input.txt"));
        String line = bf.readLine();

        while (line != null)
        {
            inputFileList.add(line);
            line = bf.readLine();
        }

        bf.close();
        // =================================================================

        int lineNo = 0;
        int accumulator = 0;
        String inputLine;
        String action;
        String op;
        int num;
        String delimiter = " ";
        int loopCount = 0;

        while (true)
        {
            loopCount++;
            if (lineNo >= inputFileList.size() || lineNo < 0)
            {
                System.out.println("lineNo " + lineNo + " does not exist in inputFileList");
                break;
            }

            String[] values = inputFileList.get(lineNo).split(delimiter);
            action = values[0];
            op = String.valueOf(values[1].charAt(0));
            num = Integer.parseInt(values[1].substring(1));

            // Handle repeated instruction =======================
            if (lineNoCountMap.containsKey(lineNo))
            {
                if (lineNoCountMap.get(lineNo) == 1)
                {
                    System.out.println("Loop " + loopCount + ": (lineNo " + lineNo + ") " + inputFileList.get(lineNo) + " is now being executed a second time. accumulator = " + accumulator);
                    break;
                }
                lineNoCountMap.put(lineNo, lineNoCountMap.get(lineNo) + 1); // this never gets hit, but maybe useful for future enhancements
            }
            else
            {
                lineNoCountMap.put(lineNo, 1);
            }
            //=============================================================

            if ("nop".equals(action))
            {
                lineNo++;
                continue;
            }

            if ("acc".equals(action))
            {
                if ("+".equals(op))
                {
                    accumulator += num;
                }
                else if ("-".equals(op))
                {
                    accumulator -= num;
                }
                lineNo++;
                continue;
            }

            if ("jmp".equals(action))
            {
                if ("+".equals(op))
                {
                    lineNo += num;
                }
                else if ("-".equals(op))
                {
                    lineNo -= num;
                }
            }
        }
    }
}