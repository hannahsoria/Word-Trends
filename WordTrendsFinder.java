/**
 * Author: Hannah Soria
 * Date: 4/29/2022
 * File: WordTrendsFinder.java
 * Section lab C, Lecture A
 * Proejct 9: Word Trends
 * CS231 Spring 2022
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WordTrendsFinder {
    int size;
    int wordCount;
    BSTMap<String, Integer> binaryTree;

    //constructor for wordTrendsFinder
    public WordTrendsFinder(){
        this.wordCount = 0;
        this.size = 0;
        binaryTree = new BSTMap<String, Integer>(new AscendingString());
    }

    //that reads the contents of a word count file and constructs the BSTMap.
    public void readWordCountFile( String filename ){ 
        try{
        FileReader fReader = new FileReader(filename);// assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
        BufferedReader bReader = new BufferedReader (fReader);// assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
        String line = bReader.readLine();// assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
        int numRows = 0;
        while (line != null){
            String[] words = line.split("[^a-zA-Z0-9']");
            if (numRows == 0){
                wordCount = Integer.parseInt(words[4]);
            }
            if (numRows != 0){
                this.binaryTree.put(words[0], Integer.parseInt(words[1]));
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

    //returns the frequency of a certain value
    public double frequency(double val){
        return (double)val / (double)wordCount;
    }

    //locates the desired word in the binary tree and returns the frequency of the value of that word
    public double findWord(String word){
        if (binaryTree.containsKey(word) == true){
            double val = binaryTree.get(word);
            return frequency(val);
        } else {
            return 0.0;
        }  
    }

    //test the functions
    //I receiveed help from Meredith Green correctly syntaxing how to read in the command lines
    public static void main( String[] argv ) {
        WordTrendsFinder test = new WordTrendsFinder();
        test.readWordCountFile("counts_ct.txt");
        System.out.println("test of findword: " + test.findWord("the"));
        System.out.println("test of frequency: " + test.frequency(10));
        for (int i = 3; i < argv.length; i++){
            System.out.print(argv[i] + " ");
            for (int j = Integer.parseInt(argv[1]); j <= Integer.parseInt(argv[2]); j++){
                String filename = argv[0] + Integer.toString(j) + ".txt";
                test.readWordCountFile(filename);
                System.out.print(test.findWord(argv[i]) + " ");
            }
            System.out.println(" ");
        }
    } 
}
