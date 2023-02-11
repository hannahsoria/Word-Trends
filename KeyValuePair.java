/**
 * Author: Hannah Soria
 * Date: 4/5/2022
 * File: KeyValuePair.java
 * Section lab C, Lecture A
 * Lab 7: Binary Search Trees and Sets
 * CS231 Spring 2022
 */

public class KeyValuePair<Key,Value> {
    Key k;
    Value v;

    public KeyValuePair( Key k, Value v ) {//the constructor initializing the key and value fields.
        this.k = k;
        this.v = v;
    }

    public Key getKey() {//returns the key.
        return k;
    }

    public Value getValue() {//returns the value.
        return v;
    }

    public void setValue( Value v ) {//sets the value.
        this.v = v;
    }

    public String toString() {//returns a String containing both the key and value.
        String s = "";
        s += "key: " + k + "     value: " + v;
        return s;
    }

    public static void main(String[]args){//test of the functions
        KeyValuePair<String,Integer> test = new KeyValuePair<>("test", 4);
        System.out.println("the key is: " + test.getKey());
        System.out.println("the value is: " + test.getValue());
        test.setValue(5);
        System.out.println("this new value is: " + test.getValue());
        test.toString();
    }

    
}
