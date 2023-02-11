/**
 * Author: Hannah Soria
 * Date: 4/21/2022
 * File: CommonWordsFinder.java
 * Section lab C, Lecture A
 * Proejct 9: Word Trends
 * CS231 Spring 2022
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommonWordsFinder {
    private int size;
    private int wordCount;
    private PQHeap heap;
    private Comparator comp;

    //constructor of CommonWordsFinder
    public CommonWordsFinder(PQHeap heap, Comparator comp){
        this.size = 0;
        this.wordCount = 0;
        this.heap = new PQHeap(comp);
        this.comp = comp;
    }

    //that reads the contents of a word count file and reconstructs the fields of the WordCount object into a heap.
    public void readWordCountFile( String filename ){ 
        try{
            FileReader fReader = new FileReader(filename);// assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            BufferedReader bReader = new BufferedReader (fReader);// assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            String line = bReader.readLine();// assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            int numRows = 0;
            System.out.println(filename);
            while (line != null){
                String[] words = line.split("[^a-zA-Z0-9']");
                //System.out.println(line);
                if (numRows == 0){
                    wordCount = Integer.parseInt(words[4]);
                }
                if (numRows != 0){
                    this.heap.add(new KeyValuePair<String,Integer>((words[0]), Integer.parseInt(words[1])));
                    this.size ++;
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
    }

    //returns the frequency of a given kvp
    public double frequency(KeyValuePair<String,Integer> kvp){
        return (double)kvp.getValue() / (double)wordCount;
    }

    //return the total word count from the last time readWords was called.
    public int totalWordCount(){
        return this.wordCount; 
    }

    //returns the number of unique words, which should be the size of the heap
    public int getUniqueWordCount(){ 
        return this.size;
    }

    //clears the heap
    public void clear(){
        this.heap = new PQHeap(this.comp);
        this.wordCount = 0;
        this.size = 0;
    }

    //test the functions
    //Meredith helped me with frequency/remove
    public static void main( String[] argv ) {
        KVPComparator kvpComp = new KVPComparator();
        PQHeap heap = new PQHeap<>(kvpComp);
        CommonWordsFinder test = new CommonWordsFinder(heap, kvpComp);
        test.readWordCountFile("counts_ct.txt");
        for (int i = 1; i< argv.length; i++){
            test.clear(); //test of the clear
            test.readWordCountFile(argv[i]); //test of the readwordcountfile
            System.out.println("test total word count: " + test.totalWordCount());
            System.out.println("test unique word count: " + test.getUniqueWordCount());
            for(int j = 0; j < Integer.parseInt(argv[0]); j++){
                KeyValuePair<String,Integer> kvp = (KeyValuePair<String,Integer>) test.heap.remove() ;
                System.out.println("the key: " + kvp.getKey());
                System.out.println("the frequency: " + test.frequency(kvp)); //test of the frequency
            }
        }
    }
}


