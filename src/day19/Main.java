package day19;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    static String inputFile = "src/day19/input.txt";
    static int ruleCount = 0;
    static List<Rule> rules = new ArrayList<>();
    static int solvedCount = 0;
    static List<String> messages = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();
        populateRules(inputFileList);
        populateMessasges(inputFileList);

        while (rules.size() > solvedCount)
        {
            for (Rule rule : rules)
            {
                if (!rule.isSolved() && getSolvedRuleNos().containsAll(getListOfIntegersInString(rule.getRuleString())))
                {
                    // I should be able to solve this rule

//                    for (Integer ruleNo : getListOfIntegersInString(rule.getRuleString()))
                    List<String> updatedSegments = new ArrayList<>();
                    for (String segment : rule.getStringSegments())
                    {
                        List<Integer> segmentInts = getListOfIntegersInString(segment);

                        if (segmentInts.size() == 3 && getRuleByRuleNo(segmentInts.get(0)).isSimpleReplace()
                            && !getRuleByRuleNo(segmentInts.get(1)).isSimpleReplace() && getRuleByRuleNo(segmentInts.get(2)).isSimpleReplace())
                        {
                            List<String> results = getAllCombinations(getRuleByRuleNo(segmentInts.get(0)), getRuleByRuleNo(segmentInts.get(1)));

                            List<String> finalResults = new ArrayList<>();

                            for (String result : results)
                            {
                                finalResults.add(result + getRuleByRuleNo(segmentInts.get(2)).getSolvedSegments().get(0));
                            }

                            rule.setSolvedSegments(finalResults);
                        }
                        else
                        {
                            boolean simpleReplace = true;

                            for (Integer segmentInt : segmentInts)
                            {
                                if (!getRuleByRuleNo(segmentInt).isSimpleReplace())
                                {
                                    simpleReplace = false;
                                }
                            }

                            if (simpleReplace)
                            {
                                for (Integer segmentInt : segmentInts)
                                {
                                    if (getRuleByRuleNo(segmentInt).isSimpleReplace())
                                    {
                                        String insertString = getRuleByRuleNo(segmentInt).getRuleString().replaceAll(" ", "");
                                        segment = segment.replaceAll(String.valueOf(segmentInt), insertString);
                                    }
                                }

                                updatedSegments.add(segment);
                                rule.setSolvedSegments(updatedSegments);
                            }
                            else if (!simpleReplace)
                            {
                                // get first source solved segment of source rule
                                List<String> results = getAllCombinations(getRuleByRuleNo(segmentInts.get(0)), getRuleByRuleNo(segmentInts.get(1)));

                                addSolvedRuleSegmentsToRule(rule, results);
                            }
                        }
                    }




                    int remainingIntCount = 0;

                    for (String segment : rule.getSolvedSegments())
                    {
                        remainingIntCount =+ getListOfIntegersInString(segment).size();
                    }

                    if (remainingIntCount == 0)
                    {
                        rule.setSolved(true);
                        sop("ruleNo " + rule.getRuleNo() + " is solved: " + getStringOfList(rule.getSolvedSegments()));
                    }

//                    break;

                }

                solvedCount = getSolvedRuleCount();
            }
        }

        sop("\n====================================================================================================================================");

        // Solve for final count
        int matchingMessageCount = 0;

        for (String message : messages)
        {
            for (String result : getRuleByRuleNo(0).getSolvedSegments())
            {
                if (result.equals(message))
                {
                    matchingMessageCount++;
                }
            }
        }

        sop("Total matching messages: " + matchingMessageCount);

    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================

    public static class Rule
    {
        int ruleNo;
        String ruleString;
//        String ruleSolved;
        boolean solved = false;
        boolean simpleReplace = false;
        List<String> stringSegments;
        List<String> solvedSegments;

        public int getRuleNo()
        {
            return ruleNo;
        }
        public void setRuleNo(int ruleNo)
        {
            this.ruleNo = ruleNo;
        }

        public String getRuleString()
        {
            return ruleString;
        }
        public void setRuleString(String ruleString)
        {
            this.ruleString = ruleString;
        }

        public boolean isSolved()
        {
            return solved;
        }
        public void setSolved(boolean solved)
        {
            this.solved = solved;
        }

        public boolean isSimpleReplace()
        {
            return simpleReplace;
        }
        public void setSimpleReplace(boolean simpleReplace)
        {
            this.simpleReplace = simpleReplace;
        }

        public List<String> getStringSegments()
        {
            return stringSegments;
        }
        public void setStringSegments(List<String> stringSegments)
        {
            this.stringSegments = stringSegments;
        }

        public List<String> getSolvedSegments()
        {
            return solvedSegments;
        }
        public void setSolvedSegments(List<String> solvedSegments)
        {
            this.solvedSegments = solvedSegments;
        }

        private Rule(int ruleNo, String ruleString)
        {
            this.ruleNo = ruleNo;
            this.ruleString = ruleString;
        }

        private void initializeObject()
        {
            if (!isEmpty(ruleString))
            {
                stringSegments = getListOfStringsFromBSVString(ruleString);

                if (isSimpleReplace())
                {
                    solvedSegments = getListOfStringsFromBSVString(ruleString);
                }
                else
                {
                    solvedSegments = new ArrayList<>();
                }

                if (!isInteger(String.valueOf(ruleString.charAt(0))))
                {
//                    setRuleSolved(ruleString);
                    setSolved(true);
                }

                if (stringSegments.size() == 1)
                {
                    if (stringSegments.get(0).length() == 1)
                    {
                        setSimpleReplace(true);
                    }
                }
            }


        }
    }

    public static void populateRules(List<String> list)
    {
        for (String s : list)
        {
            if (!s.isEmpty() && isInteger(String.valueOf(s.charAt(0))))
            {
                String ruleString = s.replaceAll("\"", "").substring(s.indexOf(":") + 2);

                rules.add(new Rule(Integer.parseInt(String.valueOf(s.charAt(0))), ruleString));
            }
        }

        for (Rule rule : rules)
        {
            rule.initializeObject();
        }
    }

    public static void populateMessasges(List<String> list)
    {
        for (String s : list)
        {
            if (!isEmpty(s) && (String.valueOf(s.charAt(0)).startsWith("a") || String.valueOf(s.charAt(0)).startsWith("b")))
            {
                messages.add(s);
            }
        }

        for (Rule rule : rules)
        {
            rule.initializeObject();
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

    public static List<String> getAllCombinations(Rule firstRule, Rule secondRule)
    {
        List<String> stringList = new ArrayList<>();

        for (String string1 : firstRule.getSolvedSegments())
        {
            for (String string2 : secondRule.getSolvedSegments())
            {
                String str = string1 + string2;

                if (!stringList.contains(str))
                {
                    stringList.add(str);
                }
            }
        }

        return stringList;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static Rule getRuleByRuleNo(int ruleNo)
    {
        for (Rule rule : rules)
        {
            if (rule.getRuleNo() == ruleNo)
            {
                return rule;
            }
        }
        return null;
//        return (Rule) rules.stream().filter(obj -> obj.getRuleNo() == ruleNo);
    }

    public static int getSolvedRuleCount()
    {
        int solvedRuleCount = 0;

        for (Rule rule : rules)
        {
            if (rule.isSolved())
            {
                solvedRuleCount++;
            }
        }

        return solvedRuleCount;
    }

    public static List<Integer> getSolvedRuleNos()
    {
        List<Integer> solvedRuleNos = new ArrayList<>();

        for (Rule rule : rules)
        {
            if (rule.isSolved())
            {
                solvedRuleNos.add(rule.getRuleNo());
            }
        }

        return solvedRuleNos;
    }

    public static void addSolvedRuleSegmentsToRule(Rule rule, List<String> solvedSegments)
    {
        List<String> ruleSegments = rule.getSolvedSegments();

        for (String segment : solvedSegments)
        {
            ruleSegments.add(segment);
        }

        rule.setSolvedSegments(ruleSegments);
    }












}