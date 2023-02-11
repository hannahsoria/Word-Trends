/**
 * Author: Hannah Soria
 * Date: 4/5/2022
 * File: AscendingString.java
 * Section lab C, Lecture A
 * Lab 7: Binary Search Trees and Sets
 * CS231 Spring 2022
 */

import java.util.Comparator;

public class AscendingString implements Comparator<String>{

    public int compare(String o1, String o2) {
        return o2.compareTo(o1);
    }
    public static void main(String[]args){
        AscendingString test = new AscendingString();
        System.out.println(test.compare("hello", "as"));
    }
}
