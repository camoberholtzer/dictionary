/*
 * CS 2852 - 021
 * Spring 2016
 * Lab 4 - Dictionary
 * Name: Kyra Oberholtzer
 * Date: 4/5/2016
 */

package oberholtzerkn;

import javax.swing.*;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Driver Class
 * Handles choosing of the files, all of the output,
 * and checking to see how many misspelled words there are
 * @author Kyra Oberholtzer
 * @version 4/11/2016
 */
public class Driver {

    public static void main(String[] args){

        Collection<String> list = new ArrayList<>();
        Dictionary dic = pickDictionary(list);
        Dictionary txt = pickText(list);
        System.out.println("| Dictionary | Document | Collection | Misspelled | Time to add | Time to spell-check |");
        runArrayList(dic, txt);

        list = new LinkedList<>();
        runLinkedList(pickDictionary(list,dic.file), pickText(list, txt.file));

        list = new SortedArrayList<>();
        runSortedArrayList(pickDictionary(list, dic.file), pickText(list, txt.file));

        list = new ArrayList<>();
        runArrayList(pickDictionary(list,txt.file), pickText(list,dic.file));

        list = new LinkedList<>();
        runLinkedList(pickDictionary(list, txt.file), pickText(list, dic.file));

        list = new SortedArrayList<>();
        runSortedArrayList(pickDictionary(list,txt.file), pickText(list,dic.file));

    }


    /**
     * Checks the text and compares it to the words in the dictionary
     * and spell checks the text file. Returns the number of words misspelled
     * @param dictionary dictionary being used in the spell check
     * @param text text being checked by the dictionary
     * @param numMisspelled number of words misspelled(should be passed in as 0)
     * @return the number of words misspelled
     */
    public static int textCheck(Collection<String> dictionary, Collection<String> text, int numMisspelled){
        Iterator<String> itrTxt = text.iterator();

        String nextTxt = itrTxt.next();
        while(itrTxt.hasNext()){
            if(!dictionary.contains(nextTxt)){
                numMisspelled++;
            }
            nextTxt = itrTxt.next();
        }

        return numMisspelled;
    }

    /**
     * Prompts the user to select a file to be used as
     * either the dictionary or the text file
     * @return filename of file choosen
     */
    public static String fileChooser(){
        Path path = null;

        //Opens the file chooser
        JFileChooser jfc = new JFileChooser();
        jfc.setAcceptAllFileFilterUsed(true);

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION)
            path = jfc.getSelectedFile().toPath();

        //Sets the file object to the selected file
        return path.toFile().toString();
    }

    /**
     * First version of pickDictionary, accepts only a collection
     * and prompts the user to pick a file. This version is
     * used at the very beginning of the program and is not
     * required later on.
     * @param list empty collection used to create the dictionary object
     * @return the newly created dictionary object
     */
    public static Dictionary pickDictionary(Collection<String> list){
        Dictionary dictionary = new Dictionary(list);

        JOptionPane.showMessageDialog(null, "Please pick a dictionary reference.");
        dictionary.file = fileChooser();
        dictionary.timeToAdd = dictionary.load(dictionary.file);

        return dictionary;
    }

    /**
     * Second version of pickDictionary, accepts a collection and
     * a filename. This version is used later in the program
     * after user prompts are no longer required so that the
     * program may still run without user input.
     * @param list empty collection used to create the dictionary object
     * @param filename preselected file
     * @return the newly created dictionary object
     */
    public static Dictionary pickDictionary(Collection<String> list, String filename){
        Dictionary dictionary = new Dictionary(list);

        dictionary.file = filename;
        dictionary.timeToAdd = dictionary.load(dictionary.file);

        return dictionary;
    }

    /**
     * First version of pickText, which is much like the pickDictionary
     * method but prompts the user with a different message asking for the
     * text to spell check instead of the dictionary to read in.
     * @param list empty collection used to create the dictionary object
     * @return the newly created dictionary object
     */
    public static Dictionary pickText(Collection<String> list){
        Dictionary secondText = new Dictionary(list);

        JOptionPane.showMessageDialog(null, "Now pick a text to spell check.");
        secondText.file = fileChooser();
        secondText.timeToAdd = secondText.load(secondText.file);

        return secondText;
    }

    /**
     * Second version of pickText, used later in the program to skip the
     * user pick file prompts and still read in the text files
     * @param list empty collection used to create the dictionary object
     * @param filename preselected file
     * @return the newly created dictionary object
     */
    public static Dictionary pickText(Collection<String> list, String filename){
        Dictionary secondText = new Dictionary(list);

        secondText.file = filename;
        secondText.timeToAdd = secondText.load(secondText.file);

        return secondText;
    }

    /**
     * Used to run the necessary checks and output for an
     * ArrayList object
     * @param dictionary dictionary used in the spell check
     * @param secondText text being checked by the dictionary
     */
    public static void runArrayList(Dictionary dictionary, Dictionary secondText){
        Collection<String> textOne = dictionary.text;
        Collection<String> textTwo = secondText.text;

        long start = System.nanoTime();
        secondText.numMisspelled = textCheck(textOne, textTwo, 0);
        long stop = System.nanoTime();
        secondText.timeToSpellCheck = stop-start;

        arrayOutput(dictionary.file, secondText.file, secondText.numMisspelled, benchToString(dictionary.timeToAdd + secondText.timeToAdd),
                benchToString(dictionary.timeToSpellCheck));
    }

    /**
     * Used to run the necessary checks and output for a
     * LinkedList object
     * @param dictionary dictionary used in the spell check
     * @param secondText text being checked by the dictionary
     */
    public static void runLinkedList(Dictionary dictionary, Dictionary secondText){
        Collection<String> textOne = dictionary.text;
        Collection<String> textTwo = secondText.text;

        long start = System.nanoTime();
        secondText.numMisspelled = textCheck(textOne, textTwo, 0);
        long stop = System.nanoTime();
        secondText.timeToSpellCheck = stop-start;

        linkedOutput(dictionary.file, secondText.file, secondText.numMisspelled, benchToString(dictionary.timeToAdd + secondText.timeToAdd),
                benchToString(dictionary.timeToSpellCheck));
    }

    /**
     * Used to run the necessary checks and output for a
     * SortedArrayList object
     * @param dictionary dictionary used in the spell check
     * @param secondText text being checked by the dictionary
     */
    public static void runSortedArrayList(Dictionary dictionary, Dictionary secondText){
        Collection<String> textOne = dictionary.text;
        Collection<String> textTwo = secondText.text;

        long start = System.nanoTime();
        secondText.numMisspelled = textCheck(textOne, textTwo, 0);
        long stop = System.nanoTime();
        secondText.timeToSpellCheck = stop-start;

        sortedOutput(dictionary.file, secondText.file, secondText.numMisspelled, benchToString(dictionary.timeToAdd + secondText.timeToAdd),
                benchToString(dictionary.timeToSpellCheck));
    }

    /**
     * Converts the benchmarking times into minutes and seconds
     * and then properly formats the output
     * @param time benchmark time to be formatted
     * @return output string
     */
    public static String benchToString(long time){
        String out;
        int min = 00;

        String minute = "00";
        String second;

        DecimalFormat df1 = new DecimalFormat("00");

        if (time >= 6e10){
            min = (int) (time/6e10);
            time -= (double)min * (6e10);
            minute = df1.format(min);
        }
        DecimalFormat df2 = new DecimalFormat("00.0000");
        second = df2.format(time * 1e-9);

        out = minute + ":" + second;

        return out;
    }

    /**
     * Prints the output to be used if the Collection used
     * was an ArrayList
     * @param dictionary dictionary filename
     * @param text text filename
     * @param numMisspelled number of misspelled words in the text
     * @param timeToAdd time it took to load in the dictionary and the text
     * @param timeToSpellCheck time it took to spell check the text
     */
    public static void arrayOutput(String dictionary, String text, int numMisspelled, String timeToAdd,
                                   String timeToSpellCheck){
        System.out.printf("\n| %s | %s | ArrayList | %d | %s | %s |", dictionary, text, numMisspelled, timeToAdd,
                timeToSpellCheck);
    }

    /**
     * Prints the output to be used if the Collection used
     * was a LinkedList
     * @param dictionary dictionary filename
     * @param text text filename
     * @param numMisspelled number of misspelled words in the text
     * @param timeToAdd time it took to load in the dictionary and the text
     * @param timeToSpellCheck time it took to spell check the text
     */
    public static void linkedOutput(String dictionary, String text, int numMisspelled, String timeToAdd,
                                    String timeToSpellCheck){
        System.out.printf("\n| %s | %s | LinkedList | %d | %s | %s |", dictionary, text, numMisspelled, timeToAdd,
                timeToSpellCheck);
    }

    /**
     * Prints the output to be used if the Collection used
     * was a SortedArrayList
     * @param dictionary dictionary filename
     * @param text text filename
     * @param numMisspelled number of misspelled words in the text
     * @param timeToAdd time it took to load in the dictionary and the text
     * @param timeToSpellCheck time it took to spell check the text
     */
    public static void sortedOutput(String dictionary, String text, int numMisspelled, String timeToAdd,
                                    String timeToSpellCheck){
        System.out.printf("\n| %s | %s | SortedArrayList | %d | %s | %s |", dictionary, text, numMisspelled, timeToAdd,
                timeToSpellCheck);
    }

}
