package day21;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    static String inputFile = "src/day21/input.txt";
    static List<Food> foods = new ArrayList<>();
    static List<Ingredient> ingredientAllergens = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        List<String> inputFileList = importInputFileIntoInputFileList();
        populateFoods(inputFileList);
        initializeIngredientAllergens();

//        for (Ingredient ingredient : ingredientAllergens)
//        {
//            ingredient.initializeObject();
//        }

        for (Food food : foods)
        {
            for (String ingredient : food.getIngredients())
            {
                getIngredientByName(ingredient).incrementUsedCount();
            }
        }

//        processFoods();



        sop("\n====================================================================================================================================");
//        sop("Total tiles: " + tiles.size() + " | Solved tiles: " + getUnSolvedTileCount());



    }

//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================
//============================================================================================================================================



//    public static void processFoods()
//    {
//        for (Food food : foods)
//        {
//        }
//    }

    public static class Food
    {
        int foodNo;
        List<String> ingredients;
        List<String> allergens;

        public int getFoodNo()
        {
            return foodNo;
        }

        public void setFoodNo(int foodNo)
        {
            this.foodNo = foodNo;
        }

        public List<String> getIngredients()
        {
            return ingredients;
        }
        public void setIngredients(List<String> ingredients)
        {
            this.ingredients = ingredients;
        }

        public List<String> getAllergens()
        {
            return allergens;
        }
        public void setAllergens(List<String> allergens)
        {
            this.allergens = allergens;
        }

        private Food(int foodNo, List<String> ingredients, List<String> allergens)
        {
            this.foodNo = foodNo;
            this.ingredients = ingredients;
            this.allergens = allergens;
        }
    }

    public static class Ingredient
    {
        String name;
        List<String> allergens;
        int usedCount = 0;

        public String getName()
        {
            return name;
        }
        public void setName(String name)
        {
            this.name = name;
        }

        public List<String> getAllergens()
        {
            return allergens;
        }
        public void setAllergens(List<String> allergens)
        {
            this.allergens = allergens;
        }

        private Ingredient(String name, List<String> allergens)
        {
            this.name = name;
            this.allergens = allergens;
        }

        private void initializeObject()
        {
            for (Food food : foods)
            {
                for (String ingredient : food.getIngredients())
                {
                    getIngredientByName(ingredient).incrementUsedCount();
                }
            }
        }

        private void incrementUsedCount()
        {
            usedCount++;
        }
    }

    public static void initializeIngredientAllergens()
    {
        for (Food food : foods)
        {
            for (String s : food.getIngredients())
            {
                if (getIngredientByName(s) == null)
                {
                    ingredientAllergens.add(new Ingredient(s, new ArrayList<>()));
                }
            }
        }
    }

    public static void populateFoods(List<String> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            String line = list.get(i);
            int ingredientsEndIndex = line.indexOf("(");
            int allgergensBeginIndex = line.indexOf("contains") + 9;
            int allgergensEndIndex = line.indexOf(")");
            String ingredientsString = line.substring(0, ingredientsEndIndex);
            String allergensString = line.substring(allgergensBeginIndex, allgergensEndIndex);
            List<String> ingredientsList = Arrays.asList(ingredientsString.split(" "));
            List<String> allergensList = Arrays.asList(allergensString.split(","));
            List<String> ingredientsListCleaned = new ArrayList<>();
            List<String> allergensListCleaned = new ArrayList<>();

            for (String s : ingredientsList)
            {
                ingredientsListCleaned.add(s.replaceAll(" ", ""));
            }

            for (String s : allergensList)
            {
                allergensListCleaned.add(s.replaceAll(" ", ""));
            }

            foods.add(new Food(i, ingredientsListCleaned, allergensListCleaned));
        }

//            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
//            {
//                return line.substring(startIndex, endIndex);
//
//            foods.add(new Food(i, ingredients, allergens));
//        }
//
//        for (String s : list)
//        {
//            if (!s.isEmpty() && s.startsWith("Tile"))
//            {
//                tileNo = Integer.parseInt(s.replaceAll("[\\D]", ""));
//            }
//            else if (!s.isEmpty() && (s.startsWith(".") || s.startsWith("#")))
//            {
//                tileLines.add(s);
//            }
//            else // add the tile and reset
//            {
//                tiles.add(new Tile(tileNo, new ArrayList<>(tileLines)));
//                isNewTile = true;
//            }
//
//            rowNumber++;
//
//            if (rowNumber == list.size())
//            {
//                tiles.add(new Tile(tileNo, new ArrayList<>(tileLines)));
//            }
//        }
//
//        for (Tile tile : tiles)
//        {
//            tile.initializeObject();
//        }
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

    public static Ingredient getIngredientByName(String name)
    {
        for (Ingredient ingredient : ingredientAllergens)
        {
            if (ingredient.getName().equals(name))
            {
                return ingredient;
            }
        }

        return null;
    }

//    public static boolean isTileSolved(Tile tile)
//    {
//        if (tile.getTopNeighbor() == 0)
//        {
//            return false;
//        }
//
//        if (tile.getLeftNeighbor() == 0)
//        {
//            return false;
//        }
//
//        if (tile.getRightNeighbor() == 0)
//        {
//            return false;
//        }
//
//        if (tile.getBottomNeighbor() == 0)
//        {
//            return false;
//        }
//
//        return true;
//    }

//    public static int getUnSolvedTileCount()
//    {
//        int count = 0;
//
//        for (Tile tile : tiles)
//        {
//            if (!tile.isSolved())
//            {
//                count++;
//            }
//        }
//
//        return count;
//    }
//
//    public static void updateSolvedTileCount()
//    {
//        int count = 0;
//
//        for (Tile tile : tiles)
//        {
//            if (tile.isSolved())
//            {
//                count++;
//            }
//        }
//
//        setSolvedTileCount(count);
//    }

//    public static int getSolvedTileCount()
//    {
//        return solvedTileCount;
//    }
//    public static void setSolvedTileCount(int solvedTileCount)
//    {
//        Main.solvedTileCount = solvedTileCount;
//    }

}