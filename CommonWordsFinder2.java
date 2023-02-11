/**
 * Author: Hannah Soria
 * Date: 4/21/2022
 * File: CommonWordsFinder2.java
 * Section lab C, Lecture A
 * Proejct 9: Word Trends
 * CS231 Spring 2022
 */

 //file for extensions

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CommonWordsFinder2 {
    private int size;
    private int wordCount;
    private ArrayList<KeyValuePair<String,Integer>> arrayList;
    private int currentIndex;
    private int currentIndexSort;
    private int index;

    //constructor for CommonWordsFInder2
    public CommonWordsFinder2(ArrayList<KeyValuePair<String,Integer>> arrayList, KVPComparator kvpComp){
        this.size = 0;
        this.wordCount = 0;
        this.arrayList = new ArrayList<KeyValuePair<String,Integer>>();
        this.currentIndex = 0;
        this.currentIndexSort = 0;
        this.index = 0;
    }

     //that reads the contents of a word count file and reconstructs the fields of the WordCount object into an arrayList
    public void readWordCountFile( String filename ){
        try{
      
            FileReader fReader = new FileReader(filename);// assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            BufferedReader bReader = new BufferedReader (fReader);// assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            String line = bReader.readLine();// assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            int numRows = 0;
            System.out.println(filename);
            while (line != null){
                String[] words = line.split("[^a-zA-Z0-9']");
                if (numRows == 0){
                    wordCount = Integer.parseInt(words[4]);
                }
                if (numRows != 0){
                    this.arrayList.add((new KeyValuePair<String,Integer>((words[0]), Integer.parseInt(words[1]))));
                    this.size ++;
                }
                line = bReader.readLine();
                numRows++;
            }
            System.out.println(arrayList);
            
            bReader.close();// call the close method of the BufferedReader
            }
            catch(FileNotFoundException ex) {
                System.out.println("Board.read():: unable to open file " + filename );
            }
            catch(IOException ex) {
                System.out.println("Board.read():: error reading file " + filename);
            }
    }

    //returns the frequency of a certain value
    public double frequency(KeyValuePair<String,Integer> kvp){
        return (double)kvp.getValue() / (double)wordCount;
    }

    //return the total word count from the last time readWords was called.
    public int totalWordCount(){
        return this.wordCount; 
    }

     //returns the number of unique words, which should be the size of the arrayList
    public int getUniqueWordCount(){
        return this.size;
    }

    //clears the arraylist
    public void clear(){
        this.arrayList= new ArrayList<KeyValuePair<String,Integer>>();
        this.wordCount = 0;
        this.size = 0;
    }

    //sorts the arraylist into max priority
    //if the arrayList is empty a kvp is added to a new array
    //if the arrayList is not empty and the values are then compared and sorted
    public ArrayList<KeyValuePair<String,Integer>> sortArray(ArrayList<KeyValuePair<String,Integer>> ogArray){
        ArrayList<KeyValuePair<String,Integer>> newArrayList = new ArrayList <KeyValuePair<String,Integer>> ();
        ArrayList<KeyValuePair<String,Integer>> newArrayList1 = new ArrayList <KeyValuePair<String,Integer>> ();
        int lastIndex = newArrayList.size();
        for (KeyValuePair<String,Integer> kvp: ogArray){
            newArrayList1.add(kvp);
        }
        for (KeyValuePair<String,Integer> kvp: ogArray){
            if (newArrayList.size() == 0){
                newArrayList.add(kvp);
                System.out.println("list after one add: " + newArrayList);
                index ++;
            } else {
                    if (kvp.getValue() >= newArrayList1.get(0).getValue()){
                        newArrayList.add(0, kvp);
                        System.out.println("after add to front: " + newArrayList);
                    } else {//(kvp.getValue()<= newArrayList1.get(lastIndex).getValue()){
                        newArrayList.add(lastIndex, kvp);
                        System.out.println("after add to end: " + newArrayList);
                    }
            }
            newArrayList1.remove(currentIndexSort);
            index++;
        }

        System.out.println(newArrayList);
        return newArrayList;
    }

    //looks at how many of the maximum values are want then returns a list of those values
    public ArrayList<KeyValuePair<String,Integer>> frequencyList(ArrayList<KeyValuePair<String,Integer>> sortedArray, int numItem){
        ArrayList<KeyValuePair<String,Integer>> finalList = new ArrayList <KeyValuePair<String,Integer>> ();
        for (int i = 0; i < numItem; i++){
            KeyValuePair<String,Integer> kvp = sortedArray.get(currentIndex);
            this.frequency(kvp);
            finalList.add(kvp);
            currentIndex ++;
        }
        return finalList;
    }

    //this function is the final method and calls on the two previous methods to return the frequencies
    public ArrayList<KeyValuePair<String,Integer>> finalFrequencyList(int numItems){
        ArrayList<KeyValuePair<String,Integer>> arr = this.sortArray(arrayList);
        ArrayList<KeyValuePair<String,Integer>> newArr = this.frequencyList(arr, numItems);
        return newArr;
    }

    //tests the functions
    public static void main( String[] argv ) {
        KVPComparator kvpComp = new KVPComparator();
        ArrayList<KeyValuePair<String,Integer>> arrayList = new ArrayList<KeyValuePair<String,Integer>>();
        CommonWordsFinder2 test = new CommonWordsFinder2(arrayList, kvpComp);
        test.readWordCountFile("counts_ct.txt");
        System.out.println("test total word count: " + test.totalWordCount());
        System.out.println("test unique word count: " + test.getUniqueWordCount());
        System.out.println(arrayList);
        System.out.println("test of frequency: " + test.finalFrequencyList(Integer.parseInt(argv[0])));
    }
}


