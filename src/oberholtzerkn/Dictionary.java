/*
 * CS 2852 - 021
 * Spring 2016
 * Lab 4 - Dictionary
 * Name: Kyra Oberholtzer
 * Date: 4/5/2016
 */

package oberholtzerkn;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Dictionary Class
 * Used to load in text files as a Collection
 * that can then be checked against text files
 * to spell check them
 * @author Kyra Oberholtzer
 * @version 4/11/2016
 */
public class Dictionary {

    /**
     * Holds the words from the read in text file
     * Accessed by the Driver class
     */
    public Collection<String> text = new ArrayList<>();
    /**
     * Name of the file passed in by the fileChooser
     * method in the Driver class
     */
    public String file;
    /**
     * Measure of time required to add the words in
     * a text file to a specified type of list
     */
    public static long timeToAdd;
    /**
     * Measure of time required to check the words
     * against another text file
     */
    public static long timeToSpellCheck;
    /**
     * Number of misspelled words in the text file
     */
    public int numMisspelled = 0;

    /**
     * Instantiates the Dictionary class
     * @param text
     */
    public Dictionary(Collection<String> text){
        if (text == null){
            try{
                throw new NullPointerException("Invalid Collection Input.");
            }catch(NullPointerException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        else {
            text.clear();
        }
    }

    /**
     * Loads in a file that is passed in by the driver class
     * @param filename name of file to be accessed
     * @return time it takes to load in the file
     */
    public long load(String filename){
        long start = System.nanoTime();
        File file = new File(filename);
        try(Scanner in = new Scanner(file)){
            while(in.hasNext()){
                String[] temp = in.next().split(" |\\.|,|\"|---|:|0|1|2|3|4|5|6|7|8|9|\\*");
                for(int i = 0; i < temp.length; i++) {
                    text.add(temp[i]);
                }
            }
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "File Not Found.");
        }
        long stop = System.nanoTime();
        return stop-start;
    }

    /**
     * Checks to see if the List text contains the
     * desired string target
     * @param target word than the List is looking for
     * @return true/false whether it is successful or not
     */
    public boolean contains(String target){
        return text.contains(target);
    }

    /**
     * Clears the current list of words
     */
    public void clear(){
        text.clear();
    }


}
