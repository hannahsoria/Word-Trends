/**
 * Author: Hannah Soria
 * Date: 4/5/2022
 * File: WordCounter2.java
 * Section lab C, Lecture A
 * Project 8: Trees v. Hashing
 * CS231 Spring 2022
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WordCounter2 {
    private MapSet<String, Integer> map;
    private int totalWords = 0;

    public WordCounter2( String data_structure ){ //constructor, where data_structure is either "bst" or "hashmap". It should create the appropriate data structure and store it in the map field.
        AscendingString string = new AscendingString();
        if (data_structure == "bst"){
            map = new BSTMap<String, Integer>( new AscendingString() );
        }
        if (data_structure == "hashmap"){
            map = new Hashmap<>(string);
        }
    }

    ArrayList<String> wordArr = new ArrayList<String>();
    public ArrayList<String> readWords( String filename ){//given the filename of a text file, read the text file and return an ArrayList list of all the words in the file.
        try{

            FileReader fReader = new FileReader(filename);// assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            BufferedReader bReader = new BufferedReader (fReader);// assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            String line = bReader.readLine();// assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            //int numRows = 0;
            while (line != null){
                String[] words = line.split("[^a-zA-Z0-9']");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();
                    if (word.length() !=0){
                        if (word.length() > 0){
                            wordArr.add(word);
                        }
                    }
                }
                line = bReader.readLine();
            }
            bReader.close();// call the close method of the BufferedReader
            }
            catch(FileNotFoundException ex) {
                System.out.println("Board.read():: unable to open file " + filename );
            }
            catch(IOException ex) {
                System.out.println("Board.read():: error reading file " + filename);
            }
        return wordArr; 
    }

    public double buildMap( ArrayList<String> words ){// given an ArrayList of words, put the words into the map data structure. Return the time taken in ms. Record the time using System.nanoTime().
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < words.size(); i ++){
            if(map.containsKey(words.get(i))){
                map.put(words.get(i), map.get(words.get(i)) +1);
                totalWords++;
            } else {
                map.put(words.get(i),1);
                totalWords++;
                //System.out.println(wordArr);
            } 
        }
        long currentTime = System.currentTimeMillis();
        long total = currentTime - startTime; 
        return total;
    }

    public void clearMap(){ // clear the map data structure.
        map.clear();
    }

    public int totalWordCount(){//return the total word count from the last time readWords was called.
        return totalWords; 

    }

    public int uniqueWordCount(){//return the unique word count, which should be the size of the map data structure.
        return map.size(); 

    }

    public int getCount( String word ) {// return the number of times the word occurred in the list of words. Query the data structure to get the information. Return 0 if the word does not exist in the data structure.
        int value = map.get(word);
        return value;
    }

    public int depthTree (){ //returns the depth of the tree
        return ((BSTMap<String, Integer>) map).depth();
    }

    public double getFrequency( String word ){ //return the frequency of the word in the list of words. Query the data structure to get the word count and then divide by the total number of words to get the frequency. Return 0 if the word does not exist in the data structure.
        return (float)getCount(word) / totalWords;
    }

    public int numCollisions (){ //returns the number of collisions
        return ((Hashmap<String, Integer>) map).getCollisions();
    }

    public boolean writeWordCount( String filename ) throws IOException{//write a word count file given the current set of words in the data structure. The first line of the file should contain the total number of words. Each subsequent line should contain a word and its frequency.
        String s = "";
        FileWriter wordCountFile = new FileWriter(filename);
        ArrayList<KeyValuePair<String,Integer>> wordCount = map.entrySet();
        int length = wordCount.size();
        s += "total word count: " + totalWordCount() + "\n";
        for (int i = 0; i < length; i ++){
            s += wordCount.get(i).getKey() + " " + wordCount.get(i).getValue() + "\n";
        }
        wordCountFile.write(s);
        wordCountFile.close();
        System.out.println("file created successfully");
        return true; 
    }
    
    public boolean readWordCount( String filename ){//read a word count file given the filename. The function should clear the map and then put all of the words, with their counts, into the map data structure.
        try{
            this.clearMap();
            FileReader fReader = new FileReader(filename);// assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            BufferedReader bReader = new BufferedReader (fReader);// assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            String line = bReader.readLine();// assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            int numRows = 0;
            while (line != null){
                System.out.println(line);
                if (numRows != 0){
                    String[] words = line.split("[^a-zA-Z0-9']");
                    map.put(words[0],Integer.parseInt(words[1]));
                }
                line = bReader.readLine();
                numRows++;
            }
            bReader.close();// call the close method of the BufferedReader
            }
            catch(FileNotFoundException ex) {
                System.out.println("Board.read():: unable to open file " + filename );
            }
            catch(IOException ex) {
                System.out.println("Board.read():: error reading file " + filename);
            }
        return false;   
    }

    public static void main( String[] argv ) throws IOException {
        WordCounter2 test = new WordCounter2("hashmap");
        ArrayList <Double> results = new ArrayList<>();
        System.out.println("Begin reading");
        ArrayList<String> words = test.readWords("reddit_comments_2008.txt");
        System.out.println("End reading");
        for (int i = 0; i < 5; i++){
            System.out.println("in for loop");
            test.clearMap();
            results.add(test.buildMap(words));
            System.out.println("here");
        }
        System.out.println("total word count: " + test.totalWordCount());
        System.out.println("unique word count: " + test.uniqueWordCount());
        //System.out.println("tree depth: " + test.depthTree());
        System.out.println("number of collisions: " + test.numCollisions());
        Double min = results.get(0);
        Double max = results.get(0);
        Double resultTotal = 0.0;
        for (int i = 0; i < results.size(); i++){
            Double result = results.get(i);
            resultTotal += result;
            if(min > result){
                min = result;
            }
            if(max < result){
                max = result;
            }
        }
        resultTotal = resultTotal - min;
        resultTotal = resultTotal - max;
        Double resultAverage = resultTotal / 3;
        System.out.println("average run time in ms: " + resultAverage);
    }
}
